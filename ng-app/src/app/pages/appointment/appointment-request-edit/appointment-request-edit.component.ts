import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FormlyFormOptions, FormlyFieldConfig } from '@ngx-formly/core';
import {  NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { getAppointmentHours } from 'src/app/form-models/doctor.formmodel';
import { ToastrService } from 'ngx-toastr';
import { AppointmentService } from 'src/app/shared/service/appointment.service';

@Component({
  selector: 'app-appointment-request-edit',
  templateUrl: './appointment-request-edit.component.html',
  styles: [
  ]
})
export class AppointmentRequestEditComponent implements OnInit {

  doctorId: number;
  form = new FormGroup({});
  model: any = {};
  options: FormlyFormOptions = {};

  fields: FormlyFieldConfig[] = [
    {
      fieldGroupClassName: 'row',
      fieldGroup: [
        {
          className: 'col-md-5',
          key: 'desiredDate',
         // type: 'urna-datepicker',
          type: 'input',
          templateOptions: {
            label: 'Desired Date',
            type: 'date',
            required: true,
          //  change: this.onChangeDesiredDate.bind(this)
            },
        },
        {
          className: 'col-md-4',
          type: 'select',
          key: 'desiredStartHours',
          templateOptions: {
            label: 'Hours Between',
            options: getAppointmentHours(),
          }
        },
        {
          className: 'col-md-3',
          type: 'select',
          key: 'desiredEndHours',
          templateOptions: {
            label: 'And',
            options: getAppointmentHours(),
          }
        }
      ],
    },
    {
      fieldGroupClassName: 'row',
      fieldGroup: [
        {
          className: 'col-md-5',
          key: 'dateFlexible',
          type: 'checkbox',
          templateOptions: {
            label: 'Date Flexible?',
          }
        },
        {
          className: 'col-md-6',
          key: 'hoursFlexible',
          type: 'checkbox',
          templateOptions: {
            label: 'Hours Flexible?',
          }
        }
      ],
    },
    {
      fieldGroupClassName: 'row',
      fieldGroup: [
        {
          className: 'col-md-12',
          key: 'healthIssueDescription',
          type: 'textarea',
          templateOptions: {
            label: 'Health Issue',
            description: 'Describe your health issue',
            rows: 8
          }
        }
      ],
    }];

  constructor(
    public activeModal: NgbActiveModal, 
    private appointmentService: AppointmentService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.model.dateFlexible = true;
    this.model.hoursFlexible = true;
  }

  onCreate() {
    const aptreq = Object.assign({}, this.model);
    aptreq.doctorId = this.doctorId;
    console.log(aptreq);
    this.appointmentService.createAppointmentRequest(aptreq)
    .subscribe(res => {
      this.appointmentService.refreshListEmitter.emit();
      this.toastr.success('Appointment Created Succesfully');
      this.activeModal.close('Y');
    }, err => {
      this.toastr.error('Could not book an appointment');
    });
  }

  onChangeDesiredDate(v) {
    console.log(v);
  }

}
