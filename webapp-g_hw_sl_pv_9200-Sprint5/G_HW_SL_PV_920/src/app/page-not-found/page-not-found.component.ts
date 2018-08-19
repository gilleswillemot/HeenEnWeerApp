import { Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.css']
})
export class PageNotFoundComponent implements OnInit {

  imagePath = this.vulAfbeeldingUrl(); 
 
  constructor() { }

  ngOnInit() {
  }
  
  vulAfbeeldingUrl() {
     let afbeeldingenUrlArray = [
        "../../assets/verkeerde-url1.jpg",
        //"../../assets/verkeerde-url2.jpg",
        "../../assets/verkeerde-url3.jpg"
      ];
    
     return afbeeldingenUrlArray[Math.floor(Math.random()*2)];
  }
}
