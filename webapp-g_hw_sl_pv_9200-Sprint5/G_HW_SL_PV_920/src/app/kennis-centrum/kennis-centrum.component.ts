import { Component, OnInit } from '@angular/core';
import { Artikel } from './artikel.model';
import { ArtikelDataService } from './artikel-data.service';
import { LoginObject } from '../user/loginObject.model';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-kennis-centrum',
  templateUrl: './kennis-centrum.component.html',
  styleUrls: ['./kennis-centrum.component.css']
})
export class KennisCentrumComponent implements OnInit {

  private _articles: Artikel[];
  private _isModerator: boolean;
  private _selectedArticle: Artikel;
  private _noArticles: boolean = false;

  constructor(private _artikelService: ArtikelDataService, private router: Router) { }

  ngOnInit() {
    // controleren of gebruiker moderator is (om artikel te creëren)
    const raw = JSON.parse(localStorage.getItem('currentUser'));
    const user = new LoginObject(raw.id, raw.token, raw.gezinId, raw.isModerator);
    this._isModerator = user.isModerator;

    // artikels ophalen
    this._artikelService.articles.subscribe(articles => {
      console.log(articles);
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
    this._artikelService.removeArticle(this._selectedArticle).subscribe(removedArticle =>
      delete this._selectedArticle);
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

  get isModerator() {
    return this._isModerator;
  }
}
