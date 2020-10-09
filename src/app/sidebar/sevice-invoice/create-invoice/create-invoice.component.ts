import { Component, OnInit, ChangeDetectionStrategy } from "@angular/core";
import { FormBuilder, FormGroup, Validators, FormControl, ValidatorFn, ValidationErrors } from "@angular/forms";
import { Invoice } from "./invoice";
import { Client } from "../../client/edit-client/edit-client.component";
import { DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { InvoiceService } from 'src/app/Service/invoice.service';
import { ClientService } from 'src/app/Service/client.service';
import { ToastrService } from 'ngx-toastr';
import { AppComponent } from 'src/app/app.component';
import { Router } from '@angular/router';
import { isNullOrUndefined } from 'util';
import { ifError } from 'assert';
declare var $: any;
export class addRow {
  particulars: string;
  particularsAmount: string;

}
@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  selector: "app-create-invoice",
  templateUrl: "./create-invoice.component.html",
  styleUrls: ["./create-invoice.component.css"],
  providers: [

  ]
})
export class CreateInvoiceComponent implements OnInit {
  gstType: any;
  clientId: number;
  client: Client[];
  flag: boolean;
  invoice: Invoice = new Invoice();
  uiClass: string;
  invoiceCreation: FormGroup;
  submitted: boolean = true;
  amountArray: Array<addRow>;
  constructor(
    private fromBuil: FormBuilder,
    private invoiceService: InvoiceService,
    private clientServeice: ClientService,
    private appComponent: AppComponent,
    private toastr: ToastrService,
    private router: Router
  ) { }

  setClass(classNames) {
    this.uiClass = classNames;
  }
  /* ngOnInit Start ( Use to call getAllClientName() and Validations */
  ngOnInit(): void {
    this.addParticulars();
    // $("#invoiceDate").datepicker({
    //   changeMonth: true,
    //   changeYear: true,
    //   dateFormat: "dd/mm/yy",
    //   minDate: new Date(),
    // });
    // $('#btnPicker1').click(function () {

    //   $('#invoiceDate').datepicker('show');
    // });

    this.getAllClientName();
    this.invoiceCreation = this.fromBuil.group({

      gstType: [null, [Validators.required]],
      clientType: [null, [Validators.required]],
      clientName: [
        null,
        [Validators.required],
      ],
      gstin: [
        null,
        [
          Validators.maxLength(15),
          Validators.minLength(15),
          Validators.pattern(
            '^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$'
          ),
        ],
      ],
      // dynamicArray:[null,[Validators.required]],
      // resultArray: ['', [Validators.required]],
      // // email: [null, [Validators.required]],
      particularsAmount: [null],
      particulars: [null],
      clientAddress: [null],
      invoiceDate: [null ,[Validators.required]],
      invoiceAmount: [null],
      cgstPercentage: [null, [Validators.required, Validators.pattern(/^\$?([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?$/)]],
      cgstAmount: [null],
      sgstPercentage: [null, [Validators.required, Validators.pattern(/^\$?([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?$/)]],
      sgstAmount: [null],
      igstPercentage: [null, [Validators.required, Validators.pattern(/^\$?([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?$/)]],
      igstAmount: [null],
      totalAmount: [null],
      // invoiceType: [null, [Validators.required]]

    });
    // this.invoiceCreation.setValidators(this.customValidation());

    $(document).ready(function () {
      $("#cgstAndSgst").hide();
      $("#igst").hide();
      $('input[type="radio"]').click(function () {
        if ($(this).attr("id") == "oneTimeClient") {
          $("#clientNameDisplay").show();
          $("#clientNameDisplayDropDown").hide();
          $("#clientAddressDisplay").show();
          $("#gstinDisplay").show();
          $("#customerType").val("OneTime");
        } else {
          $("#clientNameDisplay").hide();
          $("#clientNameDisplayDropDown").show();
          $("#clientAddressDisplay").hide();
          $("#gstinDisplay").hide();
          $("#customerType").val("Regular");
        }
      });
    });
  }/* ngOnInit Ended */
  /* Use to retrive All Client  */
  addParticulars() {
    this.newDynamic = { particular: "", particularsAmount: "" };
    this.dynamicArray.push(this.newDynamic);
  }
  getAllClientName() {
    this.clientServeice.getAllClient().subscribe((data) => {
      this.client = data;
    });
  }
  get clientName() {
     return this.invoiceCreation.get("clientName");
  }
  selectClient(event: any) {
    this.clientId = event.target.value;
  }
  /* Use to Hide and Show Based on gstType  */
  dispalayInvoice(gstType) {
    let gstTypeLocal = gstType;

    if (gstTypeLocal == "Local") {
      $("#cgstAndSgst").show();
      $("#igst").hide();
      this.invoiceCreation.get('igstPercentage').setValue(0);
    } else if (gstTypeLocal == "Interstate") {
      $("#cgstAndSgst").hide();
      $("#igst").show();
      this.invoiceCreation.get('cgstPercentage').setValue(0);
      this.invoiceCreation.get('sgstPercentage').setValue(0);
    } else {
      $("#cgstAndSgst").hide();
      $("#igst").hide();
      this.invoiceCreation.get('cgstPercentage').setValue(null);
      this.invoiceCreation.get('igstPercentage').setValue(null);
      this.invoiceCreation.get('sgstPercentage').setValue(null);
    }
    this.calculation();
  }
  /* Use to Calculation */
  calculation() {
    let initialInvoiceAmount: any = $("#invoiceAmount").val();
    let invoiceAmountRoundOff: any = Math.round(initialInvoiceAmount);
    $("#invoiceAmount").val(invoiceAmountRoundOff);
    let gstType = $("#gstType").val();
    let invoiceAmount: any = $("#invoiceAmount").val();
    let cgstTaxRate: any = $("#cgstPercentage").val();
    let sgstTaxRate: any = $("#sgstPercentage").val();
    let igstTaxRate: any = $("#igstPercentage").val();
    if (gstType == "Local") {
      let cgstAmount: any = Math.round((invoiceAmount * cgstTaxRate) / 100);
      if (isNaN(cgstAmount)) {
        $("#cgstAmount").val(0);
      } else {
        $("#cgstAmount").val(cgstAmount);
      }
      let sgstAmount: any = Math.round((invoiceAmount * sgstTaxRate) / 100);
      if (isNaN(sgstAmount)) {
        $("#sgstAmount").val(0);
      } else {
        $("#sgstAmount").val(sgstAmount);
      }
      $("#igstAmount").val(0);
      $("#igstPercentage").val(0);
      let totalAmount: any = Math.round(
        +invoiceAmount + +cgstAmount + +sgstAmount
      );
      if (isNaN(totalAmount)) {
        $("#totalAmount").val(0);
      } else {
        $("#totalAmount").val(totalAmount);
      }
    } else if (gstType == "Interstate") {
      let igstAmount = Math.round((invoiceAmount * igstTaxRate) / 100);
      if (isNaN(igstAmount)) {
        $("#igstAmount").val(0);
      } else {
        $("#igstAmount").val(igstAmount);
      }
      $("#cgstAmount").val(0);
      $("#cgstPercentage").val(0);
      $("#sgstAmount").val(0);
      $("#sgstPercentage").val(0);
      let totalAmount: any = Math.round(+invoiceAmount + +igstAmount);
      if (isNaN(totalAmount)) {
        $("#totalAmount").val(0);
      } else {
        $("#totalAmount").val(totalAmount);
      }
    }
  }/* End Calculator */

  /* Staring of particularsRowAmtWrtParticularsRowName() */
  particularsRowAmtWrtParticularsRowName() {
    let status = true;
    $("#particularsTable")
      .find('input[type="checkbox"]')
      .each(function () {
        let trIndexs = $(this).closest("tr").index();
        let lastIdRowValue = trIndexs;
        let allParticularsNames = new Array();
        let allParticularsAmts = new Array();
        let particularsRowName = document.getElementsByName(
          "particularsRowName"
        );
        let particularsRowAmount = document.getElementsByName(
          "particularsRowAmount"
        );
        for (let i = 0; i <= lastIdRowValue; i++) {
          let id2 = particularsRowName[i].getAttribute("id");
          let id3 = particularsRowAmount[i].getAttribute("id");
          let value2: any = $("#" + id2).val();
          let value3: any = $("#" + id3).val();

          if (
            value2 == ""
          ) {
            alert("Please Enter Particulars");
            document.getElementById(id2).focus();
            status = false;
            break;
          } else if (value3 == "") {
            alert("Please Enter Particulars Amount");
            document.getElementById(id3).focus();
            status = false;
            break;
          } else {

            if (value2.indexOf(",") !== -1) {
              alert("Sorry, This Field Can't Accepts Comma.");
              document.getElementById(id2).focus();
              status = false;
              break;
            } else {
              allParticularsNames[i] = value2;
            }

            let numberValidation = /^[0-9.]+$/;
            if (value3.match(numberValidation)) {
              allParticularsAmts[i] = Math.round(value3);
            } else {
              alert("Particulars Amount Should be an Integer Number");
              document.getElementById(id3).focus();
              status = false;
              break;
            }
          }
        }

        if (status == false) {
          return false;
        }
        $("#particulars").val(allParticularsNames);
        $("#particularsAmount").val(allParticularsAmts);
      });

    return status;
  }
  // /* Ended particularsRowAmtWrtParticularsRowName() */
  /* Use to save the Invoice data into database */


  particularWrtAmt(): boolean {
    let particular: any = [];
    let amount: any = [];
    this.dynamicArray.forEach((data, i) => {
      if (data.particulars == '') {
        alert('Fill the data')
        return false;
      } else {
        particular[i] = data.particulars;
      }

      amount[i] = data.particularsAmount;
    });

    this.invoiceCreation.patchValue({ particulars: particular.join() })
    this.invoiceCreation.patchValue({ particularsAmount: amount.join() })
    return true;
  }



  CreateInvoice() {
    let customerType = $("#customerType").val();
    let invoiceDate = $("#invoiceDate").val();
    let cgstAmount = $("#cgstAmount").val();
    let sgstAmount = $("#sgstAmount").val();
    let invoiceAmount = $("#invoiceAmount").val();
    let igstAmount = $("#igstAmount").val();
    let totalAmount = $("#totalAmount").val();


    this.invoiceCreation.patchValue({ invoiceDate: invoiceDate })
    this.invoiceCreation.patchValue({ cgstAmount: cgstAmount })
    this.invoiceCreation.patchValue({ sgstAmount: sgstAmount })
    this.invoiceCreation.patchValue({ igstAmount: igstAmount })
    this.invoiceCreation.patchValue({ totalAmount: totalAmount })
    this.invoiceCreation.patchValue({ invoiceAmount: invoiceAmount })



    if (this.invoiceCreation.status && this.particularWrtAmt()) {
      this.invoice = this.invoiceCreation.value;
      this.appComponent.startSpinner(" Saving Invoice Data..\xa0\xa0Please wait ...");
      setTimeout(() => {
        this.appComponent.stopSpinner();
      }, 3000);
      if (customerType === "OneTime") {

        this.invoiceService.addOneTime(this.invoice)
          .subscribe((data) => {

            if (data.success) {
              alert(data.message)

              setTimeout(() => {
              }, 100);
              if (confirm("Do you want add more invoice ?")) {
              
                this.router.navigate(["default/deleteInvoice"]);
                setTimeout(() => {
                  this.router.navigate(["default/createinvoice"]);
                }, 100);
                this.invoiceCreation.reset();
              } else {
                this.router.navigate(["default/deleteInvoice"]);
              }
            }
          });
      } else {
        this.invoiceService.addInvoice(this.invoice, this.clientId)
          .subscribe((data) => {

            if (data.success) {
              alert(data.message)

              setTimeout(() => {
              }, 100);
              if (confirm("Do you want add more invoice ?")) {
                this.router.navigate(["default/deleteInvoice"]);
               
                setTimeout(() => {
                  this.router.navigate(["default/createinvoice"]);
                }, 100);
                this.invoiceCreation.reset();
              } else {
                this.router.navigate(["default/deleteInvoice"]);
              }
            }

            else {

            }


          });
      }
    } else {
      alert("test")
    }

  }


  backToList() {
    this.router.navigate(["default/createinvoice"]);
  }
  /* Use to Add Input Fields in Particular Table */
  dynamicArray: Array<addRow> = [];
  newDynamic: any = {};
  addRow() {
    this.newDynamic = { particulars: "", particularsAmount: "" };
    this.dynamicArray.push(this.newDynamic);
    this.validateInvoiceParticularDetails(-1);
    this.dynamicArray.forEach((data) => {
      if (data.particulars == "" && data.particularsAmount == "") {

        this.invoiceCreation.invalid == true;
      }
    });
    this.toastr.success('New Row Added Successfully.');
    return true;
  }
  /* Use to Remove Input Fields in Particular Table */
  deleteRow(index) {
    this.calculation();
    if (this.dynamicArray.length == 1) {
      this.toastr.error("Can't delete the row when there is only one row", 'Warning');
      return false;
    } else {


      this.dynamicArray.splice(index, 1);

      this.toastr.warning('Row Deleted Successfully.');
      return this.calculateSumOfInvoiceAmounts();
    }

  }

  /* Start calculateSumOfInvoiceAmount() */

  calculateSumOfInvoiceAmounts() {
    let sum: number = 0;
    this.dynamicArray.forEach(element => {
      sum += Number(element.particularsAmount);
      for (let i = 0; i < this.dynamicArray.length; i++) {
        if (isNaN(sum)) {
          $("#invoiceAmount").val(0);
        } else {
          $("#invoiceAmount").val(sum);
        }
      }

    });
    this.calculation();
  }

  particularsRowName(event: any) {
    let particularsRowName = event.target.value;
    var letters = /^[A-Za-z ]+$/;
    if (particularsRowName == '') {
      alert("Please Enter the particularsRow Name");
      return $("#particularsRowName").focus();
    } else if (particularsRowName.match(letters)) {
      return true;
    }
    else {
      alert("particularsRow Name Should Contain Alphabets Only.");
      return $("#particularsRowName").focus();
    }
  }

  particularsNameRow(particularsValue: string, i: number) {
    if (particularsValue != "") {
      if (particularsValue.match(/^[a-zA-Z \s]+$/)) {
        document.getElementById("perticularMsg" + i).innerHTML = "";
        return true;
      } else {
        document.getElementById("perticularMsg" + i).innerHTML = "Please enter only alphabets characters.";
        return false;
      }
    } else {
      if (!isNullOrUndefined(document.getElementById("perticularMsg" + i))) {
        document.getElementById("perticularMsg" + i).innerHTML = "Please enter this field";
      }
      return false;
    }
  }

  particularsAmountNameRow(particularsAmountValue: string, i: number) {
    if (particularsAmountValue != "") {
      if (particularsAmountValue.match(/^\$?([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?$/)) {
        document.getElementById("perticularAmountMsg" + i).innerHTML = "";
        return true;
      } else {
        document.getElementById("perticularAmountMsg" + i).innerHTML = "Please enter only numbers .";
        return false;
      }
    } else {
      if (!isNullOrUndefined(document.getElementById("perticularAmountMsg" + i))) {
        document.getElementById("perticularAmountMsg" + i).innerHTML = "Please enter this field";
      }
      return false;
    }
  }


  invoiceParticularRowDetailsFlag: boolean = false;
  validateInvoiceParticularDetails(i: number): boolean {
    this.invoiceParticularRowDetailsFlag = false;

    if (i > -1) {
      this.particularsNameRow(this.dynamicArray[i].particulars, i);
      this.particularsAmountNameRow(this.dynamicArray[i].particularsAmount, i);
    }

    this.dynamicArray.every((object, index) => {
      let perticularNameRowFlag = this.particularsNameRow(object.particulars, index);
      let particularsAmountRowFlag = this.particularsAmountNameRow(object.particularsAmount, index);
      if (perticularNameRowFlag && particularsAmountRowFlag) {
        this.invoiceParticularRowDetailsFlag = true;
        return true;
      } else {
        this.invoiceParticularRowDetailsFlag = false;
        return false;
      }
    });
    return this.invoiceParticularRowDetailsFlag;
  }

  reloadTable() {
    this.invoiceCreation.reset();

    var particularsName = document.getElementsByName("particularsRowName");
    var particularsAmt = document.getElementsByName("particularsRowAmount");
    setTimeout(() => {
      console.log(particularsName)
      console.log(particularsAmt)
      this.dynamicArray = [];
      this.addParticulars();
    }, 1000);
  }
}
