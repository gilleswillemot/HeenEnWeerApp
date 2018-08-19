import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormGroup, Validators, FormBuilder, FormArray } from '@angular/forms';
import { Subject } from 'rxjs/Subject';
import { KostenService } from '../kosten.service';
import { AuthenticationService } from '../../user/authentication.service';
import { Kost } from '../kost.model';

@Component({
  selector: 'app-kost-toevoegen',
  templateUrl: './kost-toevoegen.component.html',
  styleUrls: ['./kost-toevoegen.component.css']
})
export class KostToevoegenComponent implements OnInit {

  @Output() nieuweKost = new EventEmitter<Kost>();
  @Output() annuleerKost = new EventEmitter<boolean>();
  @Input() betrokkenPersonenLijst;

  kost: FormGroup;
  datum: Date = new Date();
  //betrokkenPersonenLijst: string[] = ["Jan", "William", "Joost"];
  refresh: Subject<any> = new Subject();
  icons: string[] = ["fa fa-gift", "fa fa-graduation-cap"];
  categories: string[] = ["Sport", "School", "Medisch", "Cadeau", "geen categorie"];

  constructor(private fb: FormBuilder, private kostenService: KostenService, private auth: AuthenticationService) { }

  ngOnInit() {
    this.kost = this.fb.group({
      naam: ["", [Validators.required, Validators.minLength(2)]],
      betrokkenPersonen: this.buildBetrokkenPersonen(),
      datum: new Date(),
      categorie: ["sport"],
      kost: ["", [Validators.required, Validators.pattern("^[1-9][0-9]*")]],
      beschrijving: [""]
    });
  }

  buildBetrokkenPersonen() {
    const arr = this.betrokkenPersonenLijst.map(persoon => {
      return this.fb.control(true);
    });
    return this.fb.array(arr);
  }

  get betrokkenPersonen(): FormArray {
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
      this.kost.value.categorie
    );
    this.nieuweKost.emit(nieuweKost);
  }

  getBetrokkenPersonen() {
    let personen = [];
    for (var i = 0; i < this.betrokkenPersonenLijst.length; i++) {
      if (this.kost.value.betrokkenPersonen[i]) {
        personen.push(this.betrokkenPersonenLijst[i]);
      }
    }
    return personen;
  }

  annuleer() {
    this.annuleerKost.emit(true);
  }
}
