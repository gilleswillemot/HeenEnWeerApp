import { Component, OnInit, Inject, Output, EventEmitter } from '@angular/core';
import { GezinAanmakenComponent } from '../gezin-aanmaken/gezin-aanmaken.component';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Gebruiker } from '../../gebruiker.model';
import { colors } from '../../colors';
import { IColorPickerConfiguration } from "../../color-picker/interfaces/color-picker-config.interface";


@Component({
  selector: 'app-kind-aanmaken',
  templateUrl: './kind-aanmaken.component.html',
  styleUrls: ['./kind-aanmaken.component.css']
})
export class KindAanmakenComponent implements OnInit {

  @Output() nieuwKind = new EventEmitter<Gebruiker>();
  @Output() annuleerKindAanmaken = new EventEmitter<boolean>();
  kind: FormGroup;
  color: string;
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

  constructor(private fb: FormBuilder) {}

  ngOnInit() {
    this.kind = this.fb.group({
      voornaam: ["", Validators.required],
      familienaam: ["", Validators.required]
    });
    this.color = colors.lightyellow;
  }

  onSubmit() {
    let nieuwKind = new Gebruiker(
      "",
      this.kind.value.voornaam,
      this.kind.value.familienaam,
      "",
      "",
      []
    );
    nieuwKind.kleur = this.color;
    console.log(nieuwKind);
    
    this.nieuwKind.emit(nieuwKind);
  }

  annuleer() {
    this.annuleerKindAanmaken.emit(false);
  }
}
