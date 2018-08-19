import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../user/authentication.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {

  constructor(private authService: AuthenticationService,) { }

  ngOnInit() {
  }

  get currentUser(): Observable<string> {
    return this.authService.user$;
  }
}
