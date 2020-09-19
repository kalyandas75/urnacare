import { Injectable, EventEmitter } from '@angular/core';
import { SessionStorageService } from 'ngx-webstorage';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';
import { DomSanitizer } from '@angular/platform-browser';
import { SERVICE_API_URL } from 'src/app/app.constant';

@Injectable({ providedIn: 'root' })
export class AccountService {
    private userIdentity: any;
    private authenticated = false;
    private authenticationState = new Subject<any>();

    public accountChangeEvent = new EventEmitter<boolean>();

    constructor(private sessionStorage: SessionStorageService,
        private http: HttpClient,
        private domSanitizer: DomSanitizer) { }

    fetch(): Observable<HttpResponse<Account>> {
        return this.http.get<Account>(SERVICE_API_URL + '/account', { observe: 'response' });
    }

    save(account: any): Observable<HttpResponse<any>> {
        return this.http.post(SERVICE_API_URL + '/account', account, { observe: 'response' });
    }

    authenticate(identity) {
        this.userIdentity = identity;
        this.authenticated = identity !== null;
        this.authenticationState.next(this.userIdentity);
    }

    hasAnyAuthority(authorities: string[]): boolean {
        if (!this.authenticated || !this.userIdentity || !this.userIdentity.authority) {
            return false;
        }
        
        for (let i = 0; i < authorities.length; i++) {
            if (this.userIdentity.authority === authorities[i]) {
                return true;
            }
        }

        return false;
    }

    hasAuthority(authority: string): Promise<boolean> {
        if (!this.authenticated) {
            return Promise.resolve(false);
        }

        return this.identity().then(
            id => {
                return Promise.resolve(id.authority && id.authority === authority);
            },
            () => {
                return Promise.resolve(false);
            }
        );
    }

    identity(force?: boolean): Promise<any> {
        if (force) {
            this.userIdentity = undefined;
        }

        // check and see if we have retrieved the userIdentity data from the server.
        // if we have, reuse it by immediately resolving
        if (this.userIdentity) {
            return Promise.resolve(this.userIdentity);
        }

        // retrieve the userIdentity data from the server, update the identity object, and then resolve.
        return this.fetch()
            .toPromise()
            .then(response => {
                const account = response.body;
                if (account) {
                    this.userIdentity = account;
                    this.authenticated = true;
                } else {
                    this.userIdentity = null;
                    this.authenticated = false;
                }
                this.authenticationState.next(this.userIdentity);
                return this.userIdentity;
            })
            .catch(err => {
                this.userIdentity = null;
                this.authenticated = false;
                this.authenticationState.next(this.userIdentity);
                return null;
            });
    }

    isAuthenticated(): boolean {
        return this.authenticated;
    }

    isIdentityResolved(): boolean {
        return this.userIdentity !== undefined;
    }

    getAuthenticationState(): Observable<any> {
        return this.authenticationState.asObservable();
    }

    getImageUrl(): string {
        return this.isIdentityResolved() ? this.userIdentity.imageUrl : null;
    }

    image(): Observable<any> {
        return this.http
            .get(SERVICE_API_URL + 'api/account/image', { observe: 'response', responseType: 'blob' })
            .pipe(
                map(e => {
                    return this.domSanitizer.bypassSecurityTrustUrl(URL.createObjectURL(e.body));
                })
            );
    }

    savePassword(newPassword: string, currentPassword: string): Observable<any> {
        const data = {currentPassword: currentPassword, newPassword: newPassword};
        return this.http.post(SERVICE_API_URL + '/account/change-password', data);
    }

    resetPasswordInit(email: string) {
        return this.http.post(SERVICE_API_URL + '/account/reset-password/init', email, { observe: 'response'});
    }

    resetPasswordFinish(key: string, newPassword: string) {
        return this.http.post(SERVICE_API_URL + '/account/reset-password/finish', { key: key, newPassword: newPassword}, { observe: 'response'});
    }


}
