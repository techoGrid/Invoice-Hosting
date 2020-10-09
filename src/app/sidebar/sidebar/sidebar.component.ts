import { Component, OnInit, ViewChild, ElementRef, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/Service/authentication.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  role:string;
  constructor(public router: Router ,private authentication:AuthenticationService) {
    this.role = this.authentication.getLoggedUserRole();
   }
  currentUserName: string = "Vikash Kumar";
  userType:string = "Admin";
  status:string = "Online";
  imgUrl:string = "assets/images/cp.jpg";
  ngOnInit() {
  }
 
redirectToAbout() {
    this.router.navigateByUrl('dashboard/about');
}
logMeOut(){
    this.router.navigateByUrl('login');
}
}
