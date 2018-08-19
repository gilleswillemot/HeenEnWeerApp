    import {
  startOfDay, endOfDay, subDays, addDays, endOfMonth, isSameDay,
  isSameMonth, addHours, addWeeks
} from 'date-fns';
import { CalendarEvent } from "angular-calendar";
import { colors } from './colors';


export /*const*/let testActiviteiten: /*CalendarEvent*/any[] = [
  {
    start: new Date('2017-11-15T00:00:00'),
    end: addDays(new Date('2017 - 11 - 15T00:00:00'), 1),
    title: 'event 15 november 2017 en duurt 1 dag' /*this._activiteiten.subscribe(val => val[0].beschrijving)*/,
    color: {
      primary: colors.lightred,
      secondary: colors.lightred
    },
    actions: this.actions,
    personen: [0, 1],
    beschrijving: "Dit is een event die plaatsneemt op 15 november 2017 en een duratie heeft van 1 dag.",
    id: 12 //hier mag ook een string waarde
  },
  {
    start: new Date(),
    end: addDays(new Date(), 1),
    title: 'Nu tot morgen event.' /*this._activiteiten.subscribe(val => val[0].beschrijving)*/,
    color: {
      primary: colors.lightred,
      secondary: colors.lightred
    },
    actions: this.actions,
    personen: [0, 1],
    beschrijving: "Dit is een event die plaatsneemt op 15 november 2017 en een duratie heeft van 1 dag.",
    id: 8//"stringId"
  },
  {
    start: new Date(),
    end: addDays(new Date(), 2),
    title: 'Nu tot en met binnen 2 dagen event.' /*this._activiteiten.subscribe(val => val[0].beschrijving)*/,
    color: {
      primary: colors.lightblue,
      secondary: colors.lightblue
    },
    actions: this.actions,
    id: 7,
    personen: [0, 1],
    beschrijving: "Dit is een event die plaatsneemt op 15 november 2017 en een duratie heeft van 1 dag.",
  },
  {
    start: new Date('2017-11-16T00:15:00'),
    end: new Date('2017-11-16T00:15:00'),
    title: 'event op 16 november 2017' /*this._activiteiten.subscribe(val => val[0].beschrijving)*/,
    color: {
      primary: colors.lightred,
      secondary: colors.lightred
    },
    actions: this.actions,
    personen: [0, 1],
    beschrijving: "Dit is een event die plaatsneemt op 15 november 2017 en een duratie heeft van 1 dag.",
    id: 9

  },
  {
    start: subDays(new Date(), 1),
    end: subDays(new Date(), 1),
    title: '1 dag vóór vandaag event.' /*this._activiteiten.subscribe(val => val[0].beschrijving)*/,
    color: {
      primary: colors.lightred,
      secondary: colors.lightred
    },
    actions: this.actions,
    personen: [0, 1],
    beschrijving: "Dit is een event die plaatsneemt op 15 november 2017 en een duratie heeft van 1 dag.",
    id: 25

  },
  {
    start: new Date(2017, 10, 21, 23, 0),
    end: addHours(new Date(2017, 10, 21, 23, 0), 2),
    title: 'start om 23u op 21/11/2017, eindigt 2 uur later.',
    color: {
      primary: colors.lightyellow,
      secondary: colors.lightyellow
    },
    actions: this.actions,
    id: 34,
    beschrijving: "Dit is een event die plaatsneemt op 15 november 2017 en een duratie heeft van 1 dag.",
    personen: [0, 1]
  },
  {
    start: new Date(),// subDays(endOfMonth(new Date()), 3),
    end: addHours(new Date(), 14),
    title: 'start nu, eindigt binnen 14 uur',
    color: {
      primary: colors.lightblue,
      secondary: colors.lightblue
    },
    personen: [0, 1],
    beschrijving: "Dit is een event die plaatsneemt op 15 november 2017 en een duratie heeft van 1 dag.",
    id: 36
  },
  {
    start: new Date(),
    end: addDays(new Date(),3),
    title: 'start nu, eindigt in 3 dagen op zelfde uur.',
    color: {
      primary: colors.lightyellow,
      secondary: colors.lightyellow
    },
    actions: this.actions,
    resizable: {
      beforeStart: true,
      afterEnd: true
    },
    draggable: true,
    id: 45,
    beschrijving: "Dit is een event die plaatsneemt op 15 november 2017 en een duratie heeft van 1 dag.",
    personen: [0, 1]
  },
  {
    start: addWeeks(new Date(), 1),
    end: addWeeks(new Date(), 1), //"2017-11-09T22:59:59.999Z",
    title: "event die week na vandaag start.",
    color: {
      primary: colors.lightblue,
      secondary: colors.lightblue
    },
    draggable: true,
    resizable: {
      beforeStart: true,
      afterEnd: true
    },
    id: 48,
    beschrijving: "Dit is een event die plaatsneemt op 15 november 2017 en een duratie heeft van 1 dag.",
    personen: [0, 1]
  },
  {
    start: subDays(new Date(), 2),
    end: addHours(subDays(new Date(), 2), 5), //"2017-11-09T22:59:59.999Z",
    title: "event die eergisteren startte en 5 uur duurde.",
    color: {
      primary: colors.lightblue,
      secondary: colors.lightblue
    },
    draggable: true,
    resizable: {
      beforeStart: true,
      afterEnd: true
    },
    id: "49",
    beschrijving: "Dit is een event die plaatsneemt op 15 november 2017 en een duratie heeft van 1 dag.",
    personen: [0, 1]
  }
  //{
  //  start: addDays(new Date(), 5),
  //  end: new Date(), //"2017-11-09T22:59:59.999Z",
  //  title: "New event",
  //  color: colors.blue,
  //  draggable: true,
  //  resizable: {
  //    beforeStart: true,
  //    afterEnd: true
  //  }
  //}
];
