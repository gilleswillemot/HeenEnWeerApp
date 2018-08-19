import {
  Component, OnInit, Input, Output, OnChanges, EventEmitter, ChangeDetectionStrategy,
  SimpleChange, SimpleChanges, ChangeDetectorRef
} from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { Subscription } from 'rxjs/Subscription';
import {
  startOfDay, endOfDay, subDays, addDays, endOfMonth, isSameDay, isSameMonth, addHours
} from 'date-fns';
import { CalendarEvent } from "angular-calendar";

@Component({
  selector: 'app-dag-view',
  templateUrl: './dag-view.component.html',
  styleUrls: ['./dag-view.component.css'],
  //changeDetection: ChangeDetectionStrategy.OnPush

})
export class DagViewComponent implements OnInit, OnChanges {
  @Input() viewDate: Date;
  @Input() events: CalendarEvent[] = [];
  days = ['Zondag', 'Maandag', 'Dinsdag', 'Woensdag', 'Donderdag', 'Vrijdag', 'Zaterdag'];
  dag: string;
  dagNummer;
  titel;
  datum;
  huidigUurString: string;
  //tijdspannes: string[] = ["6 - 8u", "8u - 10u", "10u - 12u", "12u - 14u",
  //  "14u - 16u", "16u - 18u", "18u - 20u", "20u - 22u", "22u - 24u"];
  tijdspannes2: string[] = ["00:00", "01:00", "02:00", "03:00", "04:00", "05:00",
    "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00",
    "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00",
    "22:00", "23:00"];
  idHuidigeActiviteit: number;
  huidigeActiviteit: CalendarEvent;
  /**
  * Elke rij van de array stelt een tijdspanne voor
  * Rij met index 0 stelt <6 - 8 uur voor, index 1 stelt 8 - 10 voor, etc.
  */
  tijdspannesArray: CalendarEvent[][] = [];
  test2;

  @Input() refresh: Subject<any>;
//  @Input() activiteit;
  //@Input() activiteitWeergeven: boolean;
  @Output()
  eventClicked: EventEmitter<{ event: CalendarEvent }> =
  new EventEmitter<{ event: CalendarEvent; }>();

  constructor(private changeDetector: ChangeDetectorRef) { }

  ngOnInit() {
    this.dag = this.days[this.viewDate.getDay()];
    this.dagNummer = this.viewDate.getDay;
    //neemt enkel de activiteiten van deze dag nemen.

    //activiteiten in 2 dimens. array stoppen volgens tijdspanne.
    this.activiteitenCategoriseren();
    this.huidigUurStringInstellen();

  }

  ngOnChanges(changes: SimpleChanges): void {
    const viewDate: SimpleChange = changes["viewDate"];
    if (!viewDate.isFirstChange()) {//als de viewDate veranderd => als er bvb op volgende dag wordt geklikt (< of >)
      this.dag = this.days[this.viewDate.getDay()];
      this.activiteitenCategoriseren();
      console.log("viewdate is veranderd");
    }
  }

  activiteitBewerken(act: CalendarEvent) {
      console.log(act);
    this.eventClicked.emit({ event: act });
  }

  activiteitenFilteren(): CalendarEvent[] {
    //console.log(this.activiteiten.length);
    let activiteiten: CalendarEvent[] = [];//leegmaken
    // console.log(this.activiteiten.length);

    for (var i = 0; i < this.events.length; i++) {
      let tempEvent = this.events[i];
      //  console.log("huidige datum:" + this.viewDate.toLocaleDateString());
      // console.log("datum van activiteit:" + tempEvent.start.toLocaleDateString());
      if (isSameDay(tempEvent.start, this.viewDate))
        /*tempEvent.start.toLocaleDateString() === this.viewDate.toLocaleDateString())*/ {
        activiteiten.push(tempEvent);
      }
    }
    //  console.log(this.activiteiten.length);
    //this.changeDetector.detectChanges();// markForCheck();
    return activiteiten;

  }

//  get activiteitWeergeven():boolean {
//      return this.activiteit !== undefined;
//  }

  huidigUurStringInstellen() {
    let date = new Date().getHours();
    this.huidigUurString = ("0" + date).slice(-2) + ":00";
  }

  huidigUurCheck(uur: string): boolean {
    console.log(uur.substr(0, 2));
    console.log(uur === this.huidigUurString);
      console.log(this.huidigUurString);
    return uur === this.huidigUurString;
  }

  activiteitenCategoriseren() {
    //array instantieren.
    let activiteiten: CalendarEvent[] = this.activiteitenFilteren();
    for (var i = 0; i < 24; i++) {
      this.tijdspannesArray[i] = [];
    }

    for (var k = 0; k < activiteiten.length; k++) {
      let act = activiteiten[k];
      let uur = act.start.getHours();
      //dit plaatst alle activiteiten chronologisch volgens tijdspanne in de array
      //als de activiteit start om 8 uur dan zal deze zich op index 8 bevinden.
      this.tijdspannesArray[uur].push(act);
      //testen of de cases chronologisch verlopen, zo ja dan is
      // het testen of het groter is dan de vorige case niet meer nodig:
      // als 2 uur niet bij de eerste case (kleiner dan 1 uur past) moet je
      // in de volgende case alleen testen of het kleiner of gelijk is aan 2
      //switch (true) {
      //  case uur <= 1: this.tijdspannesArray[0].push(act); break;
      //  case uur <= 2: this.tijdspannesArray[1].push(act); break;
      //  case uur <= 3: this.tijdspannesArray[0].push(act); break;
      //  case uur <= 3: this.tijdspannesArray[0].push(act); break;
      //  case uur <= 3: this.tijdspannesArray[0].push(act); break;
      //case uur <= 8: this.tijdspannesArray[0].push(act); break;
      //case uur > 8 && uur <= 10: this.tijdspannesArray[1].push(act); break;
      //case uur > 10 && uur <= 12: this.tijdspannesArray[2].push(act); break;
      //case uur > 12 && uur <= 14: this.tijdspannesArray[3].push(act); break;
      //case uur > 14 && uur <= 16: this.tijdspannesArray[4].push(act); break;
      //case uur > 16 && uur <= 18: this.tijdspannesArray[5].push(act); break;
      //case uur > 18 && uur <= 20: this.tijdspannesArray[6].push(act); break;
      //case uur > 20 && uur <= 22: this.tijdspannesArray[7].push(act); break;
      //case uur > 22 && uur <= 24: this.tijdspannesArray[8].push(act); break;

      //};
    }
    // console.log("tijdspanne test: " + this.tijdspannesArray[0].length);
    // console.log("tijdspanne test2: " + this.tijdspannesArray[1].length);
  }
}
