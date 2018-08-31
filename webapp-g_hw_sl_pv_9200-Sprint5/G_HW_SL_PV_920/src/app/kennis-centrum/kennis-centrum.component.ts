import { Component, OnInit } from '@angular/core';
import { Artikel } from './artikel.model';
import { ArtikelDataService } from './artikel-data.service';
import { LoginObject } from '../user/loginObject.model';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthenticationService } from '../user/authentication.service';
import { Observable } from 'rxjs/Observable';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-kennis-centrum',
  templateUrl: './kennis-centrum.component.html',
  styleUrls: ['./kennis-centrum.component.css']
})
export class KennisCentrumComponent implements OnInit {

  private _articles: Artikel[];
  private _selectedArticle: Artikel;
  private _noArticles: boolean = false;
  errorMsg: string;

  constructor(private _artikelService: ArtikelDataService, private router: Router, private _authenticationService:
  AuthenticationService) { }

  ngOnInit() {
    // artikels ophalen
    this._artikelService.articles.subscribe(articles => {
      this._articles = articles;
      // eerste artikel selecteren en klaarzetten voor weergave
      if (this._articles.length != 0) {
        this._selectedArticle = this._articles[0];
      }
      else {
        this._noArticles = true;
      }
    });
  }

  public deleteSelectedArticle(): void {
    console.log("deleting article with id: " + this._selectedArticle.id);
    this._artikelService.removeArticle(this._selectedArticle).subscribe(
      item => (this._articles = this._articles.filter(val => item.id !== val.id),
    this._selectedArticle = this.articles[0]),
      (error: HttpErrorResponse) => {
        this.errorMsg = `Error ${error.status} while removing article with title ${
          this._selectedArticle.title }: ${error.error}`;
      }
    );
  }

  public articleToJson() {
    return this._selectedArticle.toJSON();
  }

  public editSelectedArticle() {
    /*
        <!-- routerLink="/artikelAanmaken" queryParams="{record: articleToJson()}">  
        <!--queryParams="{record: selectedArticle | json}"-->*/
    this.router.navigate(['/artikelAanmaken'], { queryParams:
      // { article: this._selectedArticle.toJSON() }
    { articleId: this._selectedArticle.id }
    });
  }

  public selectArticle(article: Artikel): void {
    this._selectedArticle = article;
  }

  get noArticles(): boolean {
    return this._noArticles;
  }

  get selectedArticle(): Artikel {
    return this._selectedArticle;
  }

  get articles() {
    return this._articles;
  }

  get isModerator(): Observable<boolean> {
   return this._authenticationService.isModerator$;
  }
}
