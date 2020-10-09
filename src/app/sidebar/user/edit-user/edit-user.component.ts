import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { UserService } from 'src/app/Service/user.service';
import { AppComponent } from 'src/app/app.component';
import { ToastrService } from 'ngx-toastr';
import { disablePrefixSpace } from 'src/app/custom.validation';

export class User {
  userId: number;
  username: string;
  userType: string;
  displayName: string;
  designation: number;
  email: string;
  mobile: string;
  createdDate: any;
}

@Component({
  selector: "app-edit-user",
  templateUrl: "./edit-user.component.html",
  styleUrls: ["./edit-user.component.css"],
})
export class EditUserComponent implements OnInit {
  id;
  user: User;
  designationId: number;
  desig: any;
  alert: boolean = false;
  constructor(
    private activate: ActivatedRoute,
    private userService: UserService,
    private formBilder: FormBuilder,
    private route: Router,
    private appComponent: AppComponent,
    private toster: ToastrService
  ) {
    this.activate.params.subscribe((data) => {
      this.id = data.id;
    });
  }
  userUpdate: FormGroup;
  ngOnInit() {
    this.getAllDesignation();
    this.user = new User();
    this.userService.getUserForEdit(this.id).subscribe((response) => {
      this.user = response;
      this.designationId = response.designation.designationId;
    });

    this.userUpdate = this.formBilder.group({
      username: [null, [Validators.required, Validators.maxLength(50), disablePrefixSpace]],
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
      email: [
        null,
        [
          Validators.required,
          Validators.pattern(/^([a-zA-Z0-9._-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z]{2,4})+$/),
          disablePrefixSpace
        ],
      ],
      mobile: [null, [Validators.required, Validators.minLength(10), Validators.pattern('^((\\+91-?)|0)?[0-9]{10}$'),disablePrefixSpace]],

    });
  }

  getAllDesignation() {
    this.userService.togetDesignationList().subscribe((data) => {
      this.desig = data;
    });
  }
  selectDesignation(event: any) {
    this.designationId = event.target.value;
  }
  get emailId() {
    return this.userUpdate.get("emailId");
  }


  emailIdAlreadyExist: boolean = false;
  checkEmailId(event: any) {
    let pattren = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/;
    let email = event.target.value;
    if (email !== "")
      this.userService
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


  updateClient() {
    this.appComponent.startSpinner("Updating User Data..\xa0\xa0Please wait ...");
    setTimeout(() => {
      this.appComponent.stopSpinner();
    }, 1000);
    this.userService
      .upDateUserRegistration(this.user, this.id, this.designationId)
      .subscribe((data) => {
        if (data.success) {
          setTimeout(() => {
            alert(data.message);
            this.appComponent.stopSpinner();
            this.route.navigate(["default/update"]);
          }, 1000);


        } else {
          setTimeout(() => {
            alert(data.message);
            this.appComponent.stopSpinner();
          }, 1000);
        }

      });
  }

  closeAlert() {
    this.alert = false;
  }

  cancell(): void {
    this.route.navigate(["default/update"]);
  }
}
