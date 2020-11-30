import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'src/environments/environment';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConsultationViewComponent } from '../consultation-view/consultation-view.component';
import { Subscription } from 'rxjs';
import { ChatComponent } from '../../chat/chat.component';
import { AppointmentService } from 'src/app/shared/service/appointment.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-patient-appointment-list',
  templateUrl: './patient-appointment-list.component.html',
  styles: [
  ]
})
export class PatientAppointmentListComponent implements OnInit, OnDestroy {

  appointments: any[];
  refreshListEmitterSubscription: Subscription;
  @Input() completed = false;
  constructor(
    private appointmentService: AppointmentService, 
    private toastr: ToastrService,
    private modalService: NgbModal,
    private router: Router) { }


  ngOnInit(): void {
    this.load();
    this.refreshListEmitterSubscription = this.appointmentService.refreshListEmitter.subscribe(() => {
      this.load();
    });
  }
  ngOnDestroy(): void {
    if(!!this.refreshListEmitterSubscription) {
      this.refreshListEmitterSubscription.unsubscribe();
    }
  }
  load() {
    if (!this.completed) {
      this.appointmentService.pendingAppointments()
      .subscribe(res => {
        this.appointments = res.body as any[];
        this.pushChatAndConsultationLinks();
      },
        err => {
          console.log(err);
          this.toastr.error('Unable to fetch upcoming appointments.');
        });
    } else {
      this.appointmentService.completedAppointments()
      .subscribe(res =>  {
        this.appointments = res.body as any[];
        this.pushChatAndConsultationLinks();
      },
        err => {
          console.log(err);
          this.toastr.error('Unable to fetch past appointments.');
        });
    }

  }

  pushChatAndConsultationLinks() {
    if(this.appointments && this.appointments.length) {
        this.appointments.forEach(a => {
          if(!this.completed) {
            a.enableChat = this.enableChat(a);
            if(a.enableChat) {
              a.chatLink = this.getChatLink(a);
            }
          }
        });
    }
  }

  getChatLink(apmt) {
    return environment.CHAT_SERVER_URL + '/chat?room=' + apmt.uniquieKey;
  }

  enableChat(apmt) {
    const scheduledTime = new Date(apmt.scheduledDate).getTime();
    const scheduledTimeUpto = scheduledTime + 15 * 60 * 1000;
    const currentTime = new Date().getTime();
    if(currentTime >= scheduledTime && currentTime <= scheduledTimeUpto) {
      return true;
    } else {
      return false;
    }
  }

  openConsultation(appointment) {
    const modalRef = this.modalService.open(ConsultationViewComponent, { size: 'lg', scrollable: true });
    modalRef.componentInstance.appointment = appointment;
  }
  
  openChat(roomId: string, name: String) {
    const modalRef = this.modalService.open(ChatComponent,  { size: 'sm', scrollable: true, centered: false, backdrop: 'static' });
    modalRef.componentInstance.roomId = roomId;
    modalRef.componentInstance.partner = name;
  }

  initOrder(consultationId) {
    this.router.navigateByUrl('/order/init/' + consultationId);
  }
}
