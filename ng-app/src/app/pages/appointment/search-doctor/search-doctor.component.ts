import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { FormGroup } from '@angular/forms';
import { FormlyFormOptions, FormlyFieldConfig } from '@ngx-formly/core';
import { getSpecialities } from 'src/app/form-models/doctor.formmodel';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AppointmentRequestEditComponent } from '../appointment-request-edit/appointment-request-edit.component';
import { AppointmentService } from 'src/app/shared/service/appointment.service';

@Component({
  selector: 'app-search-doctor',
  templateUrl: './search-doctor.component.html',
  styles: [
  ]
})
export class SearchDoctorComponent implements OnInit {
  doctors: any[];
  searched = false;

  form = new FormGroup({});
  model: any = {};
  options: FormlyFormOptions = {};
  fields: FormlyFieldConfig[] = [
    {
    fieldGroupClassName: 'row',
    fieldGroup: [{
      className: 'col-12',
      type: 'select',
      key: 'speciality',
      templateOptions: {
          label: 'Speciality',
          required: true,
          options: getSpecialities(),
          change: this.onChangeSpeciality.bind(this)
      },
    }]
  }
  ];
  constructor(private appointmentService: AppointmentService, 
    private toastr: ToastrService,
    private modalService: NgbModal) { }

  ngOnInit(): void {
  }

  onSearch() { 
    this.searched = true;
    this.appointmentService.searchDoctor(this.model.speciality)
    .subscribe(res => {
      this.doctors = res.body as any[];
    }, err => {
      console.log(err);
      this.toastr.error('Unable to find Doctors.');
    });
  }

  open(doctorId) {
    console.log(doctorId);
    const modalRef = this.modalService.open(AppointmentRequestEditComponent);
    modalRef.componentInstance.doctorId = doctorId;
  }

  onChangeSpeciality() {
    this.searched = false;
  }

}
