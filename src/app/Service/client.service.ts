import { Injectable, APP_ID } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { ClientList } from '../sidebar/client/update/delete-client/delete-client.component';
import { Client } from '../sidebar/client/create-client/client';



@Injectable({
  providedIn: "root",
})
export class ClientService {

  private baseURL = '/api';

  client: ClientList;
  constructor(private http: HttpClient) { }
  createclient(client: Client): Observable<any> {
    return this.http.post<Client>(`${this.baseURL}/admin/createClientRegistration`, client);
  }

  getAllClient(): Observable<any> {
    return this.http.get(`${this.baseURL}/admin/viewclient`);
  }

  deleteClient(id: number): Observable<any> {
    return this.http.delete(`${this.baseURL}/admin/deleteClient/${id}`);
  }

  getClientForUpdate(id: number): Observable<any> {
    return this.http.get(`${this.baseURL}/admin/updateClient/${id}`);
  }

  upDateClientRegistration(client: Client, id: number): Observable<any> {
    return this.http.put<Client[]>(`${this.baseURL}/admin/updateClientRegistration/${id}`, client);
  }

  checkPanNumberAlreadyExistOrNot(panNO: string): Observable<any> {
    return this.http.get(`${this.baseURL}/admin/checkPanNumberAlreadyExistOrNot/${panNO}`);
  }

  checkGstinNumberAlreadyExistOrNot(gstIn: string): Observable<any> {
    return this.http.get(`${this.baseURL}/admin/checkGstinNumberAlreadyExistOrNot/${gstIn}`);
  }
}
