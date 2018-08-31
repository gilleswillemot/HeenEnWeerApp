import { AuthenticationService } from './authentication.service';
import { Injectable } from '@angular/core';
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { CanActivate } from '@angular/router';
import { LoginObject } from './loginObject.model';


@Injectable()
export class AuthGuardService implements CanActivate {

  constructor(private authService: AuthenticationService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    let stateUrl = state.url;
    console.log(stateUrl);
    if (stateUrl === "/artikelAanmaken") {
      console.log("user wants to go to route \"artikelAanmaken\"");
      console.log(this.authService.isModerator$.getValue());
      if (this.authService.user$.getValue() && this.authService.isModerator$.getValue()) {
        console.log("permission granted");
        return true;
      }
      this.authService.redirectUrl = "";
      this.router.navigate(['/kennisCentrum']);
      console.log("permission denied");
      return false;
    } else {
      if (this.authService.user$.getValue()) {
        console.log("permission granted");
        return true;
      }
      this.authService.redirectUrl = state.url;
      this.router.navigate(['/login']);
      return false;
    }
  }
}
