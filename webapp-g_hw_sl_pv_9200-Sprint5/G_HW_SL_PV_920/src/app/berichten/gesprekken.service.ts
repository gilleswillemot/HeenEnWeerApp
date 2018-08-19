import { Injectable } from '@angular/core';
import { Bericht } from './bericht.model';
import { Gesprek } from './gesprek.model';
import { colors } from '../colors';
import { testGesprekken } from '../testGesprekken';
import { Response, Http } from '@angular/http';
import { GebruikerService } from '../gebruiker.service';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../user/authentication.service';



@Injectable()
export class GesprekkenService {

  private _url: string = '/API/gesprek';
  //TODO moeten gesprekken nog chronologisch gezet worden op datum na het ophalen uit backend?
  gesprekkenLijst: Gesprek[] = testGesprekken;
  //ingelogdeGebruiker;
  constructor(private http: Http, private auth: AuthenticationService
    , private gebruikerService: GebruikerService) {
    //  this.ingelogdeGebruiker = this.geb
  }

  get gesprekken(): Observable<Gesprek[]> {
    let userId;
    this.auth.user$.subscribe(item => userId = item);

    return this.http.get(`${this._url}/${userId}`)
      .map(response => response.json().map(item => Gesprek.fromJSON(item)));
  }

  voegNieuwGesprekToe(nieuwGesprek: Gesprek) {
    let userId;
    this.auth.user$.subscribe(item => userId = item);
    nieuwGesprek.deelnemers.push(userId);
    return this.http.post(`${this._url}/${userId}`, { gesprek: nieuwGesprek })
      .map(response => response.json()).map(item => Gesprek.fromJSON(item));
  }

  getGesprekMetId(gesprekId): Observable<Gesprek> {
    let userId;
    this.auth.user$.subscribe(item => userId = item);
    return this.http.get(`${this._url}/gesprek/${gesprekId}`)
      .map(response => response.json()).map(item => Gesprek.fromJSON(item));
  }

  verwijderGesprek(gesprekId) {
    let userId;
    this.auth.user$.subscribe(item => userId = item);
    this.http.delete(`${this._url}/${userId}/${gesprekId}`).subscribe();
  }

  voegBerichtToeAanGesprek(gesprekId, bericht): Observable<Bericht> {
    console.log(bericht);
    let userId;
    this.auth.user$.subscribe(item => userId = item);
    return this.http.post(`${this._url}/${gesprekId}/bericht`, { bericht: bericht })
      .map(response => response.json()).map(item => Bericht.fromJSON(item));
  }

  alleGesprekkengegevensOphalen(ingelogdeGebruikerId) {
    //TODO deze mapping moet in de backend gebeuren zodat niet alle gesprekken opgehaald moeten worden
    //TODO om het verkeer zo laag mogelijk te houden.
    //in de backend gesprek ophalen zonder berichten
    return this.gesprekkenLijst.map(gesprek => {

      return {
        gespreksnaam: gesprek.naam,
        id: gesprek.id,
        nieuweBerichten: gesprek.aantalNieuweBerichten(ingelogdeGebruikerId),
        isVerbondenMetActiviteit: gesprek.heeftActiviteit()
      }
    });
  }

  //voegGebruikerToeAanGesprek(gesprekId, gebruikerId) {
  //  let gesprek = this.getGesprek(gesprekId);
  //  gesprek.voegGebruikerToeAanGesprekTijdelijk(TestGezinsleden2.testGezinsleden[gebruikerId]);
  //  return gesprek;
  //}

  getGesprek(gesprekId): Gesprek {
    return this.gesprekkenLijst.find(gesprek => gesprek.id === gesprekId);

  }


  geefGesprek(gesprekId: string, ingelogdeGebruiker): Gesprek {
    //ingelogde gebruiker op index 0 zodat die rechts wordt weergegeven in chat.

    let gesprek = this.getGesprek(gesprekId);
    if (gesprek !== undefined) {
      let deelnemers = gesprek.deelnemers;
      let index = deelnemers.indexOf(ingelogdeGebruiker);
      if (index !== 0) {
        let gebruiker = deelnemers[0];
        deelnemers[0] = ingelogdeGebruiker;
        deelnemers[index] = gebruiker;
      }
    }
    return gesprek;
  }

  voegNieuwBerichtToe(gespreksId, nieuwBericht) {
    this.getGesprek(gespreksId).berichten.push(nieuwBericht);
    return nieuwBericht;
  }

  //GESPREK KENT NU activiteit en een activiteit heeft een gesprekId
  //  geefGesprekMetActiviteitId(activiteitId: string) {
  //    //return this.http.get(`api/activiteit/${activiteitId}`)
  //    //  .map(response => response.json().map(item =>Gesprek.fromJSON(item)));
  //  }
  //  geefGesprekViaActiviteitId(activiteitId: string) {
  //    let ggesprek: Gesprek;
  //    this.gesprekkenLijst.forEach(gesprek =>
  //    {
  //      if (gesprek.heeftActiviteit()) {
  //        if (gesprek.activiteitId === activiteitId) ggesprek = gesprek;
  //      } 
  //    });
  //    return ggesprek;
  //  }

  geefLaatsteGesprek(gesprekId): Observable<Gesprek> {

    return this.getGesprekMetId(gesprekId);

    //misschien kunnen we een gesprek een boolean "isLaatsteGesprek geven om het zoeken te vergemakkelijken".
    //let laatsteGesprek: Gesprek = this.gesprekkenLijst[0];
    //let max: Date = laatsteGesprek.berichten[0].datum;
    //this.gesprekkenLijst.forEach(gesprek =>
    //  gesprek.berichten.forEach(bericht => {
    //    let datum: Date = bericht.datum;
    //    if (datum.getTime() > max.getTime()) {
    //      max = datum;
    //      laatsteGesprek = gesprek;
    //    }
    //  }));
    //return laatsteGesprek;
    //      return this.gesprekkenLijst[0];
  }

  verwijderGesprek2(gesprekId) {
    console.log(this.gesprekkenLijst.length);
    this.gesprekkenLijst.splice(this.gesprekkenLijst.indexOf
      (this.getGesprek(gesprekId)),
      1);
    console.log(this.gesprekkenLijst.length);

  }
}
