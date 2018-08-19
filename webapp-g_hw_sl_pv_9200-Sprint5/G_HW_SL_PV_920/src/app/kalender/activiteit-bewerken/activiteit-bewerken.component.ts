import { Component, OnInit, Output, EventEmitter, Input, OnChanges, SimpleChange, ViewChild, SimpleChanges, TemplateRef } from '@angular/core';
import { FormGroup, Validators, FormBuilder, FormArray } from "@angular/forms";
import { ActiviteitService } from "../activiteit.service";
import { Activiteit } from "../../activiteit.model";
import { Subject } from 'rxjs/Subject';
import { IColorPickerConfiguration } from "../../color-picker/interfaces/color-picker-config.interface";
import { colors } from '../../colors';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { AlertComponent } from '../../alert/alert.component';


@Component({
  selector: 'app-activiteit-bewerken',
  templateUrl: './activiteit-bewerken.component.html',
  styleUrls: ['./activiteit-bewerken.component.css']
})
export class ActiviteitBewerkenComponent implements OnInit {

  @Output() gewijzigdeActiviteit = new EventEmitter<Activiteit>();
  @Output() activiteitActie = new EventEmitter<boolean>();//0 is terug gaan, 1 is nieuwe act aanmaken, 2 is verwijderen
  @Input() _geselecteerdeActiviteit;
  @Input() gezinsLeden;
  @Input() activiteitBewerken: boolean;
  // @ViewChild(AlertComponent) yo;

  activiteit: FormGroup;
  refresh: Subject<any> = new Subject();
  startDatum: Date = new Date();
  eindDatum: Date = new Date();
  color;
  geselecteerdePersonen: string[] = [];//lijst met id's van de betrokken personen tot deze activiteit.
  cardHeader: string = "Activiteit Bewerken";
  alert: boolean = false;

  pickerOptions: IColorPickerConfiguration = {
    width: 40,
    height: 40,
    borderRadius: 8
  };

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

  constructor(private fb: FormBuilder, private modalService: NgbModal) {

  }

  ngOnInit() {

    this.setActiviteit();
    // this.yo.modal.show();
  }


  openAlert() {
    this.alert = true;
  }

  setActiviteit() {
    if (this.activiteitBewerken) {
      this.geselecteerdePersonen = this._geselecteerdeActiviteit.personen;
      this.startDatum = this._geselecteerdeActiviteit.start;
      this.eindDatum = this._geselecteerdeActiviteit.end;
      this.color = this._geselecteerdeActiviteit.color.primary;

      this.activiteit = this.fb.group({
        titel: [
          this._geselecteerdeActiviteit.title, [
            Validators.required, Validators.maxLength(20),
            Validators.minLength(2)
          ]
        ],
        personen: this.fb.array([]),
        beschrijving: [this._geselecteerdeActiviteit.beschrijving]
        //TODO startDatum moet < zijn eindDatum
      });

      this.gezinsLeden.forEach(persoon => { //de selectie van personen juist instellen.
        this.personen.push(this.maakPersoon(this.isGeselecteerd(persoon.id), persoon.id, persoon.voornaam));
      });
    } else //als er een nieuwe activiteit wil toegevoegd worden
    {
      this.activiteit = this.fb.group({
        titel: ["", [Validators.required, Validators.maxLength(20), Validators.minLength(2)]],
        personen: this.fb.array([]), //TODO validator dat er toch minimum 1 persoon aan verbonden is
        beschrijving: [""]
      });
      this.gezinsLeden.forEach(persoon => { //de selectie van personen allemaal op false plaatsen.
        this.personen.push(this.maakPersoon(false, persoon.id, persoon.voornaam));
      });
      this.cardHeader = "Nieuwe Activiteit Toevoegen";
      this.color = "#ff6666";
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    const geselecteerdeActiviteit: SimpleChange = changes["_geselecteerdeActiviteit"];
    console.log(geselecteerdeActiviteit);
    if (!geselecteerdeActiviteit.isFirstChange()) {
      this.setActiviteit();
    }
  }

  get geselecteerdeActiviteit() {
    return this._geselecteerdeActiviteit;
  }

  break(index: number): boolean {
    console.log(index);
    let yo = index % 2 === 1 && index !== 0;
    console.log(yo);
    return yo;
  }

  maakPersoon(bool: boolean, id, naam): FormGroup {
    return this.fb.group({
      checked: [bool],
      id: [id],
      naam: [naam]
    });
  }

  get personen(): FormArray {
    return this.activiteit.get('personen') as FormArray;
  };

  onSubmit() {
    console.log("submit");
    let nieuweActiviteit = new Activiteit(
      this.activiteit.value.titel,
      this.startDatum,
      this.eindDatum,
      this.color,
      this.activiteit.value.beschrijving,
      this.getBetrokkenPersonenIds(),
      0,
      undefined,
      this.activiteitBewerken ? this.geselecteerdeActiviteit.id
        : undefined//id wordt bij nieuwe act in backend aangemaakt
    );
    console.log(nieuweActiviteit);
    this.gewijzigdeActiviteit.emit(nieuweActiviteit);
  }

  isGeselecteerd(persoonsId: string): boolean {
    return this.geselecteerdePersonen.includes(persoonsId);
  }

  getBetrokkenPersonenIds(): string[] {
    return this.activiteit.value.personen.filter(p => p.checked).map(p => p.id);
  }

  annuleerActiviteitWijzigen() {
    //this.gewijzigdeActiviteit.emit(undefined);
    this.activiteitActie.emit(false);
  }

  activiteitVerwijderen(actie: boolean) {
    if (actie) {
      this.activiteitActie.emit(true);
    }
    this.alert = false;
  }

  actieUitvoeren(actie: number) {
    return actie;
  }

} // einde component
