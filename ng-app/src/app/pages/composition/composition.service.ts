import { HttpClient } from "@angular/common/http";
import { EventEmitter, Injectable } from "@angular/core";
import { hasUncaughtExceptionCaptureCallback } from 'process';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SERVICE_API_URL } from "src/app/app.constant";

@Injectable({
  providedIn: "root",
})
export class CompositionService {
  reloadEmitter = new EventEmitter();
  constructor(private http: HttpClient) {}

  getAll(pageable: { page: number, size: number, sort: string}) {
    return this.http.get(SERVICE_API_URL + '/compositions?page=' + pageable.page + '&size=' + pageable.size + '&sort=' + pageable.sort, { observe: 'response'});
  }

  update(id, composition) {
    return this.http.put(SERVICE_API_URL + "/compositions/" + id, composition, {
      observe: "response",
    });
  }

  create(composition) {
    return this.http.post(SERVICE_API_URL + "/compositions", composition, {
      observe: "response",
    });
  }

  delete(id) {
    return this.http.delete(SERVICE_API_URL + "/compositions/" + id, {
      observe: "response",
    });
  }

  search(q: string) {
    if (!q || q.length < 3) {
      return of([]);
    }
    return this.http.get(SERVICE_API_URL + '/compositions/search?q=' + q, {
      observe: "response",
    }).pipe(
      map(response => {
        return response.body;
      })
    );
  }
}