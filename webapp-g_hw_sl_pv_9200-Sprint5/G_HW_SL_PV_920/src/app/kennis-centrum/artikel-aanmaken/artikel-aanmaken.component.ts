import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators, FormControl, EmailValidator } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { map } from 'rxjs/operators';
import { Location } from '@angular/common';
import 'rxjs/add/observable/of';
import { ArtikelDataService } from '../artikel-data.service';
import { Artikel } from '../artikel.model';

@Component({
  selector: 'app-artikel-aanmaken',
  templateUrl: './artikel-aanmaken.component.html',
  styleUrls: ['./artikel-aanmaken.component.css']
})
export class ArtikelAanmakenComponent implements OnInit {

  public newArticle: FormGroup;
  public errorMsg: string;
  private _articleToEdit: Artikel;
  private _editArticle: boolean;

  constructor(private fb: FormBuilder, private artikelDataService: ArtikelDataService, private router: Router,
    private _location: Location, private _route: ActivatedRoute) { }

  ngOnInit() {
    this.prepareFormGroup();
    let articleId;
    this._route.queryParams.subscribe(params => {
      articleId = params['articleId'];
      this._editArticle = articleId ? true : false;

      if (this._editArticle) {
        console.log("has param");
        //  this._articleToEdit = params['record'];
        console.log(this._articleToEdit);
        this.artikelDataService.getArticle(articleId).subscribe(
          article => {
            this._articleToEdit = article;
            let text = this._articleToEdit.text.replace(/<br \/>/g, "\n");

            this.newArticle.patchValue({
              title: article.title ? this._articleToEdit.title : "",
              text: article.text ? text : "",
              author: article.author ? this._articleToEdit.author : "",
              source: article.source ? this._articleToEdit.source : ""
            });
          });
      }
      else {
        console.log("has no param");
        this._articleToEdit
      }
    });
    //
    // this._route.queryParams.subscribe(params => { this._articleToEdit = Artikel.fromJSON(articleString);
    // this._articleToEdit = JSON.parse(params['record']) as Artikel;

  }

  get editArticle() {
    return this._editArticle;
  }


  prepareFormGroup() {
    this.newArticle = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(3)]],
      text: ['', [Validators.required, Validators.minLength(4)]],
      author: ['', [Validators.required, Validators.minLength(4)]],
      source: ['']
    });
  }

  onSubmit() {
    let title = this.newArticle.value.title;
    let author = this.newArticle.value.author;
    let text = this.newArticle.value.text;
    let source = this.newArticle.value.source;

    let text2 = text.replace(/\n/g, "<br />");
    console.log(text);
    console.log(text2);

    const article = new Artikel(title, text2, new Date(), author, source);

    if (this.editArticle) {
      this.artikelDataService
        .editArticle(this._articleToEdit.id, article)
        .subscribe(
          val => {
            if (val) {
              this.router.navigate(['/kennisCentrum']);
            }
          },
          (error: HttpErrorResponse) => {
            this.errorMsg = `Error ${
              error.status
              } while trying to update article ${
              error.error
              }`;
          });
    }
    else {
      this.artikelDataService
        .createArticle(article)
        .subscribe(
          val => {
            if (val) {
              this.router.navigate(['/kennisCentrum']);
            }
          },
          (error: HttpErrorResponse) => {
            this.errorMsg = `Error ${
              error.status
              } while trying to create article ${
              error.error
              }`;
          });
    }
  }

  get title(): string {
    return this.newArticle.get("title").value;
  }
}








