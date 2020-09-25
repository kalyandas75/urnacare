import { HttpClient } from "@angular/common/http";
import { EventEmitter, Injectable } from "@angular/core";
import { SERVICE_API_URL } from "src/app/app.constant";

@Injectable({
  providedIn: "root",
})
export class ManufacturerService {
  reloadEmitter = new EventEmitter();
  constructor(private http: HttpClient) {}

  getAllManufacturers() {
    return this.http.get(SERVICE_API_URL + "/manufacturers", {
      observe: "response",
    });
  }

  saveManufacturer(manufacturer) {
    return this.http.post(SERVICE_API_URL + "/manufacturers", manufacturer, {
      observe: "response",
    });
  }
}
