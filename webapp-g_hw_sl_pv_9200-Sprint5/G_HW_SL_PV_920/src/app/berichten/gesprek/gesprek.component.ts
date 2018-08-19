import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, Validators, FormBuilder, FormArray } from '@angular/forms';
import { Bericht } from '../bericht.model';
import { Gebruiker } from '../../gebruiker.model';
import { GebruikerService } from '../../gebruiker.service';

@Component({
  selector: 'app-gesprek',
  templateUrl: './gesprek.component.html',
  styleUrls: ['./gesprek.component.css']
})
export class GesprekComponent implements OnInit {
  @Input() huidigGesprek;
  ingelogdeGebruiker;
  @Output() nieuwBericht = new EventEmitter<Bericht>();
  personen;
  toeTeVoegenBericht: FormGroup;
  ingelogdeGebruikerId: string;

  constructor(private fb: FormBuilder, private gebruikerService: GebruikerService) { }
  //TODO personen toevoegen aan gesprek implementeren.
  ngOnInit() {

    this.gebruikerService.ingelogdeGebruiker.subscribe(gebruiker => {
      this.ingelogdeGebruiker = gebruiker;
        this.ingelogdeGebruikerId = gebruiker.id;
    });

    this.personen = this.huidigGesprek.deelnemers;
    console.log(this.personen);
    this.toeTeVoegenBericht = this.fb.group({
      berichtInhoud: [""]
    });
  }

  rechtsWeergeven(auteursId: string) {
    return this.ingelogdeGebruikerId === auteursId;
  }

  berichtKleur(auteursId: string) {
    if (this.personen.length < 1)
      return "#6699ff";
    else
      return this.personen.find(pers => pers.id === auteursId).kleur;
  }

  onSubmit() {
    //if (this.ingevoegdeActiviteit !== undefined) {
    //  nieuwBericht = new Bericht(25,
    //    1,
    //    "Wim",
    //    new Date(),
    //    this.toeTeVoegenBericht.value.berichtInhoud,
    //    '#60ff3d',
    //    this.ingevoegdeActiviteit.id
    //  );
    //} else {
    let nieuwBericht = new Bericht(
      this.ingelogdeGebruiker.id,
      this.ingelogdeGebruiker.voornaam,
      new Date(),
      this.toeTeVoegenBericht.value.berichtInhoud
    );
    //emitten naar parent
    this.nieuwBericht.emit(nieuwBericht);
    this.toeTeVoegenBericht.reset();
    //this.ingevoegdeActiviteit = undefined;
    //this.activiteitToevoegen = false;
  }
}/*Einde component*/
