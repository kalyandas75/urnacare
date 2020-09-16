import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FormlyFormOptions, FormlyFieldConfig } from '@ngx-formly/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { AppointmentService } from 'src/app/shared/service/appointment.service';

@Component({
  selector: 'app-consultation-edit',
  templateUrl: './consultation-edit.component.html',
  styles: [
  ]
})
export class ConsultationEditComponent implements OnInit {
  appointment;
  form = new FormGroup({});
  model: any = {};
  options: FormlyFormOptions = {};

  fields: FormlyFieldConfig[] = [
    {
      fieldGroupClassName: 'row',
      fieldGroup: [
        {
          className: 'col-md-12',
          key: 'durationOfHealthIssue',
          type: 'input',
          templateOptions: {
            label: 'Health issue since',
            description: 'Since when the health issue is'
            },
        }
      ],
    },
    {
      fieldGroupClassName: 'row',
      fieldGroup: [
        {
          className: 'col-md-12',
          key: 'healthIssue',
          type: 'textarea',
          templateOptions: {
            label: 'Presentation',
            required: true,
            rows: 8,
            description: 'Description of the health issue'
            },
        }
      ],
    },
    {
      fieldGroupClassName: 'row',
      fieldGroup: [
        {
          className: 'col-md-12',
          key: 'consultationResponse',
          type: 'textarea',
          templateOptions: {
            label: 'Rx',
            description: 'Consultation or Prescription',
            required: true,
            rows: 8
          }
        }
      ],
    }];
  constructor(public activeModal: NgbActiveModal, 
    private appointmentService: AppointmentService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
  }

  onCreate() {
    const consultation = Object.assign({}, this.model);
    this.appointmentService.addConsultation(this.appointment.id, consultation)
    .subscribe(res => {
      this.appointmentService.refreshListEmitter.emit();
      this.toastr.success('Consultation Added Successfully');
      this.activeModal.close('Y');
    }, err => {
      this.toastr.error('Could not add a consultation');
    });
  }

}
