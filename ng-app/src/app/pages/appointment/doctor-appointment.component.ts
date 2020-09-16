import { Component, OnInit, OnDestroy } from '@angular/core';
import { timer, Subscription } from 'rxjs';
import { AppointmentService } from 'src/app/shared/service/appointment.service';

@Component({
  selector: 'app-doctor-appointment',
  templateUrl: './doctor-appointment.component.html',
  styles: [
  ]
})
export class DoctorAppointmentComponent implements OnInit, OnDestroy {

  private timerSource = timer(2 * 60 * 1000, 2 * 60 * 1000); /// every 2 mins
  private timerSubscription: Subscription;
  constructor(private appointmentService: AppointmentService) { }

  ngOnDestroy(): void {
    if(!!this.timerSubscription) {
      this.timerSubscription.unsubscribe();
    }
  }

  ngOnInit(): void {
    this.timerSubscription = this.timerSource.subscribe(v => {
      this.appointmentService.refreshListEmitter.emit();
    });
  }


}
