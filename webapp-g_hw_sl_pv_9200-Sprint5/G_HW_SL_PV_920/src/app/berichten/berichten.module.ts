import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GesprekComponent } from './gesprek/gesprek.component';
import { BerichtenComponent } from './berichten.component';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { GesprekAanmakenComponent } from './gesprek-aanmaken/gesprek-aanmaken.component';
import { AlertModule } from '../alert/alert.module';
import { AuthGuardService } from '../user/auth-guard.service';

const appRoutes = [
  { path: 'berichten/:activiteitId', canActivate: [AuthGuardService], component: BerichtenComponent },
  { path: 'berichten',canActivate: [AuthGuardService], component: BerichtenComponent }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    AlertModule,
    RouterModule.forChild(appRoutes)

  ],
  declarations: [
    BerichtenComponent,
    GesprekComponent,
    GesprekAanmakenComponent
  ],
  exports: [GesprekComponent, GesprekAanmakenComponent]

})
export class BerichtenModule { }
