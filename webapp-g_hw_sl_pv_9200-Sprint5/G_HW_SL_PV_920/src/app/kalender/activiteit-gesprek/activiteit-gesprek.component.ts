import { Component, OnInit, Input } from '@angular/core';
import { GesprekkenService } from '../../berichten/gesprekken.service';
import { GebruikerService } from '../../gebruiker.service';
import { ActiviteitService } from '../activiteit.service';
import { Gesprek } from '../../berichten/gesprek.model';
import { Bericht } from '../../berichten/bericht.model';
import { Gebruiker } from '../../gebruiker.model';
import { Activiteit } from '../../activiteit.model';

@Component({
  selector: 'app-activiteit-gesprek',
  templateUrl: './activiteit-gesprek.component.html',
  styleUrls: ['./activiteit-gesprek.component.css']
})
export class ActiviteitGesprekComponent implements OnInit {
  //@Input() activiteitId: string;
  @Input() geselecteerdeActiviteit: Activiteit;
  gesprek: Gesprek;
  gesprekGevonden: boolean;
  gebruikersAlsString: string;
  gesprekAanmaken: boolean = false;
  ingelogdeGebruiker: Gebruiker;
  gesprekId: string;
  constructor(private gesprekkenService: GesprekkenService, private gebruikerService: GebruikerService,
    private activiteitService: ActiviteitService) { }

  ngOnInit() {
    this.gesprekId = this.geselecteerdeActiviteit.gesprekId;
    this.gebruikerService.ingelogdeGebruiker.subscribe(gebruiker => this.ingelogdeGebruiker = gebruiker);
    //console.log("activiteitid: " + this.activiteitId);
    console.log(this.geselecteerdeActiviteit.gesprekId);
    //this.gesprek = this.gesprekkenService.geefGesprekViaActiviteitId(this.activiteitId);
    if (this.gesprekId !== undefined) {
      this.gesprekkenService.getGesprekMetId(this.gesprekId).subscribe((gesprek: Gesprek) => {
        this.gesprek = gesprek;
        this.gesprekGevonden = true;
        this.gebruikersAlsString = this.gesprek.gebruikersAlsString();
        console.log(gesprek);
      });

    } else //er is nog geen gesprek aangemaakt omtrent het activiteit.
    {
      this.gesprekGevonden = false;
    }
  }

  nieuwGesprekAanmaken(gesprek: Gesprek) {
    console.log(gesprek);
    //gesprek.tijdelijkSetId(9000+this.nieuwGesprekZonderBerichten);
    //gesprek = this.gesprekkenService.voegGebruikerToeAanGesprek(gesprek, this.ingelogdeGebruiker.id);
    gesprek.deelnemers.push(this.ingelogdeGebruiker);
    gesprek.deelnemers.reverse();//ingelogde gebruiker op index 0 plaatsen die altijd rechts wordt weergegeven.
    console.log("gesprek die wordt aangemaakt:");
    console.log(gesprek);
    this.gesprekkenService.voegNieuwGesprekToe(gesprek).subscribe((gesprek: Gesprek) => {
      this.gesprek = gesprek;
      this.gebruikersAlsString = this.gesprek.gebruikersAlsString();
      this.geselecteerdeActiviteit.gesprekId = gesprek.id;
        console.log(this.geselecteerdeActiviteit);
      this.activiteitService.wijzigActiviteit(this.geselecteerdeActiviteit).subscribe();
    });
    console.log(gesprek);

    //this.nieuwNietGepersisteerdGesprek = gesprek;
    //this.nieuwGesprekZonderBerichten++;
    this.gesprekAanmaken = false;
    this.gesprekGevonden = true;
  }

  //  get gespreksPersonen():string {
  //    let personen: string = "";
  //    this._gespreksPersonen.map(pers => personen += `${pers}, `);
  //      return personen.substr(0, personen.length - 2);
  //  }

  berichtToevoegen(bericht: Bericht) {
    this.gesprekkenService.voegBerichtToeAanGesprek(this.gesprek.id, bericht).subscribe((bericht: Bericht) => {
      this.gesprek.berichten.push(bericht);
    });
  }

}/*Einde Component*/
