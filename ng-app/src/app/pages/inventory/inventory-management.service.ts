import { HttpClient } from "@angular/common/http";
import { EventEmitter, Injectable } from "@angular/core";
import { SERVICE_API_URL } from "src/app/app.constant";

@Injectable({
  providedIn: "root",
})
export class InventoryManagementService {
  reloadEmitter = new EventEmitter();
  constructor(private http: HttpClient) {}

  getAllInventories(pageable: { page: number; size: number; sort: string }) {
    return this.http.get(
      SERVICE_API_URL +
        "/inventories?page=" +
        pageable.page +
        "&size=" +
        pageable.size +
        "&sort=" +
        pageable.sort,
      { observe: "response" }
    );
  }

  saveInventory(inventory) {
    return this.http.post(SERVICE_API_URL + "/inventories", inventory, {
      observe: "response",
    });
  }
}
