import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray, FormControl } from '@angular/forms';
import { RegisterService } from 'src/app/shared/service/register.service';
import { PHONE_REGEX, SPECIALITIES } from 'src/app/app.constant';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  userType = 'P';

  patientForm: FormGroup;
  doctorForm: FormGroup;

  submitted = false;
  loading = false;

  registered = false;

  specialities = SPECIALITIES;

  constructor(private fb: FormBuilder, private registerService: RegisterService) { }

  ngOnInit(): void {
    this.initPatientForm();
  }

  onChangeUsertype(event) {
    this.userType = event.target.value;
    if(this.userType === 'P') {
      this.initPatientForm();
    } else if(this.userType === 'D') {
      this.initDoctorForm();
    }
  }
  get fP() { return this.patientForm.controls; }
  get fD() { return this.doctorForm.controls; }

  initPatientForm() {
    this.patientForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(16)]],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      phoneNumber: ['', [Validators.required, Validators.pattern(PHONE_REGEX)]],
      alternatePhoneNumber: ['', [Validators.pattern(PHONE_REGEX)]]
    });
  }

  initDoctorForm() {
    this.doctorForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(16)]],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      phoneNumber: ['', [Validators.required, Validators.pattern(PHONE_REGEX)]],
      alternatePhoneNumber: ['', [Validators.pattern(PHONE_REGEX)]],
      registrationNumber: ['', [Validators.required]],
      primarySpeciality: ['', [Validators.required]]
    });
  }

  onSubmit() {
    this.submitted = true;

    if(this.userType === 'P') {
      if(this.patientForm.invalid) {
        return;
      }
      this.registerService.registerPatient(this.patientForm.value)
      .subscribe(res => {
        this.registered = true;
      });
    } else if(this.userType === 'D') {
      if(this.doctorForm.invalid) {
        return;
      }
      this.registerService.registerDoctor(this.doctorForm.value)
      .subscribe(res => {
        this.registered = true;
      });
    }
  }

}
