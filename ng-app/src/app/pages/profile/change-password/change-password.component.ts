import { Component, OnInit } from '@angular/core';
import { AccountService } from 'src/app/shared/service/account.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { MustMatch } from 'src/app/shared/validator/custom.validators';
import { LoginService } from 'src/app/shared/service/login.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {
  form: FormGroup;
  loading = false;
  submitted = false;
  constructor(private fb: FormBuilder, private accountService: AccountService, private toastr: ToastrService, private loginService: LoginService) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      currentPassword: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(16)]],
      newPassword: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(16)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(16)]]
    }, {
      validator: MustMatch('newPassword', 'confirmPassword')
    });
  }

  get f() { return this.form.controls; }

  onSubmit() {
    this.submitted = true;
    if(this.form.invalid) {
      return;
    }
    this.loading = true;
    this.accountService.savePassword(this.form.get('newPassword').value, this.form.get('currentPassword').value)
    .subscribe(res => {
      this.toastr.success('Password changed successfully. Please login again.');
      this.loading = false;
      this.loginService.logout();
    }, err => {
      this.loading = false;
      this.toastr.error(err.error.message + 
        (err.error.detail ? (':' + err.error.detail) : ''), err.error.title, null);
    });
  }
}
