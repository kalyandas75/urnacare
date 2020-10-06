import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { AppointmentService } from 'src/app/shared/service/appointment.service';
import { FORMULATIONS, ValidateValueInList } from 'src/app/app.constant';
@Component({
  selector: 'app-consultation-view',
  templateUrl: './consultation-view.component.html',
  styles: [
  ]
})
export class ConsultationViewComponent implements OnInit {

  appointment:any;
  consultation: any;
  formulations = FORMULATIONS;

  constructor(public activeModal: NgbActiveModal, 
    private appointmentService: AppointmentService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.appointmentService.getConsulationByAppointmentId(this.appointment.id)
    .subscribe(res => {
      this.consultation = res.body;
    }, err => {
      this.toastr.error('Could not fetch consultation');
      this.activeModal.dismiss('X');
    })
  }

}
