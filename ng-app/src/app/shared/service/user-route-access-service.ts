import { Injectable, isDevMode } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';

import { StateStorageService } from './state-storage.service';
import { AccountService } from './account.service';
import { SessionStorageService } from 'ngx-webstorage';
import { AuthServerProvider } from './auth-jwt.service';

@Injectable({ providedIn: 'root' })
export class UserRouteAccessService implements CanActivate {
    constructor(
        private router: Router,
        private accountService: AccountService,
        private stateStorageService: StateStorageService,
        private authService: AuthServerProvider
    ) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Promise<boolean> {
        const authorities = route.data['authorities'];
        if (this.authService.getToken()) {
          return true;
        } else {
          this.router.navigate(['/authentication']);
          return false;
        }
        // We need to call the checkLogin / and so the accountService.identity() function, to ensure,
        // that the client has a principal too, if they already logged in by the server.
        // This could happen on a page refresh.
        return this.checkLogin(authorities, state.url);
    }

    checkLogin(authorities: string[], url: string): Promise<boolean> {
        return this.accountService.identity().then(account => {
            if (!authorities || authorities.length === 0) {
                return true;
            }

            if (account) {
                const hasAnyAuthority = this.accountService.hasAnyAuthority(authorities);
                if (hasAnyAuthority) {
                    return true;
                }
                if (isDevMode()) {
                    console.error('User has not any of required authorities: ', authorities);
                }
                return false;
            }

            this.stateStorageService.storeUrl(url);
            if (!account) {
                this.router.navigate(['authentication']);
            } else {
                this.router.navigate(['403']);
            }
            return false;
        });
    }
}
