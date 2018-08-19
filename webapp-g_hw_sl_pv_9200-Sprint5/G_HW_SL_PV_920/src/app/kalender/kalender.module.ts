import { NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';
import { CalendarModule } from 'angular-calendar';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DateTimePickerComponent } from '../date-time-picker/date-time-picker.component';
import { CalendarHeaderComponent } from '../date-time-picker/calendar-header.component';
import { KalenderComponent } from './kalender/kalender.component';
import { WeekViewComponent } from './week-view/week-view.component';
import { DagViewComponent } from './dag-view/dag-view.component';
import { ActiviteitBewerkenComponent } from './activiteit-bewerken/activiteit-bewerken.component';
import { ColorPickerModule } from '../color-picker/color-picker.module';
import { DateTimePickerModule } from '../date-time-picker/date-time-picker.module';
import { ActiviteitGesprekComponent } from './activiteit-gesprek/activiteit-gesprek.component';
import { BerichtenModule } from '../berichten/berichten.module';
import { AlertModule } from '../alert/alert.module';
import { AuthGuardService } from '../user/auth-guard.service';


const appRoutes = [
  { path: 'kalender/:activiteitId', canActivate: [AuthGuardService], component: KalenderComponent },
  { path: 'kalender/:activiteitId', canActivate: [AuthGuardService], component: KalenderComponent },
  { path: 'kalender', canActivate: [AuthGuardService], component: KalenderComponent }
];

@NgModule({
    declarations: [
      KalenderComponent,
      WeekViewComponent,
      DagViewComponent,
      ActiviteitBewerkenComponent,
      ActiviteitGesprekComponent,
    ],
    imports: [
      HttpModule,
      CommonModule,
      FormsModule,
      ReactiveFormsModule,
      NgbModalModule,
      ColorPickerModule.forRoot(),
      NgbModule,
      DateTimePickerModule,
      CalendarModule,
      BerichtenModule,
      AlertModule,
      RouterModule.forChild(appRoutes)
    ],
    providers: [CalendarHeaderComponent, DateTimePickerComponent]
  })
export class KalenderModule {
}
