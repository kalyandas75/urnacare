import { Routes } from '@angular/router';
import { LandingComponent } from 'src/app/pages/auth/landing/landing.component';
import { LoginComponent } from 'src/app/pages/auth/login/login.component';



export const AuthLayoutRoutes: Routes = [
    { path: 'landing', component: LandingComponent},
    { path: 'login', component: LoginComponent}
];
