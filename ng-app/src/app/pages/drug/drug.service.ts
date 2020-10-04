import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { SERVICE_API_URL } from 'src/app/app.constant';

@Injectable({
  providedIn: 'root'
})
export class DrugService {
  reloadEmitter = new EventEmitter();
  constructor(private http: HttpClient) { }

  getAll(pageable: { page: number, size: number, sort: string}) {
    return this.http.get(SERVICE_API_URL + '/drugs?page=' + pageable.page + '&size=' + pageable.size + '&sort=' + pageable.sort, { observe: 'response'});
  }

  search(q: string) {
    return this.http.get(SERVICE_API_URL + '/drugs/search?q=' + q, { observe: 'body'});
  }

  create(drug) {
    return this.http.post(SERVICE_API_URL + '/drugs', drug, { observe: 'response'});
  }

  update(id, drug) {
    return this.http.put(SERVICE_API_URL + '/drugs/' + id, drug, { observe: 'response'});
  }

  delete(id) {
    return this.http.delete(SERVICE_API_URL + "/drugs/" + id, {
      observe: "response",
    });
  }
}
