import { HttpClient } from "@angular/common/http";
import { EventEmitter, Injectable } from "@angular/core";
import { SERVICE_API_URL } from "src/app/app.constant";

@Injectable({
  providedIn: "root",
})
export class CompositionService {
  reloadEmitter = new EventEmitter();
  constructor(private http: HttpClient) {}

  getAllCompositions() {
    return this.http.get(SERVICE_API_URL + "/compositions", {
      observe: "response",
    });
  }

  saveComposition(composition) {
    return this.http.post(SERVICE_API_URL + "/compositions", composition, {
      observe: "response",
    });
  }
}
