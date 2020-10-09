import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatPaginator } from '@angular/material';
import { Router } from '@angular/router';
import { PrintService } from 'src/app/Service/print.service';
import { AuthenticationService } from 'src/app/Service/authentication.service';
import { InvoiceService } from 'src/app/Service/invoice.service';



export class PrintList {
  
    clientName: string;
    position: number;
    clientType: string;
    gstType:string;
    particulars: number; 
    totalAmount:number;
    invoiceDate:string;
    invoiceNo:string;
    invoiceAmount:number;
    invoiceId:number; 
    invoiceType:string;
    month:string;
    year:string;
    financialYear:string;
    cgstAmount:string;
    sgstAmount:string;
    igstAmount:string;
    cgstPercentage:string;
    sgstPercentage:string;
    igstPercentage:string;
    clientAddress:string;
    modifyRemarks:string;
    deleteRemarks:string;
    particularsAmount:number;
    gstin:string;
    icon:string;
  }

@Component({
  selector: 'app-print-service',
  templateUrl: './print-service.component.html',
  styleUrls: ['./print-service.component.css']
})
export class PrintServiceComponent implements OnInit {

  role: string;
  constructor(private route:Router,private printService:PrintService ,private authentication: AuthenticationService ,private invoiceService: InvoiceService)
  {
    this.role = this.authentication.getLoggedUserRole();
  }
  printInvoice(id: number): void {
    this.route.navigate(["default/printInvoiceCopy", id]);
  } 
  displayedColumns: string[] = 
  ['position', 
  'clientName', 
  'invoiceId',
  'clientType', 
  'gstType',
  'totalAmount',
  'particulars',
  'particularsAmount',
  'invoiceDate',
  'invoiceNo',
  'invoiceAmount',
  'icon'
];
  dataSource: MatTableDataSource<PrintList>;

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  ngOnInit() {
    if (this.role === "ROLE_ADMIN") {
      this.printService.getAllPrintList().subscribe(respons => {
        this.dataSource = new MatTableDataSource(respons);
        this.dataSource.paginator = this.paginator;
      });
  
    } else if (this.role === "ROLE_USER") {
      this.invoiceService.getAllInvoiceBasedCurrentUser().subscribe((respons) => {
        this.dataSource = new MatTableDataSource(respons);
        this.dataSource.paginator = this.paginator;
      });
    }



  }

  show:boolean = true; 
}
