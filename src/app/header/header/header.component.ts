import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { User } from 'src/app/user';
import { UserService } from 'src/app/Service/user.service';
import { LoginService } from 'src/app/Service/login.service';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/Service/authentication.service';
import { AppComponent } from 'src/app/app.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  showModal: boolean;
  registerForm: FormGroup;
  submitted = false;
  USER_NAME: any;
  USER_ROLE: any;
  date:any;
  today: number = Date.now();
  timer;
  constructor(private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router,
    private appComponent: AppComponent,
    private authenticationService: AuthenticationService,
    private loginService: LoginService) {
      const {USER_NAME,USER_ROLE} = this.authenticationService.getUserDetails();
    this.USER_NAME = USER_NAME
    this.USER_ROLE = USER_ROLE

  }
  isAdminRole() {
    if (this.authenticationService.getLoggedUserRole() === "ROLE_ADMIN")
      return true;
    else
      return false;
  }

  doLogout() {
    if (this.authenticationService.logout()) {
      this.router.navigateByUrl("");
    } else {
      location.reload();
    }
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
    this.timer = setInterval(() => {
      this.today = Date.now();
      }, 1000);
  //  this.getUserDetails();
  }
  ngOnDestroy(){
    clearInterval(this.timer);
  }
  // convenience getter for easy access to form fields
  get f() { return this.registerForm.controls; }
  onSubmit() {
    this.submitted = true;
    // stop here if form is invalid
    if (this.registerForm.invalid) {
      return;
    }
    if (this.submitted) {
      this.showModal = false;
    }
  }
  
  // user: User;
  // getUserDetails() {
  //   this.user = new User();
  //   this.userService.getUserForDashBoard().subscribe((data) => {
  //     this.user = data;
  //   });
  // }
 
  response;
  valid(newPwd: string, cPwd: string) {
    this.appComponent.startSpinner("Processing Data....")
    this.loginService.changePassword(newPwd, cPwd).
      subscribe((data) => {
        this.response = data;
        if(this.response.success){
          setTimeout(() => {
            this.appComponent.stopSpinner();
          }, 1000); // 1 seconds.
          alert("Password Updated Successfully")
          // this.router.navigate([""])
          // this.router.navigateByUrl("");
          this.doLogout();
        }
      })
  }
  show() {
    this.showModal = true; // Show-Hide Modal Check

  }
  //Bootstrap Modal Close event
  hide() {
    this.showModal = false;
  }
}
