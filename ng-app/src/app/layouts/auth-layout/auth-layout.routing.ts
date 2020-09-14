import { Routes } from '@angular/router';
import { LandingComponent } from 'src/app/pages/auth/landing/landing.component';
import { LoginComponent } from 'src/app/pages/auth/login/login.component';
import { RegisterComponent } from 'src/app/pages/auth/register/register.component';
import { ActivateComponent } from 'src/app/pages/auth/activate/activate.component';



export const AuthLayoutRoutes: Routes = [
    { path: 'landing', component: LandingComponent},
    { path: 'login', component: LoginComponent},
    { path: 'register', component: RegisterComponent},
    { path: 'activate', component: ActivateComponent},
];
