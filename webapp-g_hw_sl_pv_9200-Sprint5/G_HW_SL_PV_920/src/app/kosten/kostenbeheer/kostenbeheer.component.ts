import { Component, OnInit, Injectable } from '@angular/core';
import { Kost } from '../kost.model';
import { KostenService } from '../kosten.service';
import { AuthenticationService } from '../../user/authentication.service';
import { GebruikerService } from '../../gebruiker.service';


@Component({
  selector: 'app-kostenbeheer',
  templateUrl: './kostenbeheer.component.html',
  styleUrls: ['./kostenbeheer.component.css']
})

export class KostenbeheerComponent implements OnInit {

  openAlert: boolean = false;
  nieuweKostBoolean: boolean = false; //false toont lijst weer, true toont kost-toevoegen weer
  wijzigKostBoolean: boolean = false;
  selectedKost: Kost;
  kostenLijst;
  filteredOpDatum: boolean = false;
  filteredOpKost: boolean = false;
  filterLijst: Kost[];
  filterPersonen: string[] = [];
  betrokkenPersonenLijst: string[];
  allePersonenLijst: string[];
  categories: string[] = ["fa fa-soccer-ball-o", "fa fa-graduation-cap", "fa fa-heartbeat", "fa fa-gift", "fa fa-ban"];
  kostId;

  constructor(private kostenService: KostenService, private auth: AuthenticationService, private gebruikerService: GebruikerService) { }

  ngOnInit() {
    let userId;
    this.auth.user$.subscribe(id => userId = id);

    this.gebruikerService.haalGezinUitDatabase().subscribe(gezin => {
      this.allePersonenLijst = gezin.gezinsleden.map(lid => lid.volledigeNaam);
        this.betrokkenPersonenLijst = gezin.gezinsleden.filter(lid => lid.id !== userId).map(lid => lid.volledigeNaam);
      }
    );

    this.kostenService.kosten.subscribe(item => {
      this.kostenLijst = item;
      this.filterLijst = item;
      this.filterOpDatum();
    });
  }

  nieuweKostToevoegenClick() {
    this.nieuweKostBoolean = true;
  }

  removeKost(event) {
    if(event){
      this.kostenService.verwijderKost(this.kostId);
      this.filterLijst = this.filterLijst.filter(k => k.id !== this.kostId);
      this.kostenLijst = this.kostenLijst.filter(k => k.id !== this.kostId);
    }
    this.kostId = undefined;

  }

  wijzigKostClick(kost) {
    this.selectedKost = kost;
    this.wijzigKostBoolean = true;
  }

  showAlert() {
    this.openAlert = true;
  }

  voegNieuweKostToe(kost) {
    this.kostenService.voegNieuweKostToe(kost).subscribe(item => {
      this.filterLijst.push(item);
      //this.kostenLijst.push(item);
    });
    this.nieuweKostBoolean = false;
  }

  wijzigKost(kost) {
    console.log(kost);
    this.kostenService.wijzigKost(kost)
      .subscribe((item: Kost) => {
        this.filterLijst.forEach( k => {
          if (k.id === item.id)
            k = item;
        });
      });
    this.wijzigKostBoolean = false;
  }

  annuleer() {
    this.nieuweKostBoolean = false;
    this.wijzigKostBoolean = false;
  }

  filterOpDatum() {
    if (!this.filteredOpDatum) {
      this.filterLijst.sort(function (a, b) {
        return new Date(b.datum).getTime() - new Date(a.datum).getTime();
      });
    } else {
      this.filterLijst.sort(function (a, b) {
        return new Date(a.datum).getTime() - new Date(b.datum).getTime();
      });
    }

    this.filteredOpDatum = !this.filteredOpDatum;
    this.filteredOpKost = false;
  }

  filterOpKost() {
    if (!this.filteredOpKost) {
      this.filterLijst.sort(function (a, b) {
        if (a.kost < b.kost) return -1;
        if (a.kost > b.kost) return 1;
        return 0;
      });
    } else {
      this.filterLijst.sort(function (a, b) {
        if (a.kost > b.kost) return -1;
        if (a.kost < b.kost) return 1;
        return 0;
      });
    }

    this.filteredOpKost = !this.filteredOpKost;
    this.filteredOpDatum = false;
  }

  filterOpCategorie(categorie: string) {
    if (categorie != 'fa fa-ban') {
      this.filterLijst = [];
      this.filterLijst = this.kostenLijst.filter(kost => kost.getCategorieAsIcon() === categorie);
    } else {
      this.filterLijst = this.kostenLijst;
    }
  }

  filterOpPersonen(persoon) {
    if (this.filterPersonen.includes(persoon)) {
      let index = this.filterPersonen.indexOf(persoon);
      if (index > -1) {
        this.filterPersonen.splice(index, 1);
      }
    } else {
      this.filterPersonen.push(persoon);
    }
    if (this.filterPersonen.length > 0) {
      this.filterLijst = [];
      this.filterLijst = this.kostenLijst.filter(kost => kost.betrokkenPersonen.sort().join(',') === this.filterPersonen.sort().join(','));
    } else {
      this.filterLijst = this.kostenLijst;
    }
  }

}
