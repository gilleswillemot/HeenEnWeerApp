import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormGroup, Validators, FormBuilder, FormArray } from '@angular/forms';
import { Subject } from 'rxjs/Subject';
import { KostenService } from '../kosten.service';
import { Kost } from '../kost.model';
import { AuthenticationService } from '../../user/authentication.service';

@Component({
  selector: 'app-kost-wijzigen',
  templateUrl: './kost-wijzigen.component.html',
  styleUrls: ['./kost-wijzigen.component.css']
})
export class KostWijzigenComponent implements OnInit {

  @Output() gewijzigdeKost = new EventEmitter<Kost>();
  @Output() annuleerKost = new EventEmitter<boolean>();
  @Input() selectedKost;
  @Input() betrokkenPersonenLijst;


  kost: FormGroup;
  datum: Date = new Date();
  //betrokkenPersonenLijst: string[] = ["Jan", "William", "Joost"];
  refresh: Subject<any> = new Subject();
  categories: string[] = ["Sport", "School", "Medisch", "Cadeau", "geen categorie"];

  constructor(private fb: FormBuilder, private auth: AuthenticationService, private service: KostenService) { }

  ngOnInit() {
    this.datum = this.selectedKost.datum;
    this.kost = this.fb.group({
      naam: [this.selectedKost.naam, [Validators.required, Validators.minLength(2)]],
      aanmakerKost: [this.selectedKost.aanmakerKost],
      betrokkenPersonen: this.buildBetrokkenPersonen(),
      datum: this.datum,
      categorie: [this.selectedKost.categorie],
      kost: [this.selectedKost.kost, [Validators.required, Validators.pattern("^[1-9][0-9]*")]],
      beschrijving: [this.selectedKost.beschrijving]
    });
  }

  buildBetrokkenPersonen() {
    const arr = this.betrokkenPersonenLijst.map(persoon => {
      let bool = false;
      for (let p of this.selectedKost.betrokkenPersonen) {
        if (persoon === p)
          bool = true;
      }
      return this.fb.control(bool);
    });
    return this.fb.array(arr);
  }

  get betrokkenPersonen(): FormArray {
    console.log("help me");
    return this.kost.get('betrokkenPersonen') as FormArray;
  };

  onSubmit() {
    let aanmakerId;
    this.auth.user$.subscribe(item => aanmakerId = item);
    let nieuweKost = new Kost(
      this.kost.value.naam,
      this.kost.value.aanmakerKost,
      aanmakerId,
      this.getBetrokkenPersonen(),
      this.datum,
      parseInt(this.kost.value.kost),
      this.kost.value.beschrijving,
      this.kost.value.categorie,
      this.selectedKost.id
    );
    this.gewijzigdeKost.emit(nieuweKost);
  }

  getBetrokkenPersonen() {
    let personen = [];
    debugger;
    for (var i = 0; i < this.betrokkenPersonenLijst.length; i++) {
      if (this.kost.value.betrokkenPersonen[i]) {
        personen.push(this.betrokkenPersonenLijst[i]);
      }
    }
    return personen;
  }

  annuleer() {
    this.annuleerKost.emit(false);
  }
}
