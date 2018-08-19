import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ColorPickerModule } from '../color-picker/color-picker.module';
import { ProfielComponent } from './profiel/profiel.component';
import { ProfielBewerkenComponent } from './profiel-bewerken/profiel-bewerken.component';
import { ProfielBekijkenComponent } from './profiel-bekijken/profiel-bekijken.component';
import { ProfielAndereBekijkenComponent } from './profiel-andere-bekijken/profiel-andere-bekijken.component';
import { AuthGuardService } from '../user/auth-guard.service';

const appRoutes = [
  { path: 'profiel/:gebruikerId', canActivate: [AuthGuardService], component: ProfielComponent },
  { path: 'profiel', canActivate: [AuthGuardService], component: ProfielComponent },
  { path: 'profiel-bewerken', canActivate: [AuthGuardService], component: ProfielBewerkenComponent },
  { path: 'profiel-bekijken', canActivate: [AuthGuardService], component: ProfielBekijkenComponent },
  { path: 'profiel-andere-bekijken', canActivate: [AuthGuardService], component: ProfielAndereBekijkenComponent }
];

@NgModule({
  imports: [
    CommonModule,
    NgbModalModule,
    HttpModule,
    FormsModule,
    ReactiveFormsModule,
    ColorPickerModule,
    RouterModule.forChild(appRoutes)
  ],
  declarations: [
    ProfielComponent,
    ProfielBewerkenComponent,
    ProfielBekijkenComponent,
    ProfielAndereBekijkenComponent
  ],
  providers: []
})
export class ProfielModule { }
