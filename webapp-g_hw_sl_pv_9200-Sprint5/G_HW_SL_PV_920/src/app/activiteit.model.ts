import { Gebruiker } from './gebruiker.model';

export class Activiteit {

  constructor(
    private _titel: string,
    private _startDatum: Date,
    private _eindDatum: Date,
    private _kleur: string,
    private _beschrijving: string,
    private _personen: string[],//id's van de betrokken personen tot deze activiteit.
    private _soortEvent: number,//mag waarschijnlijk een enum zijn in de toekomst
    private _gesprekId?: string,
    private _id?: string
  ) {
  }

  static fromJSON(json): Activiteit {
    const rec = new Activiteit(json.titel, new Date(json.startDatum), new Date(json.eindDatum),
      json.kleur, json.beschrijving, json.personen, json.soortEvent, json.gesprekId);
    rec._id = json._id;
    return rec;
  }

  get soortEvent() {
    //0 is niet wederkerende, 1 is wekelijks, 2 is maandelijks, ...
    return this._soortEvent;
  }

  heeftGesprek():boolean {
      return this._gesprekId !== undefined;
  }

  set gesprekId(id: string) {
      this._gesprekId = id;
  }

  get gesprekId(){return this._gesprekId}

  get titel() {
    return this._titel;
  }

  get beschrijving() {
    return this._beschrijving;
  }

  get startDatum() {
    return this._startDatum;
  };

  get eindDatum() {
    return this._eindDatum;
  }

  get kleur() {
    return this._kleur;
  }

  get personen() {
    return this._personen;
  }

  get id() {
    return this._id;
  }

  getTijdAlsString() {
    let tijd: string = "";

    if (this._startDatum.getMinutes() < 10) {
      tijd += this._startDatum.getHours() + ":0" + this._startDatum.getMinutes();
    } else {
      tijd += this._startDatum.getHours() + ":" + this._startDatum.getMinutes();
    }

    if (this._eindDatum.getHours() + ":" + this._eindDatum.getMinutes() !== this._startDatum.getHours() + ":" + this._startDatum.getMinutes()) {
      tijd += " - ";
      if (this._eindDatum.getMinutes() < 10) {
        tijd += this._eindDatum.getHours() + ":0" + this._eindDatum.getMinutes();
      } else {
        tijd += this._eindDatum.getHours() + ":" + this._eindDatum.getMinutes();
      }
    }
    return tijd;

  }

  toCalendarEvent() {
    return {
      start: this.startDatum,
      end: this.eindDatum,
      title: this.titel,
      color: {
        primary: this.kleur,
        secondary: this.kleur
      },
      personen: this.personen,
      beschrijving: this.beschrijving,
      id: this.id,
      gesprekId: this.gesprekId
    }
  }

  toString(): string {
    return `${this.getTijdAlsString()}: ${this._beschrijving} &nbsp &nbsp &nbsp[ ${this._personen} ]`;
  }

  public toJSON() {
    return {
      titel: this.titel,
      kleur: this.kleur,
      startDatum: this.startDatum,
      eindDatum: this.eindDatum,
      beschrijving: this.beschrijving,
      personen: this.personen,
      gesprekId: this.gesprekId,
      _id: this.id
    }
  }
}

export enum SoortActiviteit {
  nietWederkerend = 0,
  wekelijks = 1,
  maandelijks = 2
}
