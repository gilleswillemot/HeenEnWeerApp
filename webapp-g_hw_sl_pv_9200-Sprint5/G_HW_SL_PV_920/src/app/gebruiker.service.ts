import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Gebruiker } from './gebruiker.model';
import { Observable } from 'rxjs';
import { AuthenticationService } from "./user/authentication.service";
import 'rxjs/add/operator/map';
import { Gezin } from './gezin.model';
import { map } from 'rxjs/operators';

@Injectable()
export class GebruikerService {

  private gezinObservable: Observable<Gezin>;
  private _url = '/API/gebruiker';

  constructor(private http: Http, private auth: AuthenticationService) { }

  getGebruikerMetId(id): Observable<Gebruiker> {
    return this.http.get(`${this._url}/${id}`, id)
      .map(res => res.json()).map(item => Gebruiker.fromJSON(item));
  }

  getGebruikerMetEmail(email): Observable<Gebruiker> {
    return this.http.get(`${this._url}/email/${email}`)
      .map(res => res.json()).map(item => {
        if (item != undefined)
          return Gebruiker.fromJSON(item);
        else
          return undefined;
      });
  }

  get isModerator(): Observable<boolean>{
    let gebruikerId;
    this.auth.user$.subscribe(id => gebruikerId = id);
    return this.http.post(`${this._url}/isModerator`, { gebruikerId }).pipe(
      map((item: any) => {
        if (item.isModerator === 'true') {
          return true;
        } else {
          return false;
        }
      })
    );
  }

  get ingelogdeGebruiker(): Observable<Gebruiker> {
    let gebruikerId;
    this.auth.user$.subscribe(id => gebruikerId = id);
    return this.getGebruikerMetId(gebruikerId);
  }

  geefKleurGebruiker(id): string {
      let kleur: string = "";
      this.huidigGezin.subscribe((gezin: Gezin) => kleur = gezin.geefKleurGezinslid(id));
      return kleur;
  }

  get huidigGezin(): Observable<Gezin> {
    let gebruikerId;
    this.auth.user$.subscribe(id => gebruikerId = id);
    console.log(this.gezinObservable);
    if (this.gezinObservable === undefined) //zo moet het niet elke keer opnieuw opgeroepen worden
      this.gezinObservable = this.haalGezinUitDatabase();
    return this.gezinObservable;
  }

  //haalAlleGezinnenUitDatabase(): Observable<Gezin[]> {
  //  let gebruikerId;
  //  this.auth.user$.subscribe(id => gebruikerId = id);
  //  return this.http.get(`/API/gezin/${gebruikerId}/gezinnen`)
  //    .map(response => response.json().map(item => Gezin.fromJSON(item)));
  //}

  haalGezinUitDatabase(): Observable<Gezin> {
    let gebruikerId;
    this.auth.user$.subscribe(id => gebruikerId = id);
    return this.http.get(`/API/gezin/${gebruikerId}`, { headers: new Headers({ Authorization: `Bearer ${this.auth.token}` }) })
      .map(response => response.json()).map(item => Gezin.fromJSON(item));
  }

  maakGezinAan(gezin: Gezin): Observable<Gezin> {
    let gebruikerId;
    this.auth.user$.subscribe(id => gebruikerId = id);
    return this.http.post(`/API/gezin/nieuw/${gebruikerId}`, { gezin: gezin })
      .map(response => response.json()).map(item => Gezin.fromJSON(item));

  }

  pasKleurAan(kleur) {
    console.log(kleur);
    let gebruikerId;
    this.auth.user$.subscribe(id => gebruikerId = id);
    return this.http.post(`${this._url}/${gebruikerId}/kleur`, {kleur: kleur})
      .map(response => response.json()).map(item => Gebruiker.fromJSON(item));
  }

  geefGezinsledenExclusief() {//exclusief ingelogde gebruiker
    let userId;
    this.auth.user$.subscribe(item => userId = item);
    let gezinsleden;
    this.haalGezinUitDatabase().subscribe(item => {
      gezinsleden = item.gezinsleden;
    });

    return gezinsleden;
  }


  geefLijstVanGebruikersAlsString(gebruikersIdLijst) {
    let gebruikers;
    this.haalGezinUitDatabase().subscribe(gezin => {
      gebruikersIdLijst.forEach(id => {
        gebruikers.push(gezin.gezinsleden.find(lid => lid.id === id).voornaam);
      });
    });
    return gebruikers.map(gebruikerNaam => gebruikerNaam).join(", ");
  }
}
