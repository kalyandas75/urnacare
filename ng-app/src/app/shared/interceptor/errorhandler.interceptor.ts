import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpErrorResponse, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';

@Injectable()
export class ErrorHandlerInterceptor implements HttpInterceptor {
    constructor(private toastr: ToastrService) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(
            tap(
                (event: HttpEvent<any>) => {},
                (err: any) => {
                    if (err instanceof HttpErrorResponse) {
                        if (!(err.status === 401 && (err.message === '' || (err.url && err.url.includes('/api/account'))))) {
                            // this.eventManager.broadcast({ name: 'fsgpsApp.httpError', content: err });
                            if (err && err.error && err.error.message && err.error.title) {
                                this.toastr.error(err.error.message + 
                                    (err.error.detail ? (':' + err.error.detail) : ''), err.error.title, null);
                            }
                        }
                    }
                }
            )
        );
    }
}
