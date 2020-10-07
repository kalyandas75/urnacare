import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'src/environments/environment';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConsultationEditComponent } from '../consultation-edit/consultation-edit.component';
import { ConsultationViewComponent } from '../consultation-view/consultation-view.component';
import { Subscription } from 'rxjs';
import { ChatComponent } from '../../chat/chat.component';
import { AppointmentService } from 'src/app/shared/service/appointment.service';

@Component({
  selector: 'app-doctor-appointment-list',
  templateUrl: './doctor-appointment-list.component.html',
  styles: [
    `::ng-deep .uc-lg .modal-dialog { 
      max-width: 100%;
      width: 90%;,
      top: 0
    }`
  ]
})
export class DoctorAppointmentListComponent implements OnInit, OnDestroy {

  appointments: any[];
  refreshListEmitterSubscription: Subscription;
  @Input() completed = false;
  constructor(
    private appointmentService: AppointmentService, 
    private toastr: ToastrService,
    private modalService: NgbModal) { }


  ngOnInit(): void {
    this.load();
    this.refreshListEmitterSubscription = this.appointmentService.refreshListEmitter.subscribe(v => {
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
      }, err => {
          console.log(err);
          this.toastr.error('Unable to fetch upcoming appointments.');
        });
    } else {
      this.appointmentService.completedAppointments()
      .subscribe(res => {
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
          a.enableConsultation = this.enableConsultation(a);
          if(!this.completed) {
            a.enableChat = this.enableChat(a);
            
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

  enableConsultation(apmt) {
    const scheduledTime = new Date(apmt.scheduledDate).getTime();
    const currentTime = new Date().getTime();
    if(currentTime >= scheduledTime) {
      return true;
    } else {
      return false;
    }
  }

  openConsultation(appointment) {
    // this.completed = false;
    if(!this.completed) {
      const modalRef = this.modalService.open(ConsultationEditComponent, { windowClass: 'uc-lg', centered: false, scrollable: true });
      modalRef.componentInstance.appointment = appointment;
    } else {
      const modalRef = this.modalService.open(ConsultationViewComponent, { size: 'lg', centered: false, scrollable: true });
      modalRef.componentInstance.appointment = appointment;
    }
  }

  openChat(roomId: string, name: string) {
    const modalRef = this.modalService.open(ChatComponent,  { size: 'sm', scrollable: true, centered: false, backdrop: 'static' });
    modalRef.componentInstance.roomId = roomId;
    modalRef.componentInstance.partner = name;
    modalRef.componentInstance.isDoctor = true;
  }

}
