import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-confirmation',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.scss']
})
export class ConfirmationComponent implements OnInit {

  message: string;
  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

  onAction(action: string) {
    this.activeModal.close(action);
  }
}
