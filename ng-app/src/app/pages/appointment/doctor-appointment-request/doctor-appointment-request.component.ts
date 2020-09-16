import { Component, OnInit, OnDestroy } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { FormGroup } from '@angular/forms';
import { FormlyFormOptions, FormlyFieldConfig } from '@ngx-formly/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Subscription } from 'rxjs';
import { AppointmentService } from 'src/app/shared/service/appointment.service';

@Component({
  selector: 'app-doctor-appointment-request',
  templateUrl: './doctor-appointment-request.component.html',
  styles: [
  ]
})
export class DoctorAppointmentRequestComponent implements OnInit, OnDestroy {

  appointmentRequests: any[];

  apprform = new FormGroup({});
  apprmodel: any = {};
  approptions: FormlyFormOptions = {};
  apprfields: FormlyFieldConfig[] = [
    {
      fieldGroupClassName: 'row',
      fieldGroup: [{
        className: 'col-md-6',
        type: 'input',
        key: 'scheduleDate',
        templateOptions: {
          label: 'Date',
          type: 'date',
          required: true
        },
      },
      {
        className: 'col-md-3',
        type: 'input',
        key: 'scheduleHours',
        templateOptions: {
          label: 'Hours',
          type: 'number',
          min: 0,
          max: 24,
          step: 1,
          required: true
        },
      },
      {
        className: 'col-md-3',
        type: 'input',
        key: 'scheduleMin',
        templateOptions: {
          label: 'Minutes',
          type: 'number',
          min: 0,
          max: 59,
          step: 15,
          required: true
        },
      }]
    }
  ];


  rjform = new FormGroup({});
  rjmodel: any = {};
  rjoptions: FormlyFormOptions = {};
  rjfields: FormlyFieldConfig[] = [
    {
      fieldGroupClassName: 'row',
      fieldGroup: [{
        className: 'col-md-12',
        type: 'textarea',
        key: 'reason',
        templateOptions: {
          label: 'Reason if any',
          rows: 5
        },
      }]
    }
  ];


  private refreshListEmitterSubscription: Subscription;
  constructor(private appointmentService: AppointmentService, private toastr: ToastrService, private modalService: NgbModal) { }


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
    this.appointmentService.getAppointmentRequestsForDoctor()
      .subscribe(res => {
        this.appointmentRequests = (res.body as any[]);
      }, err => {
        console.log(err);
        this.toastr.error('Could not pull appointment requests');
      });
  }

  getHours(apmtReq) {
    if (!apmtReq.desiredStartHours || !apmtReq.desiredEndHours) {
      return 'NA';
    } else {
      return apmtReq.desiredStartHours + ' to ' + apmtReq.desiredEndHours;
    }
  }

  open(content, apmt) {
    this.apprmodel.scheduleDate = apmt.desiredDate;
    this.apprmodel.scheduleHours = !!apmt.desiredStartHours ? 10 : apmt.desiredStartHours;
    this.apprmodel.scheduleMin = 0;
    this.modalService.open(content)
      .result.then(result => {
        console.log(result, this.apprmodel, apmt);
        const requestTime = this.apprmodel.scheduleDate + 'T'
          + this.getZeroAppended(this.apprmodel.scheduleHours)
          + ':' + this.getZeroAppended(this.apprmodel.scheduleMin)
          + ':00.000Z';

        this.appointmentService.approveAppointmentRequest(apmt.id, requestTime)
          .subscribe(res => {
            console.log(res.body);
            this.toastr.success('Approved');
            this.appointmentService.refreshListEmitter.emit();
            this.load();
          }, err => {
            this.toastr.error('Error approving');
            console.log(err);
          });
      }, reason => {
        console.log(reason);
      });
  }

  getZeroAppended(n: number) {
    if (n < 10) {
      return '0' + n;
    } else {
      return '' + n;
    }
  }

  openReject(content, id) {
    this.modalService.open(content)
    .result.then(result => {
      console.log(result);
      this.appointmentService.rejectAppointmentRequest(id, this.rjmodel.reason)
      .subscribe(res => {
        this.toastr.success('Successfully rejected');
        this.load();
      })
    }, reason => {  
        console.log(reason);
    });
  }


}
