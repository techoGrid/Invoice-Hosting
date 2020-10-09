import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ClientService } from 'src/app/Service/client.service';
import { ToastrService } from 'ngx-toastr';
import { AppComponent } from 'src/app/app.component';
import { disablePrefixSpace } from 'src/app/custom.validation';

export class Client {
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
}

@Component({
  selector: "app-edit-client",
  templateUrl: "./edit-client.component.html",
  styleUrls: ["./edit-client.component.css"],
})
export class EditClientComponent implements OnInit {
  flag: boolean;
  // client: Client;
  uiClass: string;
  id;
  client: Client;
  constructor(
    private activate: ActivatedRoute,
    private clientService: ClientService,
    private formBilder: FormBuilder,
    private route: Router,
    private appComponent: AppComponent
    ,
    private toaster: ToastrService
  ) {
    this.activate.params.subscribe((data) => {
      this.id = data.id;
    });
  }

  clientUpdate: FormGroup;
  // submitted: boolean = true;
  submitted: boolean = true;
  panNumberAlreadyExist: boolean = false;
  gstinNumberAlreadyExist: boolean = false;

  ngOnInit() {
    this.client = new Client();
    this.clientService.getClientForUpdate(this.id).subscribe((response) => {
      this.client = response;
    });

    this.clientUpdate = this.formBilder.group({

      clientname: [null, [Validators.required, Validators.pattern(/^[a-zA-Z\s]+$/), Validators.maxLength(50),disablePrefixSpace]],
      address: [null, [Validators.required, Validators.maxLength(150),disablePrefixSpace]],
      clientCity: [null, [Validators.required, Validators.pattern(/^[a-zA-Z\s]+$/), Validators.maxLength(50),disablePrefixSpace]],
      clientState: [null, [Validators.required, Validators.pattern(/^[a-zA-Z\s]+$/), Validators.maxLength(50),disablePrefixSpace]],
      clientPinCode: [null, [Validators.required, Validators.minLength(6), Validators.pattern(/^[1-9][0-9]{5}$/),disablePrefixSpace]],
      clientMobile: [null, [Validators.required, Validators.pattern('^((\\+91-?)|0)?[0-9]{10}$'),disablePrefixSpace]],
      officePhone: [null, [Validators.required, Validators.pattern('^((\\+91-?)|0)?[0-9]{10}$'),disablePrefixSpace]],
      clientEmail1: [null, [Validators.required, Validators.pattern(/^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/),disablePrefixSpace]],
      panNo: [
        null,
        [
          Validators.required,
          Validators.maxLength(10),
          Validators.minLength(10),
          Validators.pattern(/^[A-Z]{5}\d{4}[A-Z]{1}$/),disablePrefixSpace
        ],
      ],
      gstin: [
        null,
        [
          Validators.required,
          Validators.maxLength(15),
          Validators.minLength(15),
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
             $("#createClient").prop("disabled", true);
            }
          } else {
            this.panNumberAlreadyExist = false;
            if (
              panNo !== "" &&
              panNo.match(pattren) &&
              !this.panNumberAlreadyExist
            ) {
             $("#createClient").prop("disabled", false);
            }
            if (panNo == "" && !panNo.match(pattren)) {
             $("#createClient").prop("disabled", true);
            }
          }
        });
  }

  checkGstNo(event: any) {
    // alert("Test")
    let gstin = event.target.value;
    if (gstin != "")
      this.clientService
        .checkGstinNumberAlreadyExistOrNot(gstin)
        .subscribe((response) => {
          if (response.success) {
            //(<HTMLInputElement>document.getElementById("userName")).value = '';
            $("#createClient").prop("disabled", true);
            this.gstinNumberAlreadyExist = true;
          } else {
            this.gstinNumberAlreadyExist = false;
          }
        });
  }

  cancell(): void {
    this.route.navigate(["default/updateClient"]);
  }
  updateClient() {
    this.appComponent.startSpinner("Updating Client Data..\xa0\xa0Please wait ...");
      setTimeout(() => {
        this.appComponent.stopSpinner();
      }, 3000);
      
    this.clientService
      .upDateClientRegistration(this.client, this.id)
      .subscribe((data) => {
        
        if (data.success) {
          setTimeout(() => {
            alert(data.message);
            this.appComponent.stopSpinner();
            this.route.navigate(["default/updateClient"]);
          }, 1000);


        } else {
          setTimeout(() => {
            alert(data.message);
            this.appComponent.stopSpinner();
          }, 1000);
        }

        
      });
  }
}
