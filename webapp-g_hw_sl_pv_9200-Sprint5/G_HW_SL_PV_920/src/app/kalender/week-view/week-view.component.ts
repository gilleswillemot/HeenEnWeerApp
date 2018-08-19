import { Component, OnInit, Input, Output, EventEmitter, OnChanges, SimpleChange, SimpleChanges, TemplateRef }
  from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { CalendarEvent, CalendarEventAction, CalendarEventTimesChangedEvent } from "angular-calendar";

import {
  startOfDay, endOfDay, subDays, addDays, endOfMonth, isSameDay, eachDay,
  isSameMonth, addHours, isSameWeek, differenceInHours, startOfWeek
} from 'date-fns';
import { Dag } from './dag.model';
import { Subscription } from 'rxjs/Subscription';

@Component({
  selector: 'app-week-view',
  templateUrl: './week-view.component.html',
  styleUrls: ['./week-view.component.css']
})
export class WeekViewComponent implements OnInit, OnChanges {
  private _viewDate: Date;
  constructor() { }
  @Input() events: CalendarEvent[] = [];

  @Input() viewDate: Date;
  //}@Input()
  //set viewDate(date: Date) {
  //  this.activiteitenOpdelenPerDag(date);
  //    this._viewDate = date;
  //}

  @Input() refresh: Subject<any>;
  @Input() ververs: boolean;
  @Input() headerTemplate: TemplateRef<any>;
  week: string[];
  tijdspannes2: string[];
  activiteiten: CalendarEvent[];
  tijdspannesArray: CalendarEvent[][] = [];
  weekrooster: Dag[];
  weekDagen: string[] = ["Maandag", "Dinsdag", "Woensdag", "Donderdag", "Vrijdag", "Zaterdag", "Zondag"];
  refreshSubscription: Subscription;
  maand: string;
  maanden: string[] = ["Januari", "Februari", "Maart", "April", "Mei", "Juni", "Juli", "Augustus",
    "September", "Oktober", "November", "December"];
  /**
   * Called when the event title is clicked
   */
  @Output()
  eventClicked: EventEmitter<{ event: CalendarEvent }> =
  new EventEmitter<{ event: CalendarEvent; }>();
  //new EventEmitter<CalendarEvent>();

  ngOnInit() {
    this.weekHeaderOpvullen();//de header van de tabel (elke dag + dagnummer weergeven)
    this.tijdspannes2 = [
      "00:00", "01:00", "02:00", "03:00", "04:00", "05:00",
      "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00",
      "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00",
      "22:00", "23:00"
    ];
    //TODO in de toekomst een weergave van aantal activiteiten deze week, aantal volgende week. "er zijn x ingepland voor x"
    this.maand = this.maanden[this.viewDate.getMonth()];
    this.activiteitenOpdelenPerDag();
    //if (this.refresh) {
    //    this.refreshSubscription = this.refresh.subscribe(() => {

    //        console.log("refresh doet iets.");
    //        this.activiteitenOpdelenPerDag();
    //    });
    //};
    //this.activiteitenOpdelenPerDag();
  }

  ngOnChanges(changes: SimpleChanges) {
    const viewDate: SimpleChange = changes["viewDate"];
    if (!viewDate.isFirstChange()/*viewDate.currentValue !== viewDate.previousValue*/) {//in de toekomst niet meer de lijst van activiteiten
      //opvullen in ngOnInit, maar meteen hier.
      this.weekHeaderOpvullen();
      this.activiteitenOpdelenPerDag();
      this.maand = this.maanden[this.viewDate.getMonth()];
      console.log("viewdate veranderd.");
      // if(change)
    }
    //    changes.
    ////    const viewDate: SimpleChange = changes.this.viewDate;
    //    console.log('prev value: ', name.previousValue);
    //    console.log('got name: ', name.currentValue);
    //    this._name = name.currentValue.toUpperCase();
  }

  weekHeaderOpvullen() {
    this.week = []; //week leegmaken
    let eersteDag: Date = startOfWeek(this.viewDate, { weekStartsOn: 1 });
    for (var i = 0; i < this.weekDagen.length; i++) {
      this.week[i] = `${this.weekDagen[i]} ${eersteDag.getDate()}`;
      eersteDag = addDays(eersteDag, 1);
    }
  }

  activiteitOptiesWeergeven(activiteit: CalendarEvent) {
    this.eventClicked.emit({ event: activiteit });
  }
  //get weekrooster() {

  //  return this._weekrooster;
  //}


  private activiteitenOpdelenPerDag() {
    this.weekrooster = [];
    //elke index van variabele week zal een dag voorstellen
    //maandag= index 0, disndag= index 1, etc.
    let week: Dag[][] = [];
    //het instantieren van de array.
    for (var i = 0; i < 7; i++) {
      this.weekrooster[i] = new Dag(this.week[i]);
    }
    for (var t = 0; t < this.events.length; t++) {
      let act = this.events[t];
      let actStart = act.start;
      let actEnd = act.end;
      if (isSameWeek(this.viewDate, actStart)) {
        // let dagNummer = act.start.getDay();
        let daysArray = eachDay(actStart, actEnd);
        if (daysArray.length > 1) { //als activiteit > 1 dag inneemt.
          this.weekrooster[(actStart.getDay() + 6) % 7].activiteiten[actStart.getHours()].push(act);
          this.weekrooster[(actEnd.getDay() + 6) % 7].activiteiten[actEnd.getHours()].push(act);
        }
        else {//als activiteit < 1 dag inneemt.
          let activiteitsDuur = differenceInHours(actEnd, actStart);//#uren tussen de actvtn
          let dagNr = (actStart.getDay() + 6) % 7;
          activiteitsDuur = activiteitsDuur === 0 ? 1 : activiteitsDuur;//vermijden dat de duur 0 is en niet loopt
          for (var j = 0; j < activiteitsDuur; j++) {
            this.weekrooster[dagNr].activiteiten[actStart.getHours() + j].push(act);
          }
        }
        /*daysArray.forEach(day => this._weekrooster[(day.getDay() + 6) % 7]
          .activiteiten[day.getHours()].push(act));*/
        //getDay geeft 0 terug als zondag, 1 als maandag, etc
        //(dus + 6 % 7) geeft dan 0 als maandag, 1 als dinsdag, etc
        //vervolgens wordt de activiteit op basis van diens start uur
        //op de juiste index geplaatst: start uur=7 -> index 7

      }
    }
  }

}/*Einde class*/

