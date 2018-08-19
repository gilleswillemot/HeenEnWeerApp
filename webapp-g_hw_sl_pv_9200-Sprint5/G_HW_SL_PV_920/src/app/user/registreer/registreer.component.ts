import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators, FormControl } from '@angular/forms';
import { AuthenticationService } from '../authentication.service';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

function passwordValidator(length: number): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } => {
    return control.value.length < length ? {
      'passwordTooShort':
        { requiredLength: length, actualLength: control.value.length }
    } : null;
  };
}

function comparePasswords(control: AbstractControl): { [key: string]: any } {
  const password = control.get('password');
  const confirmPassword = control.get('confirmPassword');
  return password.value === confirmPassword.value ? null : { 'passwordsDiffer': true };
}

@Component({
  selector: 'app-registreer',
  templateUrl: './registreer.component.html',
  styleUrls: ['./registreer.component.css']
})
export class RegistreerComponent implements OnInit {

  public user: FormGroup;

  constructor(private fb: FormBuilder, private authenticationService: AuthenticationService, private router: Router) { }

  ngOnInit() {
    this.user = this.fb.group({
      email: ['', [Validators.required],
       /* this.serverSideValidateEmail()*/],
      passwordGroup: this.fb.group({
        password: ['', [Validators.required, passwordValidator(12)]],
        confirmPassword: ['', Validators.required]
      }, { validator: comparePasswords }),
      voornaam: ['', [Validators.required]],
      familienaam: ['', [Validators.required]],
    });
  }

  get passwordControl(): FormControl {
    return <FormControl>this.user.get('passwordGroup').get('password');
  }

  //serverSideValidateEmail(): ValidatorFn {
  //  return (control: AbstractControl): Observable<{ [key: string]: any }> => {
  //    return this.authenticationService.checkEmailAvailability(control.value).map(available => {
  //      if (available) {
  //        return null;
  //      }
  //      return { userAlreadyExists: true };
  //    });
  //  };
  //}

  onSubmit() {
    this.authenticationService.register(
      this.user.value.email,
      this.passwordControl.value,
      this.user.value.voornaam,
      this.user.value.familienaam).subscribe(val => {
      if (val) {
        this.router.navigate(['/profiel']);
      }
    });
  }

}
