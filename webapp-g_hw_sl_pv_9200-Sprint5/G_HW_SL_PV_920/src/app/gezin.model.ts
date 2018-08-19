import { Gebruiker } from './gebruiker.model';
import { Kost } from './kosten/kost.model';
import { Activiteit } from './activiteit.model';
import { Gesprek } from './berichten/gesprek.model';

export class Gezin {

  constructor(
    private _naam: string,
    private _gezinsleden: Gebruiker[],
    private _activiteiten: Activiteit[],
    private _kosten: Kost[],
    private _gesprekken: Gesprek[],
    private _id?: string
  ) {
  }

  static fromJSON(json): Gezin {
    //console.log("Gezin model json: ");
    //console.log(json);
    const rec = new Gezin(
      json.naam,
      json.gezinsLeden.map(g => Gebruiker.fromJSON(g)),
      json.activiteiten.map(a => Activiteit.fromJSON(a)),
      json.kosten.map(k => Kost.fromJSON(k)),
      json.gesprekken.map(g => Gesprek.fromJSON(g))
    );

    rec._id = json._id;
    return rec;
  }

  get naam() {
    return this._naam;
  }

  get id() {
    return this._id;
  }

  get gesprekken() {
    return this._gesprekken;
  }

  voegLidToe(gezinslid: Gebruiker) {
    this._gezinsleden.push(gezinslid);
  }

  get gezinsleden() {
      return this._gezinsleden;
  }

  geefGezinslid(id:string) {
      return this.gezinsleden.find(lid => lid.id === id);
  }

  geefKleurGezinslid(id: string):string {
      return this.geefGezinslid(id).kleur;
  }

}
