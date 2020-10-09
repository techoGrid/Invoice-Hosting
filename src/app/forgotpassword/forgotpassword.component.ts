import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { FormBuilder, Validators } from '@angular/forms';
import { LoginService } from '../Service/login.service';
import { AuthenticationService } from '../Service/authentication.service';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-forgotpassword',
  templateUrl: './forgotpassword.component.html',
  styleUrls: ['./forgotpassword.component.css']
})
export class ForgotpasswordComponent implements OnInit {
  
  forgotPasswordResponse: any;
  user: User = new User();
  invalidCredential: boolean = false;
  formGroup;
  constructor(  private fb: FormBuilder,
    private loginservice:LoginService ,
    private router:Router,
    private appComponent: AppComponent,
    private authenticationService:AuthenticationService) { }

  ngOnInit() {
    this.formGroup = this.fb.group({
      email: [
        "",
        [
          Validators.required,
          Validators.pattern(/^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/),
        ],
      ],
    });
  }
  
  doLogout() {
    if (this.authenticationService.logout()) {
      this.router.navigateByUrl("");
    } else {
      location.reload();
    }
  }

    forgotPassword(forgotPasswordEmailId){
      this.appComponent.startSpinner(
        "Sending New Password...."
      );
    this.loginservice.sendEmail(forgotPasswordEmailId)
    .subscribe(response=>{
      if (response.success) {
        alert("New Password Send Successfully On Your  Registered Email");
        setTimeout(() => {
          this.appComponent.stopSpinner();
        }, 1000); // 1 seconds.
        this.doLogout();
      }else{
        alert("Please Enter Valid Registered Email");
        setTimeout(() => {
          this.appComponent.stopSpinner();
        }, 0.50); // 1 seconds.
      }
    })
}
}