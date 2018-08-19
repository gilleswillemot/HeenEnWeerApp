import { Bericht } from './bericht.model';
import { Gebruiker } from '../gebruiker.model';
import { Activiteit } from "../activiteit.model";


export class Gesprek {

  constructor(private _naam: string,
    //private _deelnemerIdLijst: number[],
    private _deelnemers: Gebruiker[],
    private _berichten?: Bericht[],
    //private idAuteurLaatsteBericht?: number,
    //private _aantalNieuweBerichten?: number, /*private _activiteitId?: number,*/
    private _activiteit?: any,
    private _id?: string) {
  }

  static fromJSON(json): Gesprek {
    const rec = new Gesprek(json.naam, json.deelnemers.map(g => Gebruiker.fromJSON(g)),
      json.berichten.map(b => Bericht.fromJSON(b)), json.activiteit);
    rec._id = json._id;
    return rec;
  }

  aantalNieuweBerichten(ingelogdeGebruikerId) {
    //misschien kunnen we het laatste index nemen maar persistentie kan dit niet garanderen:
    //let nieuwsteBericht = this.berichten[0];
    //  this.berichten.forEach(bericht => {
    //      if (bericht.datum.getTime() > nieuwsteBericht.datum.getTime()) nieuwsteBericht = bericht;
    //  });
    //if (nieuwsteBericht.auteursId === ingelogdeGebruikerId) {
    //    return 0;
    //} else {

    //}
    //return ingelogdeGebruikerId === this.idAuteurLaatsteBericht ? 0 : this._aantalNieuweBerichten;

  }

  gebruikersAlsString(): string {
    return this._deelnemers.map(deelnemer => deelnemer.voornaam).join(", ");
  }

  voegGebruikerToeAanGesprekTijdelijk(gebruiker) {
    this._deelnemers.push(gebruiker);
  }

  tijdelijkSetId(id: string) {
    this._id = id;
  }

  heeftActiviteit(): boolean {
    return this._activiteit !== undefined;
  }

  get activiteitId() {
    return this._activiteit.id;
  }

  get activiteit() {
      return this._activiteit;
  }

  get naam() {
    return this._naam;
  }

  set naam(naam:string) {
    this._naam = naam;
  }

  get id() {
    return this._id;
  }

  get berichten() {
    return this._berichten;
  }

  //get deelnemerIdLijst() {
  //  return this._deelnemerIdLijst;
  //}

  get deelnemers() {
    return this._deelnemers;
  }
  //get deelnemers() {
  //    return this._deelnemers;
  //}

  toGesprekInformatie() {
    return {
      gespreksnaam: this._naam,
      id: this.id,
      heeftActiviteit: this.activiteit !== undefined
    }
  }

  voegBerichtToe(bericht: Bericht) {
    //TODO een id toevoegen aan het bericht omdat de date vh bericht niet genoeg is om het te identificeren
    //TODO als uniek
    this._berichten.push(bericht);
    //if (this.idAuteurLaatsteBericht bericht.auteursId) {
    //  this.idAuteurLaatsteBericht = bericht.auteursId;
    //  this._aantalNieuweBerichten = 1;
    //} else //zender van bericht is gelijk aan zender van het laatste bericht
    //  this._aantalNieuweBerichten++;
  }

//  rechtsWeergeven(auteursId: string): boolean {
//   // return this._deelnemers.indexOf(this._deelnemers.find(d => d.id === auteursId)) % 2 === 0;
//    return this
//  }
}
