import { Component, OnInit } from '@angular/core';
import { GebruikerService } from '../gebruiker.service';
import { Gebruiker } from '../gebruiker.model';

@Component({
  selector: 'app-gezin',
  templateUrl: './gezin.component.html',
  styleUrls: ['./gezin.component.css']
})
export class GezinComponent implements OnInit {

  gezin;
  ingelogdeGebruiker;
  voogdOuder;

  constructor(private gebruikerService: GebruikerService) { }

  ngOnInit() {

    this.gebruikerService.ingelogdeGebruiker.subscribe((item: Gebruiker) => {
      console.log(item);
      this.ingelogdeGebruiker = item;
      this.voogdOuder = this.ingelogdeGebruiker.voornaam + " "+this.ingelogdeGebruiker.familienaam;
    });

    this.gebruikerService.huidigGezin.subscribe(item => {
      console.log(item);
      this.gezin = item;
    });

    

  }

}
