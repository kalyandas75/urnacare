import { HttpClient } from "@angular/common/http";
import { EventEmitter, Injectable } from "@angular/core";
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SERVICE_API_URL } from "src/app/app.constant";

@Injectable({
  providedIn: "root",
})
export class ManufacturerService {
  reloadEmitter = new EventEmitter();
  constructor(private http: HttpClient) {}

  getAll(pageable: { page: number, size: number, sort: string}) {
    return this.http.get(SERVICE_API_URL + '/manufacturers?page=' + pageable.page + '&size=' + pageable.size + '&sort=' + pageable.sort, { observe: 'response'});
  }

  update(id, manufacturer) {
    return this.http.put(SERVICE_API_URL + "/manufacturers/" + id, manufacturer, {
      observe: "response",
    });
  }

  create(manufacturer) {
    return this.http.post(SERVICE_API_URL + "/manufacturers", manufacturer, {
      observe: "response",
    });
  }

  delete(id) {
    return this.http.delete(SERVICE_API_URL + "/manufacturers/" + id, {
      observe: "response",
    });
  }

  search(q: string) {
    if (!q || q.length < 3) {
      return of([]);
    }
    return this.http.get(SERVICE_API_URL + '/manufacturers/search?q=' + q, {
      observe: "response",
    }).pipe(
      map(response => {
        return response.body;
      })
    );
  }
}

