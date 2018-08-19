export class Gebruiker {

  constructor(
    private _email: string,
    private _voornaam: string,
    private _familienaam: string,
    private _kleur: string,
    private _huidigGezinId: string,
    private _gezinnenIdLijst: string[],
    private _notificaties?: string[][],
    private _id?: string) { }

  static fromJSON(json): Gebruiker {
    //console.log(json);
    const rec = new Gebruiker(json.username, json.voornaam, json.familienaam, json.kleur,
      json.huidigGezinId, json.gezinnenIdLijst);
    rec._id = json._id;
    return rec;
  }

  get email() {
    return this._email;
  }

  get huidigGezinId() {
    return this._huidigGezinId;
  }

  get kleur() {
    return this._kleur;
  }

  get voornaam() {
    return this._voornaam;
  }

  get familienaam() {
    return this._familienaam;
  }

  get volledigeNaam() {
    return this._voornaam + " " + this._familienaam;
  }

  get id(): string {
    return this._id;
  }


  heeftGezin(): boolean {
    return (this.huidigGezinId !== undefined);
  }

  public toJSON() {
    return {
      username: this.email,
      voornaam: this.voornaam,
      familienaam: this.familienaam,
      kleur: this.kleur,
      huidigGezinId: this.huidigGezinId,
      gezinnenIdLijst: this._gezinnenIdLijst,
      _id: this.id
    }
  }
}
