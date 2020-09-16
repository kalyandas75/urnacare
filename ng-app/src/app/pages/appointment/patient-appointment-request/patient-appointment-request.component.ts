import { Component, OnInit, OnDestroy } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { AppointmentService } from 'src/app/shared/service/appointment.service';

@Component({
  selector: 'app-patient-appointment-request',
  templateUrl: './patient-appointment-request.component.html',
  styles: [
  ]
})
export class PatientAppointmentRequestComponent implements OnInit, OnDestroy {

  appointmentRequests: any[];
  refreshListEmitterSubscription: Subscription;
  constructor(private appointmentService: AppointmentService, private toastr: ToastrService) { }


  ngOnInit(): void {
    this.refreshListEmitterSubscription = this.appointmentService.refreshListEmitter.subscribe(val => {
      this.load();
    });
    this.load();
  }

  ngOnDestroy(): void {
    if(!!this.refreshListEmitterSubscription) {
      this.refreshListEmitterSubscription.unsubscribe();
    }
  }

  load() {
    this.appointmentService.getAppointmentRequestsForPatient()
    .subscribe(res => {
      this.appointmentRequests = (res.body as any[]);
    }, err => {
      console.log(err);
      this.toastr.error('Could not pull appointment requests');
    });
  }

}
