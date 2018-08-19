import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from '../authentication.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public user: FormGroup;
  public errorMsg: string;

  constructor(private authService: AuthenticationService, private router: Router, private fb: FormBuilder) { }

  ngOnInit() {
    this.user = this.fb.group({
      email: ["", Validators.required],
      password: ["", Validators.required]
    });
  }

  onSubmit() {    
    this.authService.login(this.user.value.email, this.user.value.password).subscribe(val => {
      if (val) {
        if (this.authService.redirectUrl) {
          this.router.navigateByUrl(this.authService.redirectUrl);
          this.authService.redirectUrl = undefined;
        } else {
          // this.refresh();
          this.router.navigate(['/homepage']);
        }
      }
    }, err => this.errorMsg = err.json());
  }
  // refresh(): void {
  //   window.location.reload();
  // }
}
