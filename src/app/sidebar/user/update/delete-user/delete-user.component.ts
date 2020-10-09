import { Component, OnInit, ViewChild } from "@angular/core";
import { MatTableDataSource } from "@angular/material/table";
import { MatPaginator } from "@angular/material";
import { Router } from "@angular/router";
import { UserService } from 'src/app/Service/user.service';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from 'src/app/Service/authentication.service';


export interface UserList {
  username: string;
  userId: number;
  position: number;
  createdDate: string;
  // designation: string;
  displayName: string;
  email: string;
  mobile: string;
  icons: string;
  icon: string;
}

@Component({
  selector: "app-delete-user",
  templateUrl: "./delete-user.component.html",
  styleUrls: ["./delete-user.component.css"],
})
export class DeleteUserComponent implements OnInit {
  role: string;
  constructor(private route: Router, private userService: UserService, private toaster: ToastrService, private authentication:AuthenticationService) { }
  editUser(id: number): void {
    this.role = this.authentication.getLoggedUserRole();
    this.route.navigate(["default/editUser", id]);
  }
  displayedColumns: string[] = [
    "position",
    "userId",
    "username",
    "createdDate",
    "displayName",
    "email",
    "mobile",
    "icons",
    "icon",
  ];
  dataSource: MatTableDataSource<UserList>;
  ngOnInit() {
    this.getUseDataForDelete();
  }

  getUseDataForDelete() {
    this.userService.getUser().subscribe((respons) => {

      this.dataSource = new MatTableDataSource(respons);
      this.dataSource.paginator = this.paginator;
    });
  }
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  show: boolean = true;
  user: any;
  deleteUser(id: number) {
    if (confirm(`Are you sure to delete this user ?`)) {
      this.userService
      .deleteUser(id)
      .subscribe((response) => {
        (this.user = response)
        if(response.success)
        {
          alert(response.message)
          this.getUseDataForDelete();
        }
        
      });
    this.route.navigate(["default/update"]);   
    } 
  }
}
