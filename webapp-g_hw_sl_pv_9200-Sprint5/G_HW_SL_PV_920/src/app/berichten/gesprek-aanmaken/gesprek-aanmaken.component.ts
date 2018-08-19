import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, Validators, FormBuilder, FormArray } from "@angular/forms";
import { Gesprek } from "../gesprek.model";
import { Bericht } from "../bericht.model";
import { GebruikerService } from '../../gebruiker.service';
import { Gezin } from '../../gezin.model';

@Component({
  selector: 'app-gesprek-aanmaken',
  templateUrl: './gesprek-aanmaken.component.html',
  styleUrls: ['./gesprek-aanmaken.component.css']
})
export class GesprekAanmakenComponent implements OnInit {

  nieuwGesprek;
  //@Input() gezinsLeden;
  gezinsLedenExcl;
  //@Input() aangemeldeGebruiker;
  @Output() nieuwGesprek2 = new EventEmitter<Gesprek>();
  @Output() annuleren = new EventEmitter<boolean>();
  ingelogdeGebruiker;
  //get personen(): FormArray {
  //    return <FormArray>this.nieuwGesprek.get('personen');
  //}
  gezinsleden;

  constructor(private fb: FormBuilder, private gebruikerService: GebruikerService) { }

  ngOnInit() {
    //this.nieuwGesprek = this.fb.group({
    //  titel: [
    //    "", [/*Titel van gesprek.*/
    //      Validators.required, Validators.maxLength(20),
    //      Validators.minLength(2)
    //    ]
    //  ],
    //  personen: this.buildPersonen()
    //});
    this.gebruikerService.ingelogdeGebruiker.subscribe(gebruiker => {
      this.ingelogdeGebruiker = gebruiker;
      this.gebruikerService.haalGezinUitDatabase().subscribe((gezin: Gezin) => {
        this.gezinsleden = gezin.gezinsleden;
        this.gezinsLedenExcl = gezin.gezinsleden.filter(lid => 
          lid.id !== this.ingelogdeGebruiker.id
        ).map(lid => lid.volledigeNaam);
        console.log(this.gezinsLedenExcl);
        this.nieuwGesprek = this.fb.group({
          titel: [
            "", [/*Titel van gesprek.*/
              Validators.required, Validators.maxLength(30),
              Validators.minLength(2)
            ]
          ],
          personen: this.buildPersonen()
        });
      });
    });

    //this.nieuwGesprek.value.personen = this.gezinsLedenExcl;
  }

  buildPersonen() {
    const arr = this.gezinsLedenExcl.map(persoon => {
      return this.fb.control(true);
    });
    console.log(arr);
    return this.fb.array(arr);
  }

  get personen(): FormArray {
    return this.nieuwGesprek.get('personen') as FormArray;
  };


  gesprekAanmakenAnnuleren() {
    //TODO implementeren
    this.annuleren.emit(true);
  }

  maakPersoon(bool: boolean, id, naam): FormGroup {
    return this.fb.group({
      checked: [bool],
      id: [id],
      naam: [naam]
    });
  }

  onSubmit() {
    let berichten: Bericht[] = [];

    let nieuwGesprek = new Gesprek(
      this.nieuwGesprek.value.titel,
      this.getBetrokkenPersonen(),
      //this.getBetrokkenPersonen(),
      berichten
    );
    console.log(nieuwGesprek);

    this.nieuwGesprek2.emit(nieuwGesprek);
  }

  getBetrokkenPersonen() {
    let personen = [];
    for (var i = 0; i < this.gezinsLedenExcl.length; i++) {
      if (this.nieuwGesprek.value.personen[i]) {
        personen.push(this.gezinsleden.find(g => g.volledigeNaam === this.gezinsLedenExcl[i]).id);
      }
    }
    //personen.push(this.ingelogdeGebruiker.id);
    return personen;
  }

}
