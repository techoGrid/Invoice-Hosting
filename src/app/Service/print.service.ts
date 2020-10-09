import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http"; 
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PrintService {
  private baseURL = '/api';
  constructor(private http: HttpClient) { }

  getAllPrintList(): Observable<any> {
    return this.http.get(`${this.baseURL}/admin/printInvoice`);
  }
  getInvoiceForPrint(invoiceId: number): Observable<any> {
    return this.http.get(`${this.baseURL}/admin/printInvoiceCopy/${invoiceId}`);
  }
 /* getPdf(invoiceId:number):any{
    return this.http.get(`${this.httpUrl}/printPdfInvoiceClientCopy/${invoiceId}`);
    
  } */
}

