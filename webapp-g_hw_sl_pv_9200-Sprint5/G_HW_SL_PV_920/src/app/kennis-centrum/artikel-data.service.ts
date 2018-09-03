import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Artikel } from './artikel.model';
import { Observable } from 'rxjs/Observable';
import { map } from 'rxjs/operators';
import { Headers } from '@angular/http';
import { AuthenticationService } from '../user/authentication.service';

@Injectable()
export class ArtikelDataService {

  private readonly _appUrl = '/API/artikel/';

  constructor(private http: HttpClient, private auth: AuthenticationService) {}

  get articles(): Observable<Artikel[]> {
    return this.http
      .get(`${this._appUrl}`)
      .pipe(map((list: any[]): Artikel[] => list.map(Artikel.fromJSON)));
  }

  getArticle(articleId: String): Observable<Artikel> {
    return this.http
      .get(`${this._appUrl}${articleId}`)
      .pipe(map(Artikel.fromJSON));
  }

  createArticle(article: Artikel): Observable<Artikel> {
    let headers = this.createAuthorizationHeader();
    return this.http
      .post(`${this._appUrl}`, article, { headers })
      .pipe(map(Artikel.fromJSON));
  }

  removeArticle(rec: Artikel): Observable<Artikel> {
    let headers = this.createAuthorizationHeader();
    return this.http
      .delete(`${this._appUrl}${rec.id}`, { headers })
      .pipe(map(Artikel.fromJSON));
  }

  editArticle(articleId: String, rec: Artikel): Observable<Artikel> {
    let headers = this.createAuthorizationHeader();
    return this.http.put(`${this._appUrl}${articleId}`, rec, { headers })
    .pipe(map(Artikel.fromJSON));
  }

  createAuthorizationHeader(): HttpHeaders{
    let headers = new HttpHeaders().set('Content-Type', 'application/json')
    .set('authorization', 'Bearer ' + this.auth.token);
    return headers;
  }
}
