import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Bericht } from './bericht.model';
import { Gesprek } from './gesprek.model';
import { Activiteit } from '../activiteit.model';
import { ActiviteitService } from "../kalender/activiteit.service";
import { testActiviteiten } from "../testActiviteiten";
import { CalendarEvent } from "angular-calendar";
import { GesprekkenService } from "./gesprekken.service";
import { GebruikerService } from '../gebruiker.service';
import { Gebruiker } from '../gebruiker.model';
import { Gezin } from '../gezin.model';

@Component({
  selector: 'app-berichten',
  templateUrl: './berichten.component.html',
  styleUrls: ['./berichten.component.css']
})
export class BerichtenComponent implements OnInit {

  //berichten: Bericht[];
  private _gesprekMetActiviteit: boolean = false;
  ingevoegdeActiviteit;
  activiteiten;
  huidigGesprek: Gesprek;
  _berichten: Bericht[];
  gesprekken;
  activiteitPersonen: string[] = [];
  //nieuwGesprek;
  _gesprekAanmaken: boolean = false;
  gesprekMetActiviteit: boolean = false;
  gezinsLeden;
  aangemeldeGebruiker;
  nieuwGesprekZonderBerichten: number = 0;
  nieuwNietGepersisteerdGesprek: Gesprek;
  personenInHuidigGesprek: string;
  ingelogdeGebruiker: Gebruiker;
  gezinNaam: string;
  //alert:boolean = false;
  gesprekId: string;
  constructor(private route: ActivatedRoute, private activiteitService: ActiviteitService,
    private gesprekkenService: GesprekkenService, private gebruikerService: GebruikerService) { }

  ngOnInit() {
    this.gesprekken = [];
    this.gebruikerService.haalGezinUitDatabase().subscribe((gezin: Gezin) => {
      this.gezinNaam = gezin.naam;
      console.log(gezin);
      this.gezinsLeden = gezin.gezinsleden;
      console.log(this.gezinNaam);
      let alleGesprekken = [];
      gezin.gesprekken.forEach(g => {
        let gesprek;
        this.gesprekkenService.getGesprekMetId(g.id).subscribe(item => {
          gesprek = item;
          this.gesprekken.push(gesprek.toGesprekInformatie());
          console.log(this.gesprekken);
          alleGesprekken.push(gesprek);
          this.huidigGesprek = alleGesprekken.find(g => g.naam === this.gezinNaam);
          if(this.huidigGesprek !== undefined)
            this.personenInHuidigGesprek = this.geefLijstVanGebruikersAlsString(this.huidigGesprek.deelnemers);
        });
      });
      //this.gesprekkenService.gesprekken.subscribe((gesprekken: Gesprek[]) => {
      //  console.log(gesprekken);
      //  this.gesprekken = gesprekken.map(i => i.toGesprekInformatie());
      console.log(alleGesprekken);
     
      //});
    });




    const paramId = this.route.snapshot.params['activiteitId'];
    var regex = /^\d+$/;//kijken of parameter een cijfer is.
    if (paramId !== undefined && regex.test(paramId)) { //als er een activiteitId is meegegeven aan de URL.
      this.activiteitService.getActiviteitWithId(+paramId).subscribe(item => this.ingevoegdeActiviteit = item); /*.distinct() voor de veiligheid*/;
      //gesprekWeergeven   
    } else {
      console.log("de url klopt niet, alle gesprekken worden weergegeven.");
      //this.gesprekkenService.geefLaatsteGesprek(this.gesprekken[0].id).subscribe(item => this.huidigGesprek = item);
    }
    //this.ingelogdeGebruiker = this.gebruikerService.geefIngelogdeGebruiker();
    //this.gesprekken = this.gesprekkenService
    //  .alleGesprekkengegevensOphalen(this.ingelogdeGebruiker);
    //this.personenInHuidigGesprek = this.geefLijstVanGebruikersAlsString(this.huidigGesprek.deelnemers);
  }

  //get berichten(): Bericht[] {
  //  return this._berichten;
  //}

  get activiteit(): CalendarEvent {
    return this.ingevoegdeActiviteit;
  }

  gesprekAanmakenAnnuleren() {
    this._gesprekAanmaken = false;
  }

  //gesprekMetActiviteit(): boolean {
  //  //return this.ingevoegdeActiviteit !== undefined;
  //  return this.huidigGesprek.isVerbondenMetActiviteit();
  //}

  activiteitOphalen(activiteitsId) {
    //TODO in de toekomst moet die gebruik maken van waarschijnlijk de ActiviteitService om de activiteit op
    //TODO te halen.
    return this.activiteitService.getActiviteitWithId(activiteitsId).subscribe(); /*.distinct() voor de veiligheid*/;

    //return this.activiteiten.filter(act => act.id === activiteitsId)[0];
  }

  gesprekOphalen(gesprekId: string, verbondenMetActiviteit: boolean) {
    this.gesprekkenService.getGesprekMetId(gesprekId).subscribe(item => {
      this.huidigGesprek = item;
      this.gesprekKlaarzetten(verbondenMetActiviteit);
    });
    //this.huidigGesprek = this.gesprekkenService.geefGesprek(gesprekId, this.ingelogdeGebruiker);

    //this.gesprekKlaarzetten(verbondenMetActiviteit);
  }

  gesprekKlaarzetten(heeftActiviteit: boolean) {
    if (heeftActiviteit) {
      this.ingevoegdeActiviteit = this.activiteitOphalen(this.huidigGesprek.activiteitId);
      this.gesprekMetActiviteit = true;
      this.activiteitPersonen = this.ingevoegdeActiviteit.personen
        .map(id => this.gezinsLeden.find(gebruiker => gebruiker.id === id).voornaam);
    } else {
      this.ingevoegdeActiviteit = undefined;
      this.gesprekMetActiviteit = false;
    }
    this.personenInHuidigGesprek = this.geefLijstVanGebruikersAlsString(this.huidigGesprek.deelnemers);
  }

  geefLijstVanGebruikersAlsString(deelnemers: Gebruiker[]): string {
    return deelnemers.length > 0 ? deelnemers.map(deelnemer => deelnemer.voornaam).join(", ") : undefined;
  }

  berichtToevoegen(nieuwBericht/*TODO, 2de param met id van gesprek*/) {

    this.gesprekkenService.voegBerichtToeAanGesprek(this.huidigGesprek.id, nieuwBericht)
      .subscribe(item => {
        this.huidigGesprek.berichten.push(item);
      });

    //if (this.nieuwGesprekZonderBerichten!==0) {
    //  this.huidigGesprek.voegBerichtToe(nieuwBericht);
    //  this.nieuwGesprekZonderBerichten--;
    //} else {

    //}
    //this.huidigGesprek.berichten
    //  .push(this.gesprekkenService.voegNieuwBerichtToe(this.huidigGesprek.id, nieuwBericht));
  }

  gesprekAanmaken() {
    this.gezinsLeden = this.gebruikerService.geefGezinsledenExclusief();
    this._gesprekAanmaken = true;
    //this.aangemeldeGebruiker = this.gebruikerService.geefIdIngelogdeGebruiker();
  }




  nieuwGesprek(gesprek: Gesprek) {
    if (gesprek.naam === this.gezinNaam)
      gesprek.naam = this.gezinNaam + "2";//Tijdelijk, wànt gezin heeft áltijd 1 hoofdgesprek. 
    //gesprek.tijdelijkSetId(9000+this.nieuwGesprekZonderBerichten);
    //gesprek = this.gesprekkenService.voegGebruikerToeAanGesprek(gesprek, this.ingelogdeGebruiker.id);
    this.gesprekkenService.voegNieuwGesprekToe(gesprek).subscribe(item => {
      this.huidigGesprek = item;
      this.gesprekken.push(this.huidigGesprek.toGesprekInformatie());
      this.gesprekKlaarzetten(this.huidigGesprek.heeftActiviteit());
    });


    //  {
    //  gespreksnaam: this.huidigGesprek.naam,
    //id: this.huidigGesprek.id,//TODO opletten als je id hierboven wegdoet want anders is dit undefined!
    // nieuweBerichten: 0,
    //isVerbondenMetActiviteit: this.huidigGesprek.isVerbondenMetActiviteit()
    //});
    //this.nieuwNietGepersisteerdGesprek = gesprek;
    //this.nieuwGesprekZonderBerichten++;
    this._gesprekAanmaken = false;



  }

  gesprekVerwijderen(gesprekId: string, accepteer: boolean) {
    if (accepteer) {
      this.gesprekkenService.verwijderGesprek(gesprekId);
      this.gesprekken.splice(this.gesprekken.indexOf
        (this.gesprekken.find(g => g.id === gesprekId)),
        1);
    }
    this.gesprekId = undefined;

  }

  //gesprekAanmaken() {
  //  this._gesprekAanmaken = true;

  //}
}/*Einde class BerichtenComponent*/
