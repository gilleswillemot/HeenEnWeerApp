import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Artikel } from './artikel.model';
import { Observable } from 'rxjs/Observable';
import { map } from 'rxjs/operators';

@Injectable()
export class ArtikelDataService {

  private readonly _appUrl = '/API/artikel/';

  constructor(private http: HttpClient) {}

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
    return this.http
      .post(`${this._appUrl}`, article)
      .pipe(map(Artikel.fromJSON));
  }

  removeArticle(rec: Artikel): Observable<Artikel> {
    console.log("in remove article in artikelDataService, rec id: " + rec.id);
    return this.http
      .delete(`${this._appUrl}${rec.id}`)
      .pipe(map(Artikel.fromJSON));
  }

  editArticle(articleId: String, rec: Artikel): Observable<Artikel> {
    return this.http.put(`${this._appUrl}${articleId}`, rec)
    .pipe(map(Artikel.fromJSON));
  }

  // addTextToArticle(text: String, rec: Artikel): Observable<Artikel> {
  //   const theUrl = `${this._appUrl}/article/${rec.id}/text`;
  //   return this.http.post(theUrl, text).pipe(map(Artikel.fromJSON));
  // }

  // getArticle(id: string): Observable<Artikel> {
  //   return this.http
  //     .get(`${this._appUrl}/article/${id}`)
  //     .pipe(map(Artikel.fromJSON));
  // }
}
