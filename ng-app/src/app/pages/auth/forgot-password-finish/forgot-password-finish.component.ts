import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AccountService } from 'src/app/shared/service/account.service';
import { MustMatch } from 'src/app/shared/validator/custom.validators';

@Component({
  selector: 'app-forgot-password-finish',
  templateUrl: './forgot-password-finish.component.html',
  styleUrls: ['./forgot-password-finish.component.scss']
})
export class ForgotPasswordFinishComponent implements OnInit {

  key: string;
  form: FormGroup;
  submitted = false;
  loading = false;
  constructor(private fb: FormBuilder, private route: ActivatedRoute, private router: Router, private accountService: AccountService, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.key = this.route.snapshot.queryParamMap.get('key');
    this.form = this.fb.group({
      newPassword: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(16)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(16)]]
    }, {
      validator: MustMatch('newPassword', 'confirmPassword')
    });
  }
  get f() { return this.form.controls; }

  onSubmit() {
    this.submitted = true;
    if(this.form.invalid) return;
    this.loading = true;
    this.accountService.resetPasswordFinish(this.key, this.form.get('newPassword').value)
    .subscribe(res => {
      this.loading = false;
      this.toastr.success('Password changed successfully. Please login.');
      this.router.navigate(['/login']);
    }, err => {
      this.loading = false;
      this.toastr.error('Could not reset password');
    });
  }

}
