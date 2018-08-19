import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-activiteit-popover',
  templateUrl: './activiteit-popover.component.html',
  styleUrls: ['./activiteit-popover.component.css']
})
export class ActiviteitPopoverComponent implements OnInit {
  @Input() activiteit;
  constructor() { }

  ngOnInit() {
  }

}
