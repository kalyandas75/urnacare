import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormlyFieldConfig, FormlyFormOptions } from '@ngx-formly/core';
import { ToastrService } from 'ngx-toastr';
import { PHONE_REGEX } from 'src/app/app.constant';
import { UserManagementService } from '../user-management.service';

@Component({
  selector: 'app-user-management-edit',
  templateUrl: './user-management-edit.component.html',
  styleUrls: ['./user-management-edit.component.scss']
})
export class UserManagementEditComponent implements OnInit {

  user;
  form: FormGroup;
  submitted = false;
  loading = false;
  genders = ['Female', 'Male', 'Other'];
  authorities = ['ROLE_ADMIN', 'ROLE_SUPPORT', 'ROLE_PATIENT', 'ROLE_DOCTOR'];

  constructor(public activeModal: NgbActiveModal, 
    private userManagementService: UserManagementService,
    private fb: FormBuilder,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    console.log('user', this.user, !this.user.email);
    this.form = this.fb.group({
      email: [(!!this.user.email && !!this.user.email) ? this.user.email : '', [Validators.required, Validators.email]],
      firstName: [(!!this.user.email && !!this.user.firstName) ? this.user.firstName : '', [Validators.required]],
      lastName: [(!!this.user.email && !!this.user.lastName) ? this.user.lastName : '', [Validators.required]],
      phoneNumber: [(!!this.user.email && !!this.user.phoneNumber) ? this.user.phoneNumber : '' , [Validators.required, Validators.pattern(PHONE_REGEX)]],
      alternatePhoneNumber: [(!!this.user.email && !!this.user.alternatePhoneNumber) ? this.user.alternatePhoneNumber : '' , [Validators.pattern(PHONE_REGEX)]],
      gender: [(!!this.user.email && !!this.user.gender) ? this.user.gender : 'Other'],
      authority: [{ value: (!!this.user.email && !!this.user.authority) ? this.user.authority : '', disabled: !!this.user.email}, [Validators.required]],
      activated: [{value : !this.user.email ? true : this.user.activated, disabled: !this.user.email}]
    });
    if(!this.user.email) {
      this.form.addControl('password', new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(16)]));
    }
  }
  get f() { return this.form.controls; }

  onSubmit() {
    this.submitted = true;
    if(this.form.invalid) {
      return;
    }
    console.log('sending', this.form.value);
    this.userManagementService.saveUser(this.form.value, this.user.id)
    .subscribe(res => {
      this.toastr.success('User saved successfully');
      this.userManagementService.reloadEmitter.emit();
      this.activeModal.close('Y');
    }, err => {
      this.toastr.error('Unable to save user');
    });
    
  }

  getAuthorities() {
    if(!this.user.email) {
      return ['ROLE_ADMIN', 'ROLE_SUPPORT'];
    }
    return this.authorities;
  }

}
