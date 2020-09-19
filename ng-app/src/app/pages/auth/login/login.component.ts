import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from 'src/app/shared/service/login.service';
import { Router } from '@angular/router';
import { AccountService } from 'src/app/shared/service/account.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loading = false;
  submitted = false;
  constructor(private fb: FormBuilder, private loginService: LoginService, private router: Router, private accountService: AccountService) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
      rememberMe: [false]
    });
  }

  get f() { return this.loginForm.controls; }

  onSubmit() {
    this.submitted = true;
    if(this.loginForm.invalid) {
      return;
    }
    this.loading = true;
    this.loginService.login(this.loginForm.value)
    .then((data) => {
      console.log(this.accountService.hasAnyAuthority(['ROLE_PATIENT']));
      if(this.accountService.hasAnyAuthority(['ROLE_PATIENT'])) {
        this.router.navigate(['/patient-appointment']);
      } else if(this.accountService.hasAnyAuthority(['ROLE_DOCTOR'])) {
        this.router.navigate(['/doctor-appointment']);
      } else {
        this.router.navigate(['/dashboard']);
      }
      this.loading = false;         
      }).finally(() => {
          this.loading = false;
      });
  }

}
