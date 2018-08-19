import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-profiel-andere-bekijken',
  templateUrl: './profiel-andere-bekijken.component.html',
  styleUrls: ['./profiel-andere-bekijken.component.css']
})
export class ProfielAndereBekijkenComponent implements OnInit {

  @Input() andereGebruiker;

  constructor() { }

  ngOnInit() {
  }

}
