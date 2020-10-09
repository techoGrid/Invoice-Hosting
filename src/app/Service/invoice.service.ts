import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Invoice } from '../sidebar/sevice-invoice/create-invoice/invoice';



@Injectable({
  providedIn: "root",
})
export class InvoiceService {
  private baseURL = '/api';
  constructor(private http: HttpClient) { }


  addOneTime(invoice: Invoice): Observable<any> {
    return this.http.post<Invoice>(
      `${this.baseURL}/admin/submitServiceInvoiceCreationOneTimeDetails`,
      invoice
    );
  }

  addInvoice(invoice: Invoice, Id: number): Observable<any> {

    return this.http.post<Invoice>(
      `${this.baseURL}/admin/submitServiceInvoiceCreationRegularDetails/${Id}`, invoice);
  }
  getAllInvoice(): Observable<any> {
    return this.http.get(`${this.baseURL}/admin/viewServiceInvoice`);
  }
  deleteInvoice(invoiceId: number): Observable<any> {
    return this.http.delete(
      `${this.baseURL}/admin/deletingServiceInvoiceDetails/${invoiceId}`
    );
  }
  getInvoiceForEdit(id: number): Observable<any> {
    return this.http.get(`${this.baseURL}/admin/getInvoiceDetailsById/${id}`);
  }
  updateInvoice(invoice: Invoice, invoiceId: number, clientId: number): Observable<any> {
    
    return this.http.put<Invoice[]>(
      `${this.baseURL}/admin/updateServiceInvoiceCreationRegularDetails/${invoiceId}/${clientId}`, invoice
    );
  }
  updateOneTime(invoice: Invoice, invoiceId: number):any {
    return this.http.put<Invoice[]>(
      `${this.baseURL}/admin/updateServiceInvoiceCreationOneTimeDetails/${invoiceId}`, invoice
    );
  }
  getAllInvoiceBasedCurrentUser(): Observable<any> {
    return this.http.get(`${this.baseURL}/admin/invoiceListBasedOnCurrentUser`);
  }
}
