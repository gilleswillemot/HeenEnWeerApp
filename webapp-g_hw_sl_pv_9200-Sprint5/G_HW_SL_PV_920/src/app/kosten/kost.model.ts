
export class Kost {

  constructor(private _naam: string,
    private _aanmaker: string,
    private _aanmakerId: string,
    private _betrokkenPersonen: string[],
    private _datum: Date,
    private _kost: number,
    private _beschrijving: string,
    private _categorie: string,
    private _id?: string
  ) { }

  static fromJSON(json): Kost {
    const rec = new Kost(json.naam, json.aanmaker, json.aanmakerId, json.betrokkenPersonen,
      new Date(json.datum), json.kost, json.beschrijving, json.categorie);
    rec._id = json._id;
    return rec;
  }

  get aanmaker() {
    return this._aanmaker;
  }

  get aanmakerId() {
    return this._aanmakerId;
  }

  get categorie() {
    return this._categorie;
  }

  get naam() {
    return this._naam;
  }

  get id() {
    return this._id;
  }

  get datum() {
    return this._datum;
  }

  get kost() {
    return this._kost;
  }

  get beschrijving() {
    return this._beschrijving;
  }

  get betrokkenPersonen() {
    return this._betrokkenPersonen;
  }

  datumToString() {
    return this._datum.toLocaleDateString();
  }

  getBetrokkenPersonenAlsString() {
    let result = "";

    this._betrokkenPersonen.forEach(persoon => {
      result += persoon + ", ";
    });

    return result.substring(0, result.length - 2);
  }

  getCategorieAsIcon() {
    if (this._categorie != undefined) {
      switch (this._categorie.toLowerCase()) {
        case 'sport':
          return 'fa fa-soccer-ball-o';
        case 'school':
          return 'fa fa-graduation-cap';
        case 'medisch':
          return 'fa fa-heartbeat';
        case 'cadeau':
          return 'fa fa-gift';
        default:
          return "";
      }
    }
    return "";
  }

  public toJSON() {
    return {
      naam: this.naam,
      aanmaker: this.aanmaker,
      aanmakerId: this.aanmakerId,
      betrokkenPersonen: this.betrokkenPersonen,
      datum: this.datum,
      kost: this.kost,
      beschrijving: this.beschrijving,
      categorie: this.categorie,
      _id: this.id
    }
  }
}
