import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { SERVICE_API_URL } from 'src/app/app.constant';
import { Router } from '@angular/router';


@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(private localStorage: LocalStorageService,
        private sessionStorage: SessionStorageService,
        private router: Router) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        if (!request || !request.url || (/^http/.test(request.url) && !(SERVICE_API_URL && request.url.startsWith(SERVICE_API_URL)))) {
            return next.handle(request);
        }

        const token = this.localStorage.retrieve('authenticationToken') || this.sessionStorage.retrieve('authenticationToken');
        console.log('TOKEN', token);
        if (!!token) {
            request = request.clone({
                setHeaders: {
                    Authorization: 'Bearer ' + token
                }
            });

        }
        return next.handle(request);
    }
}
