import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GebruikerService } from '../../gebruiker.service';
import { Gebruiker } from '../../gebruiker.model';
import { IColorPickerConfiguration } from "../../color-picker/interfaces/color-picker-config.interface";
import { colors } from '../../colors';
import { FormGroup, FormControl } from '@angular/forms';
import { AuthenticationService } from '../../user/authentication.service';

@Component({
  selector: 'app-profiel',
  templateUrl: './profiel.component.html',
  styleUrls: ['./profiel.component.css']
})
export class ProfielComponent implements OnInit {
  aanpassen: boolean = false;
  andereGebruiker;
  eigenProfiel: boolean = false; //false is ander profiel, true is eigen profiel
  gebruiker: Gebruiker;
  aanpassingen: FormGroup;
  huidigGezin;
  _color: string;
  availableColors: string[] = [
    colors.darkblue,
    colors.lightblue,
    colors.darkgreen,
    colors.lightgreen,
    colors.darkpink,
    colors.lightpink,
    colors.darkred,
    colors.lightred,
    colors.darkyellow,
    colors.lightyellow
  ];
  pickerOptions: IColorPickerConfiguration = {
    width: 200,
    height: 30,
    borderRadius: 8
  };
  constructor(private route: ActivatedRoute, private gebruikerService: GebruikerService, private auth: AuthenticationService) { }

  ngOnInit() {
    const paramId: string = this.route.snapshot.params['gebruikerId'];
    if (paramId !== undefined) {
      let userId;
      this.auth.user$.subscribe(item => userId = item);
      if (userId === paramId) {
        this.gebruikerService.ingelogdeGebruiker.subscribe((item: Gebruiker) => {
          this.gebruiker = item;
          this.color = this.gebruiker.kleur;
        });
        this.eigenProfiel = true;
      } else {
        this.gebruikerService.getGebruikerMetId(paramId).subscribe(gebruiker => {
          this.gebruiker = gebruiker;
          this.color = gebruiker.kleur;
        });
        this.eigenProfiel = false;
      }

    } else {
      this.gebruikerService.ingelogdeGebruiker.subscribe((item: Gebruiker) => {
        this.gebruiker = item;
        this.color = this.gebruiker.kleur;
      });
      this.eigenProfiel = true;
    }


    //this.gebruikerService.huidigGezin.subscribe(item => {
    //  console.log("huidig gezin bij profiel: " + item);
    //  this.huidigGezin = item;
    //});

    this.aanpassingen = new FormGroup({
      naam: new FormControl()
    });


  }

  set color(kleur) {
    console.log(kleur);
    kleur === undefined ? this._color = "buttonface" : this._color = kleur;
    console.log(this._color);
  }


  //get color() {
  //  if (this._color == undefined)
  //    retur
  //}

  onSubmit() {
    this.gebruikerService.pasKleurAan(this._color).subscribe();
    this.gebruiker.kleur = this._color;
    this.aanpassenToggle();
    console.log(this._color);
  }

  aanpassenToggle() {
    this.aanpassen = !this.aanpassen;
  }
  onsubmit() {

  }

}
