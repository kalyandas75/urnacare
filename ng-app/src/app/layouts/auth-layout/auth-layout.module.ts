import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthLayoutRoutes } from './auth-layout.routing';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from "@angular/common/http";
import { LandingComponent } from 'src/app/pages/auth/landing/landing.component';
import { LoginComponent } from 'src/app/pages/auth/login/login.component';
import { RegisterComponent } from 'src/app/pages/auth/register/register.component';
import { ActivateComponent } from 'src/app/pages/auth/activate/activate.component';
import { ForgotPasswordFinishComponent } from 'src/app/pages/auth/forgot-password-finish/forgot-password-finish.component';
import { ForgotPasswordInitComponent } from 'src/app/pages/auth/forgot-password-init/forgot-password-init.component';
@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AuthLayoutRoutes),
    FormsModule,
    HttpClientModule,
    NgbModule,
    ReactiveFormsModule
  ],
  declarations: [
    LandingComponent,
    LoginComponent,
    RegisterComponent,
    ActivateComponent,
    ForgotPasswordInitComponent,
    ForgotPasswordFinishComponent
  ]
})
export class AuthLayoutModule { }
