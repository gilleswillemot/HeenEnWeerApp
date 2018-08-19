import { AuthGuardService } from './auth-guard.service';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AuthenticationService } from './authentication.service';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpModule } from '@angular/http';
import { RegistreerComponent } from './registreer/registreer.component';
import { LogoutComponent } from './logout/logout.component';
//import { UserDataService } from '../services/user-data.service';

const routes = [
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'registreren', component: RegistreerComponent }
];

@NgModule({
  imports: [
    CommonModule,
    HttpModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes)
  ],
  declarations: [
    LoginComponent,
    RegistreerComponent,
    LogoutComponent
  ],
providers: [
  AuthenticationService,
  AuthGuardService,
  //UserDataService
]
})
export class UserModule { }
