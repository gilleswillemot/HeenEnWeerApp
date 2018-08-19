import { Observable } from 'rxjs';
import { Response, Http } from '@angular/http';
import { Injectable } from '@angular/core';
import { Activiteit } from '../activiteit.model';
import { testActiviteiten } from '../testActiviteiten';
import { AuthenticationService } from '../user/authentication.service';
import { GebruikerService } from '../gebruiker.service';
/*
  TROUBLESHOOTING: kijk eerst of de fout niet ligt aan het feit dat het een CalendarEvent object is
  idpv een Activiteit, of andersom!
*/
@Injectable()
export class ActiviteitService {

  private activiteitenLijst = testActiviteiten;
  private _url: string = "/API/activiteit";

  constructor(private http: Http, private auth: AuthenticationService, private gebruikerService: GebruikerService) { }

  get activiteiten(): Observable<Activiteit[]> {
    let userId;
    this.auth.user$.subscribe(item => userId = item);

    return this.http.get(`${this._url}/${userId}`)
      .map(response => response.json().map(item => Activiteit.fromJSON(item)));
  }

  verwijderActiviteit(activiteit) {
    let userId;
    this.auth.user$.subscribe(item => userId = item);

    this.http.delete(`${this._url}/${userId}/${activiteit.id}`).subscribe();
  }

  getActiviteitWithId(activiteit): Observable<Activiteit> {
    let userId;
    this.auth.user$.subscribe(item => userId = item);

    return this.http.get(`${this._url}/${userId}/${activiteit.id}`)
      .map(response => response.json()).map(item => Activiteit.fromJSON(item))
      .catch(this.handleError);
  }

  voegNieuweActiviteitToe(nieuweActiviteit: Activiteit) {
    let userId;
    this.auth.user$.subscribe(item => userId = item);
    return this.http.post(`${this._url}/${userId}`, { activiteit: nieuweActiviteit })
      .map(response => response.json()).map(item => Activiteit.fromJSON(item))
      .catch(this.handleError);
  }

  wijzigActiviteit(gewijzigdeActiviteit: Activiteit): Observable<Activiteit> {
      let userId;
      this.auth.user$.subscribe(item => userId = item);
      console.log(gewijzigdeActiviteit);
    //TODO activiteit terugsturen om toe te voegen aan lijst (customEvents) of dat daar meteen doen zonder return
    return this.http.put(`${this._url}/${userId}/${gewijzigdeActiviteit.id}`, { activiteit: gewijzigdeActiviteit })
      .map(response => response.json()).map(item => Activiteit.fromJSON(item))
      .catch(this.handleError);
  }


  set activiteiten(activiteiten: Observable<Activiteit[]>) {
    this.activiteiten = activiteiten;
  }

  getActiviteitenVanGezin(gezinId: number): Observable<Activiteit[]> {
    this.http.get(`api/GetActiviteiten/${gezinId}` /*+ gezinId*/).map(response =>
      response.json().map(item =>
        new Activiteit(item.titel, new Date(item.StartDatum), new Date(item.EindDatum), item.Kleur, item.Beschrijving, item.Personen, item.ActiviteitId)
      )
    ).catch(this.handleError);
    return this.activiteiten;
  }

  getAlleActiviteitenAlsActiviteitObjectenVanGezin(gezinId: number): Activiteit[] {
    let test: Activiteit[];
    this.getActiviteitenVanGezin(gezinId).subscribe(response => {
      test = response;
    });
    return test;
  }

  private handleError(error: Response) {
    console.log(error);
    return Observable.throw(error.json().error || 'Server error');
  }

  //voegNieuweActiviteitToe(nieuweActiviteit) {
  //  //TODO activiteit toevoegen aan backend, daar een id laten creeren
  //  //TODO vervolgens teruggeven MET een id
  //  nieuweActiviteit.id = new Date().getTime(); /*nieuw id creeren?*/
  //    return nieuweActiviteit;
  //}
}
