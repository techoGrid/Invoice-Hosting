import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
// import { User } from "src/app/sidebar/user/edit-user/edit-user.component";
import { FormBuilder, Validators } from "@angular/forms";
import { User } from "src/app/user";
import { AuthenticationService } from 'src/app/Service/authentication.service';
import { AppComponent } from 'src/app/app.component';


@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"],
})
export class LoginComponent implements OnInit {
  showModal: boolean;
  user: User = new User();
  invalidCredential: boolean = false;
  formGroup;
  formGroup1;
  forgotPasswordResponse;
  loading: boolean;
  constructor(
    private router: Router,
    private fb: FormBuilder,
    private appComponent: AppComponent,
    private authentication: AuthenticationService
  ) { }
  show() {
    this.showModal = true; // Show-Hide Modal Check
  }
  //Bootstrap Modal Close event
  hide() {
    this.showModal = false;
  }
  username: string = '';
  password: string = '';

  isLoggedIn = false;

  error: string;
  usernameError: string;
  passwordError: string;

  ngOnInit() {
    this.isLoggedIn = this.authentication.isUserLoggedIn();
    if (this.isLoggedIn) {
      this.router.navigate(["default/home"]);
    }
    this.formGroup = this.fb.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]],
    });

    this.formGroup1 = this.fb.group({
      emailId: [
        "",
        [
          Validators.required,
          Validators.pattern(/^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/),
        ],
      ],
    });
  }
  validateUsername() {
    if (this.username == '' || this.username == null) {
      this.usernameError = 'Username Required *';
    } else {
      this.usernameError = '';
      this.error = '';
    }
  }

  validatePassword() {
    if (this.password == '' || this.password == null) {
      this.passwordError = 'Password Required *';
    } else {
      this.passwordError = '';
      this.error = '';
    }
  }
  get emailId() {
    return this.formGroup1.get("emailId");
  }

  logIn() {
    this.appComponent.startSpinner(
      "Authenticating..."
    );
    if (
      this.username !== "" &&
      this.username !== null &&
      this.password !== "" &&
      this.password !== null
    ) {

      this.authentication
        .authenticate(this.username, this.password)
        .subscribe(
          (resp: boolean) => {
            if (resp) {
              this.router.navigate(["default/home"]);
              setTimeout(() => {
                this.appComponent.stopSpinner();
              }, 1000); // 1 seconds.
            } else {
              this.error = "Bad Credentials.";
              setTimeout(() => {
                this.appComponent.stopSpinner();
              }, 1000); // 1 seconds.
            }
          },
          (error) => {
            console.log(error);
            this.error = "Invalid Credentials.";
            setTimeout(() => {
              this.appComponent.stopSpinner();
            }, 1000); // 1 seconds.
          }
        );
    } else {
      this.validateUsername();
      this.validatePassword();
    }
  }
}
