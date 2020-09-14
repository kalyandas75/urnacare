import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { RouterModule } from "@angular/router";
import { ToastrModule } from 'ngx-toastr';

import { AppComponent } from "./app.component";
import { AdminLayoutComponent } from "./layouts/admin-layout/admin-layout.component";
import { AuthLayoutComponent } from './layouts/auth-layout/auth-layout.component';

import { NgbModule } from "@ng-bootstrap/ng-bootstrap";

import { AppRoutingModule } from "./app-routing.module";
import { ComponentsModule } from "./components/components.module";
import { NgxWebstorageModule } from 'ngx-webstorage';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { AuthInterceptor } from './shared/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './shared/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './shared/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './shared/interceptor/notification.interceptor';
@NgModule({
  imports: [
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    ComponentsModule,
    NgbModule,
    RouterModule,
    AppRoutingModule,
    ToastrModule.forRoot(),
    NgxWebstorageModule.forRoot()
  ],
  declarations: [AppComponent, AdminLayoutComponent, AuthLayoutComponent],
  providers: [    { provide: LocationStrategy, useClass: HashLocationStrategy },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
  },
  {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthExpiredInterceptor,
      multi: true
  },
  {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlerInterceptor,
      multi: true
  },
  {
      provide: HTTP_INTERCEPTORS,
      useClass: NotificationInterceptor,
      multi: true
  }
],
  bootstrap: [AppComponent]
})
export class AppModule {}
