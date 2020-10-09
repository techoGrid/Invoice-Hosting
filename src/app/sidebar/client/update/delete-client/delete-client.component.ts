import { Component, OnInit, ViewChild } from "@angular/core";
import { MatTableDataSource, MatPaginator } from "@angular/material";
import { Router } from "@angular/router";
import { ClientService } from "src/app/Service/client.service";
import { ToastrService } from 'ngx-toastr';

export class ClientList {
  position: number;
  clientId: number;
  clientname: string;
  address: string;
  clientCity: string;
  clientState: string;
  clientPinCode: string;
  clientMobile: string;
  officePhone: string;
  clientEmail1: string;
  stNo: string;
  vatNo: string;
  cstNo: string;
  panNo: string;
  tanNo: string;
  gstin: string;
  createdDate: string;
  icons: string;
  icon: string;
}
@Component({
  selector: "app-delete-client",
  templateUrl: "./delete-client.component.html",
  styleUrls: ["./delete-client.component.css"]
})
export class DeleteClientComponent implements OnInit {
  displayedColumns: string[] = [
    "position",
    "clientId",
    "clientname",
    "clientMobile",
    "clientEmail1",
    "createdDate",
    "panNo",
    "gstin",
    "icons",
    "icon"
  ];
  dataSource: MatTableDataSource<ClientList>;

  clientData;
  constructor(private route: Router, private clientService: ClientService, private toaster: ToastrService) { }
  editClient(id: number): void {
    this.route.navigate(["default/editClient", id]);
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  ngOnInit() {

    this.getClinetForDelete();

  }

  getClinetForDelete() {
    this.clientService.getAllClient().subscribe(respons => {
      this.dataSource = new MatTableDataSource(respons);
      this.dataSource.paginator = this.paginator;
    });
  }
  show: boolean = true;
  client: any;
  deleteClient(id: number) {
    if (confirm(`Are you sure to delete this Client ?`)) {
      this.clientService
        .deleteClient(id)
        .subscribe((response) => {
          (this.client = response)
          alert(response.message)
          this.getClinetForDelete();

        });
      this.route.navigate(["default/updateClient"]);
    }

  }
}
