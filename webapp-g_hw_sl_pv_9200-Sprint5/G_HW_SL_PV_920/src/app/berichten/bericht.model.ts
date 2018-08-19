import { differenceInCalendarDays } from "date-fns";

export class Bericht {

  constructor(private _auteursId: string,
    private _voornaam: string,
    private _datum: Date,
    private _inhoud: string,/*, private _kleur?: string,*/
    private _id?: string/*, private _activiteitId?: number*/)
  { }

  static fromJSON(json): Bericht {
    const rec = new Bericht(json.auteursId, json.voornaam, new Date(json.datum), json.inhoud);
    rec._id = json._id;
    return rec;
  }

  get id() {
    return this._id;
  }

  get datum() {
    return this._datum;
  }

  get inhoud() {
    return this._inhoud;
  }

  get auteursId() {
    return this._auteursId;
  }

  get voornaam() {
    return this._voornaam;
  }

  get berichtOuderdom() {
    return this._datum.toLocaleString('nl-NL', {day: 'numeric', month:'numeric', year:'numeric',  hour: '2-digit', minute: '2-digit' });
    //console.log("aantal dagen oud: " + differenceInCalendarDays(this._datum, new Date()));
    //return differenceInCalendarDays(this._datum, new Date());
    //TODO ouder als 1 dag, aantal dagen geleden weergeven idpv datum met uur + on hover het preciese uur.
  }
}
