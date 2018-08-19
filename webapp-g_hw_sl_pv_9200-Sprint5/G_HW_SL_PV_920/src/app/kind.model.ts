import{ Gebruiker } from './gebruiker.model';

export class Kind extends Gebruiker{
//  bloedgroep: String,
//  allergieen: [String],
//  geboortedatum: Date,
//  hobbys: [String]

  constructor(
    voornaam2: string,
    familienaam: string,
    dsfsdf : string
    //kleur: string,
    //private _bloedgroep: string,
    //private _allergieen: string[],
    //private _geboortedatum: Date,
    //private _hobbys: string[]
  ) {
     super("",voornaam2,familienaam,"","",["",""]);
  }

  test() {
    let kind = new Kind("naam", "naam","test");
   // kind.te;
    
  }
}
