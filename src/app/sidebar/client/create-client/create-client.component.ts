import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
// import { Client } from "./Client";

import { Client } from "./client";
import { ClientService } from 'src/app/Service/client.service';
import { AppComponent } from 'src/app/app.component';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { disablePrefixSpace } from 'src/app/custom.validation';

@Component({
  selector: "app-create-client",
  templateUrl: "./create-client.component.html",
  styleUrls: ["./create-client.component.css"],
})
export class CreateClientComponent implements OnInit {
  flag: boolean;
  client: Client;
  uiClass: string;

  panNumberAlreadyExist: boolean = false;
  gstinNumberAlreadyExist: boolean = false;
  constructor(
    private formBilder: FormBuilder,
    private clientService: ClientService,
    private appComponent: AppComponent,
    private toaster: ToastrService,
    private router: Router
  ) { }
  clientCreation: FormGroup;
  // submitted: boolean = true;
  submitted: boolean = true;

  // client: Client = new Client();

  ngOnInit() {
    this.clientCreation = this.formBilder.group({
      // /^[a-zA-Z\s]+$/
      clientname: [null, [Validators.required, Validators.pattern(/^(?!.*[_\s-]{2,})[a-zA-Z][a-zA-Z_\s\-]*[a-zA-Z]$/), 
      Validators.maxLength(50),disablePrefixSpace]],

      address: [null, [Validators.required, Validators.maxLength(150)]],
      clientCity: [null, [Validators.required, Validators.pattern(/^[a-zA-Z\s]+$/), Validators.maxLength(50) ,disablePrefixSpace]],
      clientState: [null, [Validators.required, Validators.pattern(/^[a-zA-Z\s]+$/), Validators.maxLength(50),disablePrefixSpace]],
      clientPinCode: [null, [Validators.required, Validators.minLength(6), Validators.pattern(/^[1-9][0-9]{5}$/),disablePrefixSpace]],
      clientMobile: [null, [Validators.required, Validators.pattern('^((\\+91-?)|0)?[0-9]{10}$'),disablePrefixSpace]],
      officePhone: [null, [Validators.required, Validators.pattern('^((\\+91-?)|0)?[0-9]{10}$'),disablePrefixSpace]],
      clientEmail1: [null, [Validators.required, Validators.pattern(/^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/),disablePrefixSpace]],
      panNo: [
        null,
        [
          Validators.required,
          Validators.pattern(/^[A-Z]{5}\d{4}[A-Z]{1}$/),disablePrefixSpace
        ],
      ],
      gstin: [
        null,
        [
          Validators.required,
          Validators.pattern(
            /^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$/
          ),
          disablePrefixSpace
        ], 
      ],

      stNo: [null,[disablePrefixSpace]],
      vatNo: [null,[disablePrefixSpace]],
      cstNo: [null,[disablePrefixSpace]],
      tanNo: [null,[disablePrefixSpace]],
    });
  }

  setClass(classNames) {
    this.uiClass = classNames;
  }

  createClient(): void {
    this.clientService.createclient(this.client).subscribe((data) => {
    });
  }



  checkpanNo(event: any) {
    let pattren = /^[A-Z]{5}\d{4}[A-Z]{1}$/;
    let panNo = event.target.value;
    if (panNo !== "")
      this.clientService
        .checkPanNumberAlreadyExistOrNot(panNo)
        .subscribe((response) => {
          if (response.success) {
            this.panNumberAlreadyExist = true;
            if (
              (panNo == "" && !panNo.match(pattren)) ||
              this.panNumberAlreadyExist
            ) {
            //  $("#createClient").prop("disabled", true);
            }
          } else {
            this.panNumberAlreadyExist = false;
            if (
              panNo !== "" &&
              panNo.match(pattren) &&
              !this.panNumberAlreadyExist
            ) {
             // $("#createClient").prop("disabled", false);
            }
            if (panNo == "" && !panNo.match(pattren)) {
            //  $("#createClient").prop("disabled", true);
            }
          }
        });
  }

  checkGstNo(event: any) {
    let gstin = event.target.value;
    if (gstin != "")
      this.clientService
        .checkGstinNumberAlreadyExistOrNot(gstin)
        .subscribe((response) => {
          if (response.success) {
            //(<HTMLInputElement>document.getElementById("userName")).value = '';
            // $("#createClient").prop("disabled", true);
            this.gstinNumberAlreadyExist = true;
          } else {
            this.gstinNumberAlreadyExist = false;
          }
        });
  }



  onSubmit() {
    this.appComponent.startSpinner("Saving Client Data..\xa0\xa0Please wait ...");
    setTimeout(() => {
      this.appComponent.stopSpinner();
    }, 3000);

    this.client = this.clientCreation.value;
    console.log(this.clientCreation);
    this.clientService.createclient(this.client).subscribe((data) => {

      if (data.success) {
        alert(data.message)
        setTimeout(() => {
        }, 3000);
        if (confirm("Do you want add more clients ?")) {
          this.backToList();
          this.clientCreation.reset();
        } else {
          this.router.navigate(["default/updateClient"]);
        }

      }
    });
  }

  backToList() {
    this.router.navigate(["default/createClient"]);
  }
}
