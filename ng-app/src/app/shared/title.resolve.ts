import { Injectable } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class TitleResolver implements Resolve<any> {

  constructor(
    private title: Title
    ) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      this.title.setTitle(route.data['title']);
      return null;
  }

}
