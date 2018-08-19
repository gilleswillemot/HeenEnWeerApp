import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AlertComponent } from './alert.component';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';



@NgModule({
  imports: [
    CommonModule,
    NgbModalModule
  ],
  declarations: [
    AlertComponent
  ],
  exports: [
    AlertComponent]
})
export class AlertModule { }
