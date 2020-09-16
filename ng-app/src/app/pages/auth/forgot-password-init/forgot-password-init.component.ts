import { ThrowStmt } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { AccountService } from 'src/app/shared/service/account.service';

@Component({
  selector: 'app-forgot-password-init',
  templateUrl: './forgot-password-init.component.html',
  styleUrls: ['./forgot-password-init.component.scss']
})
export class ForgotPasswordInitComponent implements OnInit {
  form: FormGroup;
  loading = false;
  submitted = false;
  sent = false;
  constructor(private fb: FormBuilder, private accountService: AccountService, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  get f() { return this.form.controls; }

  onSubmit() {
    this.submitted = true;
    if(this.form.invalid) return;
    this.loading = true;
    this.accountService.resetPasswordInit(this.form.get('email').value)
    .subscribe(res => {
      this.sent = true;
      this.loading = false;
    }, err => {
      this.loading = false;
      this.toastr.error('Error reseting password');
    });
  }
}
