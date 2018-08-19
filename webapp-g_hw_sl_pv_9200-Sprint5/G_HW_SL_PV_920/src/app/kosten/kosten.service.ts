import { Injectable } from '@angular/core';
import { Kost } from './kost.model';
import { AuthenticationService } from '../user/authentication.service';
import { Response, Http } from '@angular/http';
import { Observable } from 'rxjs';



@Injectable()
export class KostenService {

  private _url = '/API/kost';

  constructor(private http: Http, private auth: AuthenticationService) { }


  get kosten(): Observable<Kost[]> {
    let userId;
    this.auth.user$.subscribe(item => userId = item);

    return this.http.get(`${this._url}/${userId}`)
      .map(response => response.json().map(item => Kost.fromJSON(item)));
  }

  voegNieuweKostToe(kost): Observable<Kost> {
    let userId;
    this.auth.user$.subscribe(item => userId = item);
    return this.http.post(`${this._url}/${userId}`, { kost: kost })
      .map(response => response.json()).map(item => Kost.fromJSON(item));
  }

  verwijderKost(kostId) {
    let userId;
    this.auth.user$.subscribe(item => userId = item);

    this.http.delete(`${this._url}/${userId}/${kostId}`).subscribe();
  }

  wijzigKost(gewijzigdeKost: Kost): Observable<Kost> {
    let userId;
    this.auth.user$.subscribe(item => userId = item);
    console.log(gewijzigdeKost);
    //TODO Kost terugsturen om toe te voegen aan lijst (customEvents) of dat daar meteen doen zonder return
    return this.http.put(`${this._url}/${userId}/${gewijzigdeKost.id}`, { kost: gewijzigdeKost })
      .map(response => response.json()).map(item => Kost.fromJSON(item));
   
  }


  kostenLijst = [
    new Kost("Medicatie", "Inge", "0", ["Joost", "Jan"], new Date(2017, 10, 10), 15, "Gekocht bij apotheker Janssens.", "medisch"),
    new Kost("Voetbalschoenen", "Mark", "0", ["Jan"], new Date(2017, 7, 21), 59, "De schoenen zijn maat 35", "sport"),
    new Kost("schoolboeken", "Inge", "0", ["Joost", "Jan"], new Date(2017, 10, 7), 89, "Alle schoolboeken gekocht", "school"),
    new Kost("kladblokken", "Mark", "0", ["Joost"], new Date(2017, 7, 5), 5, "Stond in zijn agenda dat hij een kladboek nodig had", "school"),
    new Kost("Doktersbezoek", "Inge", "0", ["Jan"], new Date(2017, 1, 30), 50, "Jan had buikpijn, het bleek niets te zijn.",  "medisch"),
    new Kost("Doktersbezoek", "Inge", "0", ["William"], new Date(2017, 3, 5), 43, "Medicatie voor william zijn verkoudheid",  "medisch"),
    new Kost("Nieuwe broek", "Inge", "0", ["Jan"], new Date(2017, 10, 7), 49, "Nieuwe jeansbroek gekocht bij Zalando",  "cadeau"),
    new Kost("Schoolboek Biologie", "0", "Mark", ["William"], new Date(), 24, "Aankoop schoolboek Biologie.",  "school"),
    new Kost("Kermis", "Mark", "0", ["William", "Jan", "Joost"], new Date(2017, 11, 28), 15, "",  "school")
  ];

  selectedKost: Kost;
}
