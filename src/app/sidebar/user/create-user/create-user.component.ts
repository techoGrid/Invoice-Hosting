import { Component, OnInit } from "@angular/core";
import { FormBuilder, Validators, FormGroup, NgForm } from "@angular/forms";
import { User } from "src/app/user";
import { UserService } from 'src/app/Service/user.service';
import { AppComponent } from 'src/app/app.component';
import { THIS_EXPR, NULL_EXPR } from '@angular/compiler/src/output/output_ast';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { disablePrefixSpace } from 'src/app/custom.validation';

@Component({
  selector: "app-create-user",
  templateUrl: "./create-user.component.html",
  styleUrls: ["./create-user.component.css"],
})
export class CreateUserComponent implements OnInit {
  flag: boolean;
  user: User = new User();
  uiClass: string;
  designation;
  msg;
  localStorageUserId;
  userDetail;
  userTypeId;
  designationId: number;
  desig: any;
  loading: boolean;
  userNameAlreadyExist: boolean = false;
  emailIdAlreadyExist: boolean = false;

  alert: boolean = false;

  constructor(private formBilder: FormBuilder, private router: Router, private service: UserService, private appComponent: AppComponent, private toastr: ToastrService) { }
  userCreation: FormGroup;
  submitted: boolean = true;
  ngOnInit() {
    this.getAllDesignation();
    this.localStorageUserId = localStorage.getItem("userId");
    this.getUserDetails(this.localStorageUserId);

    this.userCreation = this.formBilder.group({
      username: [
        null,
        [
          Validators.required,
          Validators.pattern(/^[a-zA-Z\s]+$/),
          Validators.maxLength(50), disablePrefixSpace
        ],
      ],
      userType: [null, [Validators.required]],
      displayName: [
        null,
        [
          Validators.required,
          Validators.pattern(/^[a-zA-Z\s]+$/),
          Validators.maxLength(50),
          disablePrefixSpace
        ],
      ],
      emailId: [
        null,
        [
          Validators.required,
          Validators.pattern(/^([a-zA-Z0-9._-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z]{2,4})+$/), disablePrefixSpace
        ],
      ],
      mobile: [null, [Validators.required, Validators.minLength(10), Validators.pattern('^((\\+91-?)|0)?[0-9]{10}$'), disablePrefixSpace]],
    });
  }

  getAllDesignation() {
    this.service.togetDesignationList().subscribe((data) => {
      this.desig = data;
    });
  }
  selectDesignation(event: any) {
    this.designationId = event.target.value;
  }

  setClass(classNames) {
    this.uiClass = classNames;
  }

  get username() {
    return this.userCreation.get("username");
  }

  get displayName() {
    return this.userCreation.get("displayName");
  }

  get userType() {
    return this.userCreation.get("userType");
  }

  get emailId() {
    return this.userCreation.get("emailId");
  }

  getUserDetails(localId: number) {
    if (this.designation == "Admin") {
      this.service.getUserForEdit(localId).subscribe((response) => {
        this.userDetail = response.object.user.companyId;
      });
    }
  }

  checkEmailId(event: any) {
    let pattren = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/;
    let email = event.target.value;
    if (email !== "")
      this.service
        .toCheckEmailIdAlreadyExistOrNot(email)
        .subscribe((response) => {
          if (response.success) {
            this.emailIdAlreadyExist = true;
            if (
              (email === "" && !email.match(pattren)) ||
              this.emailIdAlreadyExist
            ) {
              $("#creteUser").prop("disabled", true);
            }
          } else {
            this.emailIdAlreadyExist = false;
            if (
              email !== "" &&
              email.match(pattren) &&
              !this.emailIdAlreadyExist
            ) {
              4;
              $("#creteUser").prop("disabled", false);
            }
            if (email === "" && !email.match(pattren)) {
              $("#creteUser").prop("disabled", true);
            }
          }
        });
  }

  checkUserName(event: any) {
    let username = event.target.value;
    if (username !== "")
      this.service
        .toCheckUserNameAlreadyExistOrNot(username)
        .subscribe((response) => {
          if (response.success) {
            $("#creteUser").prop("disabled", true);
            this.userNameAlreadyExist = true;
          } else {
            this.userNameAlreadyExist = false;
          }
        });
  }

  selectUserType(event: any) {
    this.userTypeId = event.target.value;
  }

  onSubmit() {
    this.submitted = true;
    if (this.userCreation.valid) {
      this.userCreation.reset();

    }
  }
  createUser(uName: string, dName: string, email: string, uMobile: string) {
    this.appComponent.startSpinner("Saving User Data..\xa0\xa0Please wait ...");
    setTimeout(() => {
      this.appComponent.stopSpinner();

    }, 500);

    this.loading = true;
    this.user.username = uName;
    this.user.displayName = dName;
    this.user.email = email;
    this.user.mobile = uMobile;
    if (this.designation == "Admin") {
      let userTypeId = (<HTMLInputElement>document.getElementById("userType")).value;
      this.service.addUser(this.user, this.designationId).subscribe((response) => {
        this.loading = false;
        if (response.success) {
          alert(response.message)
          setTimeout(() => {
          }, 3000);
          if (confirm("Do you want add more Users ?")) {
            // this.backToList();
            this.userCreation.reset();
          } else {
            this.router.navigate(["default/update"]);
          }

        }
      });
    } else {
      let userTypeId = (<HTMLInputElement>document.getElementById("userType"))
        .value;
      this.service
        .addUser(this.user, this.designationId)
        .subscribe((response) => {
          this.loading = false;
          if (response.success) {
            alert(response.message)
            setTimeout(() => {
            }, 3000);
            if (confirm("Do you want add more Users ?")) {
              // this.backToList();
              this.userCreation.reset();
            } else {
              this.router.navigate(["default/update"]);
            }
            // this.msg = response.message;

            // (<HTMLInputElement>document.getElementById("username")).value = "";
            // (<HTMLInputElement>document.getElementById("displayName")).value =
            //   "";
            // (<HTMLInputElement>document.getElementById("email")).value = "";
          } else {
            this.msg = response.message;
            (<HTMLInputElement>document.getElementById("username")).value = "";
            (<HTMLInputElement>document.getElementById("displayName")).value =
              "";
            (<HTMLInputElement>document.getElementById("email")).value = "";
          }
        });
    }
    // this.alert=false;
    this.onSubmit();
    // setTimeout(() => {
    //   this.alert=false;
    // }, 3000);
  }

  closeAlert() {
    this.alert = false;
  }
  backToList() {
    this.router.navigate(["default/createUser"]);
  }
}
