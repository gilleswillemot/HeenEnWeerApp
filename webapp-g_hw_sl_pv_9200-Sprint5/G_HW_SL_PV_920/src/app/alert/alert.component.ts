import { Component, OnInit, Input, EventEmitter, Output, ViewChild, ElementRef } from '@angular/core';
import { NgbAlertConfig, NgbModal, ModalDismissReasons, NgbModalRef, NgbActiveModal
    } from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css'],
  providers: [NgbAlertConfig],
//  styles: [`   
//.red-modal .modal-content {
//      background-color: red;
//      color: white;
//    }
//    .red-modal .close {
//      color: white;   
//    }
//  `]
})
export class AlertComponent implements OnInit {
  @Input() titel: string;
  @Input() alertBoodschap: string;
  @Input() welBoodschap: string;
  @Input() nietBoodschap: string;
  @Input() isOpen: boolean;
  @ViewChild('content') private content;

  @Output() actie = new EventEmitter<boolean>();
    //TODO in de toekomst eventueel 1 object met de bovenstaande attributen.
  constructor( private modalService: NgbModal) {
//    alertConfig.type = 'danger';
//    alertConfig.dismissible = false;
  }

  ngOnInit() {
    
   this.open(this.content);

  }

  //annuleer() {
  //  this.actie.emit(false);
  //}

  open(content) {
    this.modalService.open(content, { windowClass: 'red-modal' });
  }

  uitvoeren(accepteren:boolean) {
    this.actie.emit(accepteren);
  };
}
