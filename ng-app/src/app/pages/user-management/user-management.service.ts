import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { SERVICE_API_URL } from 'src/app/app.constant';

@Injectable({
  providedIn: 'root'
})
export class UserManagementService {

  reloadEmitter = new EventEmitter();

  constructor(private http: HttpClient) { }

  getAllUsers(pageable: { page: number, size: number, sort: string}) {
    return this.http.get(SERVICE_API_URL + '/users?page=' + pageable.page + '&size=' + pageable.size + '&sort=' + pageable.sort, { observe: 'response'});
  }

  saveUser(user) {
    return this.http.post(SERVICE_API_URL + '/users', user, { observe: 'response'});
  }
}
