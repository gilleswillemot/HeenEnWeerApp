import {
  CalendarEvent,
  DayView,
  DayViewHour,
  DayViewHourSegment,
  DayViewEvent
} from 'calendar-utils';
import {
  startOfDay, endOfDay, subDays, addDays, endOfMonth, isSameDay,
  isSameMonth, addHours
} from 'date-fns';
export class Dag {
  //activiteiten: CalendarEvent[];
  //dag;
  constructor(private _dag: string, private _activiteiten?: CalendarEvent[][]) {
    this._activiteiten = [];
      for (var i = 0; i < 24; i++) {//instantieren van de array
          this._activiteiten[i] = [];
      }
      // this.activiteiten = [];
      // this.vulActiveiten(activiteiten);
  }

  set activiteiten(act: CalendarEvent[][]) {
    this._activiteiten = act;
  }

  get activiteiten()/*:CalendarEvent[][]*/ {
      return this._activiteiten;
  }

  getActiviteiten(index: number) {
      return this._activiteiten[index];
  }
  set dag(dag: string) {
      this._dag = dag;
  }

  get dag() {
      return this._dag;
  }

  //geefActiviteitenChronologisch():CalendarEvent[][] {
  //  let chronActiviteiten = [];
  //  //controleren of this.activiteiten niet null is
  //  if (this.activiteiten === undefined) {
  //      return chronActiviteiten;
  //  }
  //    for (var i = 0; i < this.activiteiten.length; i++) {
          
  //    }
  //  return 
  //}

  /**
   * Gaat alle activiteiten van het parameter overlopen om
   * te controleren of ze op hetzelfde dag verlopen als het attribuut dag;
   */
  /*vulActiveiten(activiteiten: CalendarEvent[]) {
    for (var i = 0; i < activiteiten.length; i++) {
        let act = activiteiten[i];
          if (isSameDay(act.start, this.dag)) {
              this.activiteiten.push(act);
          }
      }
  }*/
}
