import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { SPECIALITIES } from 'src/app/app.constant';
import { AccountService } from 'src/app/shared/service/account.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  form: FormGroup;

  submitted = false;
  loading = false;
  profile;
  genders = ['Female', 'Male', 'Other'];
  specialities = SPECIALITIES;
  constructor(private fb: FormBuilder, private accountService: AccountService, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.loadProfile();
  }

  loadProfile() {
    this.accountService.fetch()
    .subscribe(res => {
      this.profile = res.body;
      this.initForm();
    }, err => {
      this.toastr.error('Couldnot load profile');
    });
  }

  initForm() {

    if(this.accountService.hasAnyAuthority(['ROLE_DOCTOR'])) {
      console.log('role is doctor');
      this.form = this.fb.group({
        email: [this.profile.email, [Validators.required, Validators.email]],
        firstName: [this.profile.firstName, [Validators.required]],
        lastName: [this.profile.lastName, [Validators.required]],
        phoneNumber: [this.profile.phoneNumber, [Validators.required, Validators.pattern(/^[1-9]{1}[0-9]{9}$/)]],
        alternatePhoneNumber: [this.profile.alternatePhoneNumber, [Validators.pattern(/^[1-9]{1}[0-9]{9}$/)]],
        gender: [this.profile.gender],
        registrationNumber: [this.profile.registrationNumber, [Validators.required]],
        primarySpeciality: [this.profile.primarySpeciality, [Validators.required]],
        qualifications: this.fb.array([], {updateOn: 'blur'}),
        otherSpecialities: this.fb.array([]),
        addresses: this.fb.array([])
      });
      if(this.profile.qualifications && this.profile.qualifications.length > 0) {
        this.profile.qualifications.forEach(q => {
          this.addQualification(q);
        });
      }
      if(this.profile.otherSpecialities && this.profile.otherSpecialities.length > 0) {
        this.profile.otherSpecialities.forEach(s => {
          this.addOtherSpeciality(s);
        });
      }
    } else {
        this.form = this.fb.group({
          email: [this.profile.email, [Validators.required, Validators.email]],
          firstName: [this.profile.firstName, [Validators.required]],
          lastName: [this.profile.lastName, [Validators.required]],
          phoneNumber: [this.profile.phoneNumber, [Validators.required, Validators.pattern(/^[1-9]{1}[0-9]{9}$/)]],
          alternatePhoneNumber: [this.profile.alternatePhoneNumber, [Validators.pattern(/^[1-9]{1}[0-9]{9}$/)]],
          gender: [this.profile.gender],
          addresses: this.fb.array([], {updateOn: 'blur'})
        });      
    }
    if(this.profile.addresses && this.profile.addresses.length > 0) {
      this.profile.addresses.forEach(a => {
        this.addAddress(a);
      });
    }
      
  }

  private getFormArray(name: string) {
    return (this.form.get(name) as FormArray);
  }

  addQualification(qualification: string) {
    if(this.getFormArray('qualifications') && this.getFormArray('qualifications').length > 0 && 
    !this.getFormArray('qualifications').controls[this.getFormArray('qualifications').length - 1].value) {
      return;
    }
    this.getFormArray('qualifications').push(new FormControl(qualification));
  }
  removeQualification(index: number) {
    (this.form.get('qualifications') as FormArray).removeAt(index);
  }

  addOtherSpeciality(speciality: string) {
    if(this.getFormArray('otherSpecialities') && this.getFormArray('otherSpecialities').length > 0 && 
    !this.getFormArray('otherSpecialities').controls[this.getFormArray('otherSpecialities').length - 1].value) {
      return;
    }
    (this.form.get('otherSpecialities') as FormArray).push(new FormControl(speciality));
  }
  removeOtherSpeciality(index: number) {
    (this.form.get('otherSpecialities') as FormArray).removeAt(index);
  }

  addAddress(address?: any) {
    const addressArray = this.getFormArray('addresses');
    if(!!addressArray && addressArray.length > 0) {
      if(addressArray.controls[addressArray.length - 1].invalid) {
        return;
      }
    }
    addressArray.push(this.fb.group({
      address: [!! address && !!address.address ? address.address: '', Validators.required],
      city: [!! address && !!address.city ? address.city: '', Validators.required],
      state: [!! address && !!address.state ? address.state: '', Validators.required],
      pin: [!! address && !!address.pin ? address.pin: '', [Validators.required, Validators.pattern(/^[1-9][0-9]{5}$/)]],
      type: [!! address && !!address.type ? address.type : '', [Validators.required]]
    }));
  }

  removeAddress(index: number) {
    this.getFormArray('addresses').removeAt(index);
  }

  get f() { return this.form.controls; }

  onSubmit() {
    console.log(this.form);
    this.submitted = true;
    if(this.form.invalid) {
      console.log((this.form.get('addresses') as FormArray).controls[0].get('address').errors);
      return;
    }
    const data = this.form.value;
    data.id = this.profile.id;
    this.accountService.save(this.form.value)
    .subscribe(res => {
      this.toastr.success('Profile saved successfully');
      this.loadProfile();
    })
  }
}
