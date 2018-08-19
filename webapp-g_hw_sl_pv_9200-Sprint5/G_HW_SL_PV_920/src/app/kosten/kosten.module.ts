import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CalendarModule } from 'angular-calendar';
import { DateTimePickerModule } from '../date-time-picker/date-time-picker.module';
import { AlertModule } from '../alert/alert.module';

import { KostToevoegenComponent } from './kost-toevoegen/kost-toevoegen.component';
import { KostenbeheerComponent } from './kostenbeheer/kostenbeheer.component';
import { KostenService } from './kosten.service';
import { KostWijzigenComponent } from './kost-wijzigen/kost-wijzigen.component';
import {AuthGuardService} from '../user/auth-guard.service';

const appRoutes = [
  { path: 'kostenbeheer', canActivate: [AuthGuardService], component: KostenbeheerComponent },
  { path: 'kost-toevoegen', canActivate: [AuthGuardService], component: KostToevoegenComponent },
  { path: 'kost-wijzigen', canActivate: [AuthGuardService], component: KostWijzigenComponent }
];

@NgModule({
  imports: [
    HttpModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModalModule,
    CalendarModule,
    DateTimePickerModule,
    AlertModule,
    RouterModule.forChild(appRoutes)
  ],
  declarations: [
    KostToevoegenComponent,
    KostenbeheerComponent,
    KostWijzigenComponent
  ],
  providers: [KostenService]
})
export class KostenModule { }
