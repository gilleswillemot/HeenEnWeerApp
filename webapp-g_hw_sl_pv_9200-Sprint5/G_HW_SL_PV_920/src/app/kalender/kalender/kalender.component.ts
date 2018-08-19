import {Component, OnInit, Output, EventEmitter, TemplateRef, ViewChild, OnChanges, SimpleChanges, Input,SimpleChange } from '@angular/core';
import { ActiviteitService } from "../activiteit.service";
import { Activiteit } from '../../activiteit.model';
import { CalendarEvent, CalendarEventAction, CalendarEventTimesChangedEvent } from "angular-calendar";
import { Subject } from 'rxjs/Subject';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GebruikerService } from '../../gebruiker.service';
import { Gezin } from '../../gezin.model';

import {
  startOfDay, endOfDay, subDays, addDays, endOfMonth, isSameDay, isSameMonth, addHours, getISOWeek,
  subWeeks, addWeeks, addMonths, subMonths
} from 'date-fns';


@Component({
  selector: 'app-kalender',
  templateUrl: './kalender.component.html',
  styleUrls: ['./kalender.component.css'],
  providers: [ActiviteitService]
})

export class KalenderComponent implements OnInit, OnChanges {

  @ViewChild('modalContent') modalContent: TemplateRef<any>;
  activePanel: string = "panel-kalender";
  @Input() activiteitActie: number;
  gezinnen: Gezin[];/*Gezin[], gevuld door gezinService, op zelfde niveau van onder app*/
  view: string = 'month';//month //zorgt ervoor dat de maandkalender wordt getoont op de pagina, kan ook dag/week zijn.
  _customEvents;
  _wederkerendeCustomEvents;
  viewDate: Date = new Date();//is het startdag, toont de maand van deze dag
  actions: CalendarEventAction[] = [
    {
      label: '<i class="fa fa-fw fa-pencil"></i>',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.handleEvent('Edited', event);
      }
    },
    {
      label: '<i class="fa fa-fw fa-times"></i>',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this._customEvents = this._customEvents.filter(iEvent => iEvent !== event);
        this.handleEvent('Deleted', event);
      }
    }
  ];
  datumClicked: Date = new Date();
  refresh: Subject<any> = new Subject();
  // moet een model teruggeven met activiteiten en een attribuut gezinNaam en id) of we doen als eerste kalender altijd een merge van alles.
  //_nieuweActiviteitToevoegen: boolean = false;
  activiteitBewerkenOfToevoegen: boolean = false;
  activeDayIsOpen: boolean = true;//blijft op false, we gaan hier zelf iets voor ontwerpen. Of we overriden het
  activiteitBewerken: boolean = false;
  gezinsLeden;
  _geselecteerdeActiviteit;
  activiteitsPersonen;
  kalenderWeergeven: boolean = false;
  ingelogdeGebruiker;

  constructor(private route: ActivatedRoute, private activiteitService: ActiviteitService,
    private gebruikerService: GebruikerService, private modal: NgbModal) {//TODO check of modal weg mag

  }


  ngOnInit() {
    this.gezinnen = [];
    this._customEvents = [];
    this._wederkerendeCustomEvents = [];

    this.gebruikerService.ingelogdeGebruiker.subscribe(gebruiker => {
      this.ingelogdeGebruiker = gebruiker;
    });

    this.activiteitService.activiteiten.subscribe((activiteiten: Activiteit[]) => {
      this._customEvents = activiteiten.map(act => act.toCalendarEvent());
      console.log(this._customEvents);

    });


    this.gebruikerService.haalGezinUitDatabase().subscribe(gezin => {
      this.gezinsLeden = gezin.gezinsleden;
      this.gezinnen.push(gezin);
      console.log(this.gezinnen);
    });

    //this.gebruikerService.haalAlleGezinnenUitDatabase().subscribe(gezinnen => {
    //  this.gezinnen = gezinnen;
    //});

    const paramId: number = this.route.snapshot.params['activiteitId'];
    if (paramId !== undefined) {
      this._geselecteerdeActiviteit = this._customEvents.find(act => act.id === +paramId);
      if (this._geselecteerdeActiviteit !== undefined) {
        this.view = 'day';
        this.viewDate = this._geselecteerdeActiviteit.start;
        console.log(this._geselecteerdeActiviteit);
        this.activiteitToevoegenOfBewerken();
        this.activiteitBewerken = true;
      }
    }
    //this.route.paramMap.subscribe(
    //  params => {
    //    let id = +params.get('gezinId'); //de plus cast de string om naar een number
    //    if (id)//gebruiker heeft nog geen specifieke kalender opgevraagd.
    //      this.service.getActiviteitenVanGezin(id).subscribe(response => {
    //        this.activiteiten = response;
    //        this.customEvents = this.activiteitenToevoegenAanKalender(this.activiteiten);
    //      });
    //  }
    //);
    //this.service.activiteiten.subscribe(response => {
    //  this.activiteiten = response;
    //  this.customEvents = this.activiteitenToevoegenAanKalender(this.activiteiten);
    //});
  }


  ngOnChanges(changes: SimpleChanges) {
    const ce: SimpleChange = changes["_customEvents"];
    console.log(ce);
    if (!ce.isFirstChange()) {
      console.log("penes");
      //this.setActiviteit();
    } if (ce.isFirstChange()) {
      console.log("penes");
      //this.setActiviteit();
    } if (ce.firstChange) {
      console.log("penes");
      //this.setActiviteit();
    }
    if (changes['_customEvents']) {
      console.log("penes");

    }
  }

  get weekHeader() {
    return `week ${getISOWeek(this.viewDate)} van 2017`;
  }

  dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
    //TODO overgaan naar day view component
    this.datumClicked = date;

    if (isSameMonth(date, this.viewDate)) {
      if (
        (isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) || events.length === 0) {
        this.activeDayIsOpen = false;
      } else {
        this.activeDayIsOpen = true;
        this.viewDate = date;
      }
    }
  }

  handleEvent(action: string, event: CalendarEvent): void {
    console.log(event);
    this._geselecteerdeActiviteit = event;
    this.activiteitToevoegenOfBewerken();
    this.activiteitBewerken = true;
  }

  veranderEvents(terugInTijd: boolean) {
    if (terugInTijd) //subtract, week/maand/day aftrekken van activiteit datum.
      switch (this.view) {
        case "month":
          this._wederkerendeCustomEvents.forEach(item => {
            item.start = subMonths(item.start, 1);
          });
          break;
        case "week": this._wederkerendeCustomEvents.forEach(item => {
          item.start = subWeeks(item.start, 1);
        });
          break;
        case "day": this._wederkerendeCustomEvents.forEach(item => {
          item.start = subDays(item.start, 1);
        }); break;
      }
    else {//add, week/maand/day toevoegen aan datum van activiteit.
      switch (this.view) {
        case "month":
          this._wederkerendeCustomEvents.forEach(item => {
            item.start = addMonths(item.start, 1);
          });
          break;
        case "week": this._wederkerendeCustomEvents.forEach(item => {
          item.start = addWeeks(item.start, 1);
        });
          break;
        case "day": this._wederkerendeCustomEvents.forEach(item => {
          item.start = addDays(item.start, 1);
        }); break;
      }
    }

  }

  eventTimesChanged({
    event,
    newStart,
    newEnd
  }: CalendarEventTimesChangedEvent): void {
    event.start = newStart;
    event.end = newEnd;
    this.handleEvent('Dropped or resized', event);
    this.refresh.next();
  }

  activiteitToevoegenOfBewerken() {
    console.log("activiteit toevoegen of bewerken.");
    this.activiteitBewerkenOfToevoegen = true;
  }

  geselecteerdGezin(gezinId): boolean {
    return gezinId === this.ingelogdeGebruiker.huidigGezinId;
  }

  wijzigActiviteit(activiteit) {
    console.log("wijzigActiviteit uitgevoerd");
    if (this.activiteitBewerken) { // activiteit bewerken
      this.activiteitService.wijzigActiviteit(activiteit).subscribe((item: Activiteit) => this
        ._geselecteerdeActiviteit = item.toCalendarEvent());
      this.activiteitBewerken = false;
    }
    else { //nieuwe activiteit
      console.log("nieuwe activiteit");
      this.activiteitService.voegNieuweActiviteitToe(activiteit)
        .subscribe((activiteit: Activiteit) => {
          console.log(activiteit);
          this._customEvents.push(activiteit.toCalendarEvent());
        });
    } /*else if (this.activiteitActie === 2)*/ //activiteit verwijderen
    
    this._geselecteerdeActiviteit = undefined;
    this.activiteitBewerkenOfToevoegen = false;
    //this.activePanel = "panel-kalender";
  }



  converter(activiteit) {
    return {
      start: activiteit.startDatum,
      end: activiteit.eindDatum,
      title: activiteit.titel,
      color: {
        primary: activiteit.kleur,
        secondary: activiteit.kleur
      },
      personen: activiteit.personen,
      beschrijving: activiteit.beschrijving,
      id: activiteit.id
    }
  }

  kalenderWeergaveKnopInversen() {
    this.kalenderWeergeven = !this.kalenderWeergeven;
  }

  activiteitActieUitvoeren(actie: boolean) {
    if (actie) { //verwijderen van activiteit {
      this.activiteitService.verwijderActiviteit(this._geselecteerdeActiviteit);
      const index: number = this._customEvents.indexOf(this._geselecteerdeActiviteit);
      if (index !== -1) {//TODO lijst opnieuw ophalen idpv customevents aan te passen.
        this._customEvents.splice(index, 1);
      } //activiteitActie === 0, niets doen, terug gaan. uit bewerken
    }
    this.activiteitBewerken = false;
    this._geselecteerdeActiviteit = undefined;
    this.activiteitBewerkenOfToevoegen = false;
    //this.route.params.subscribe(params => {
    //    this.param = params['yourParam'];
    //    this.initialiseState(); // rest and set based on new parameter this time
    //});
  }
}/*Einde class kalenderComponent*/
