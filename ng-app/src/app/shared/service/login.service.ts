import { Injectable } from '@angular/core';
import { AccountService } from './account.service';
import { AuthServerProvider } from './auth-jwt.service';
import { Router } from '@angular/router';


@Injectable({ providedIn: 'root' })
export class LoginService {
    constructor(private accountService: AccountService,
        private authServerProvider: AuthServerProvider,
        private router: Router) {}

    login(credentials, callback?) { console.log('credentials ==>', credentials);
        const cb = callback || function() {};

        return new Promise((resolve, reject) => {
            this.authServerProvider.login(credentials).subscribe(
                data => {
                    this.accountService.identity(true).then(account => {
                        resolve(data);
                    });
                    return cb();
                },
                err => {
                    this.logout();
                    reject(err);
                    return cb(err);
                }
            );
        });
    }

    loginWithToken(jwt, rememberMe) {
        return this.authServerProvider.loginWithToken(jwt, rememberMe);
    }

    logout() {
        this.authServerProvider.logout().subscribe();
        this.accountService.authenticate(null);
        this.router.navigate(['/']);
    }
    isLoggedIn() {
      return !!this.authServerProvider.getToken();
    }
}
