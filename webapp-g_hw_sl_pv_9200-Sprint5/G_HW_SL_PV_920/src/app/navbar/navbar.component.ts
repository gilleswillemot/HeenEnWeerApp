import { Component, OnInit, NgModule, OnChanges, SimpleChange, SimpleChanges} from '@angular/core';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../user/authentication.service';
import { GebruikerService } from "../gebruiker.service";
import { Gebruiker } from '../gebruiker.model';
import { Router } from '@angular/router';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
  providers: [GebruikerService]
})

export class NavbarComponent implements OnInit{

  // _ingelogdeGebruiker;

  constructor(private router:Router, private authService: AuthenticationService, private gebruikerService: GebruikerService) { }

  ngOnInit() {
    let userId;
    // this.currentUser.subscribe(item => userId = item);
    // if(userId != undefined){
   
  }

  get username(): Observable<String>{
    return this.authService.username$; 
      //  this._ingelogdeGebruiker = this.gebruikerService.getGebruikerMetEmail(this.authService.username$);      
  }

  get currentUser(): Observable<string> {
    return this.authService.user$;
  }

  get heeftGezin(): Observable<string> {
    return this.authService.heeftGezin$;
  }

  logout() {
    console.log("logout");
    this.authService.logout();
    // this.gebruikerService.ingelogdeGebruiker.subscribe(item => {
    //   this.ingelogdeGebruiker = item;
    //   console.log(this.ingelogdeGebruiker);
    // });
    this.router.navigate(['login']);
  }

// get heeftGezin(){
//      this.ingelogdeGebruiker.subscribe(item =>{
//       return item.heeftGezin();
//     });
//       //this.gebruikerService.ingelogdeGebruiker.subscribe(item => {
//     //  this.ingelogdeGebruiker = item;
//     //  if (this.ingelogdeGebruiker.heeftGezin())
//     //    this.heeftGezin = true;
//     //  else
//     //    this.heeftGezin = false;
//     //});
//   }

  ngOnChanges(changes: SimpleChanges) {
    
    //const gebruiker: SimpleChange = changes["ingelogdeGebruikerObservable"];
    //console.log("ngonchanges" + gebruiker);
    //if (gebruiker.firstChange)
    //  console.log("firstchange");
    //if (gebruiker.isFirstChange())
    //  console.log("isfirstchange");
  }
}
