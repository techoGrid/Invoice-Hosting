import { Component, OnInit } from "@angular/core";
import { Validators, FormBuilder, FormGroup } from "@angular/forms";
import { TableData } from "../create-invoice/tabledata";
import { InvoiceService } from 'src/app/Service/invoice.service';
import { ClientService } from 'src/app/Service/client.service';
import { Invoice } from "../create-invoice/invoice";
import { Client } from "../../client/create-client/client";
import { ActivatedRoute, Router } from "@angular/router";
import { ToastrService } from 'ngx-toastr';
import { addRow } from '../create-invoice/create-invoice.component';
import { AppComponent } from 'src/app/app.component';
import { isNullOrUndefined } from 'util';
declare var $: any;
@Component({
  selector: "app-edit-invoice",
  templateUrl: "./edit-invoice.component.html",
  styleUrls: ["./edit-invoice.component.css"],
  providers: [
  ]
})
export class EditInvoiceComponent implements OnInit {
  gstType: any;
  tableData = new TableData();
  clientId: number;
  dataArray = [];
  client: Client[];
  id;
  id1;
  flag: boolean;
  invoice: Invoice = new Invoice();
  //invoice: Invoice;
  uiClass: string;
   clientname:any;
  particularRow: any = new Array();
  invoiceCreation: FormGroup;
  submitted: boolean = true;
  constructor(
    private fromBuil: FormBuilder,
    private invoiceService: InvoiceService,
    private clientServeice: ClientService,
    private activated: ActivatedRoute,
    private route: Router,
    private appComponent: AppComponent,
    private toastr: ToastrService
  ) {
    this.activated.params.subscribe((data) => {
      this.id = data.id;
      this.id1 = data.id1;
    });
  }

  addForm() {
    this.tableData = new TableData();
    this.dataArray.push(this.tableData);
  }
  removeForm(index) {
    this.dataArray.slice(index);
  }
  setClass(classNames) {
    this.uiClass = classNames;
  }
  clientName1
  date;
  aa: any;
  bb: any;

  clientType:any;
  ngOnInit() {
    // $("#invoiceDate").datepicker({
    //   changeMonth: true,
    //   changeYear: true,
    //   dateFormat: "dd/mm/yy",
    //   minDate: new Date(),
    // });
    // $('#btnPicker1').click(function () {

    //   $('#invoiceDate').datepicker('show');
    // });

    this.invoice = new Invoice();
    this.invoiceService.getInvoiceForEdit(this.id).subscribe((response) => {
      this.invoice = response;
      this.clientType=this.invoice.clientType;
      console.log("Clinet Type ",this.clientType);
      
      this.getParticularWrtMnt(this.invoice);
      this.dispalayInvoice(response.gstType);
      this.displayCustomerType(response.clientType)
      if (response.clientType == "Regular") {
        this.clientId = response.client.clientId;
      }
      else {
        this.clientname = this.invoice.clientName;
      }
    });
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
            /^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$/
          ),
        ],
      ],
      particularsAmount: [null],
      particulars: [null],
      clientAddress: [null],
      invoiceDate: [null],
      invoiceAmount: [null],
      cgstPercentage: [null, [Validators.required, Validators.pattern(/^\$?([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?$/)]],
      cgstAmount: [null],
      sgstPercentage: [null, [Validators.required, Validators.pattern(/^\$?([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?$/)]],
      sgstAmount: [null],
      igstPercentage: [null, [Validators.required, Validators.pattern(/^\$?([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?$/)]],

      igstAmount: [null],
      totalAmount: [null],
    });
    $(document).ready(function () {
      // $("#cgstAndSgst").hide();
      // $("#igst").hide();
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
  }


  getParticularWrtMnt(invoice) {
    let particulars: any = [];
    let particularsAmount: any = [];

    particulars = invoice.particulars.split(',');
    particularsAmount = invoice.particularsAmount.split(',');
    if (particulars.length == particularsAmount.length) {
      for (let i = 0; i < particularsAmount.length; i++) {
        this.newDynamic = { particulars: particulars[i], particularsAmount: particularsAmount[i] };
        this.dynamicArray.push(this.newDynamic);
      }
    } else {
      alert("something went wrong")
    }

  }

  displayCustomerType(clientType) {
    let custType = clientType;
    if (custType == 'OneTime') {
      $("#clientNameDisplayDropDown").hide();
      $("#clientNameDisplay").show();
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
  }
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
  }

  UpdateInvoice() {
    this.appComponent.startSpinner(" Updating Invoice Data..\xa0\xa0Please wait ...");
    setTimeout(() => {
      this.appComponent.stopSpinner();
    }, 3000);
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
      if (customerType == "OneTime") {
        this.invoiceService
          .updateOneTime(this.invoice, this.id,)
          .subscribe((data) => {
            if (data.success) {
              setTimeout(() => {
                alert(data.message);
                this.appComponent.stopSpinner();
                this.route.navigate(["default/deleteInvoice"]);
              }, 1000);
            }
            else {
              setTimeout(() => {
                alert(data.message);
                this.appComponent.stopSpinner();
              }, 1000);
            }

            // this.invoiceCreation.reset();
          });
      } else {

        this.invoiceService
          .updateInvoice(this.invoice, this.id, this.clientId)
          .subscribe((data) => {
            if (data.success) {
              setTimeout(() => {
                alert(data.message);
                this.appComponent.stopSpinner();
                this.route.navigate(["default/deleteInvoice"]);
              }, 1000);
            }
            else {
              setTimeout(() => {
                alert(data.message);
                this.appComponent.stopSpinner();
              }, 1000);
            }

          });
      }
    }
  }

  cancell(): void {
    this.route.navigate(["default/deleteInvoice"]);
  }

  particularWrtAmt(): boolean {
    let particular: any = [];
    let amount: any = [];
    this.dynamicArray.forEach((data, i) => {
      particular[i] = data.particulars;
      amount[i] = data.particularsAmount;
    });
    console.log(particular.join())
    console.log(amount.join())
    this.invoiceCreation.patchValue({ particulars: particular.join() })
    this.invoiceCreation.patchValue({ particularsAmount: amount.join() })
    return true;
  }

  dynamicArray: Array<addRow> = [];
  newDynamic: any = {};
  addRow() {
    this.newDynamic = { particulars: "", particularsAmount: "" };
    this.dynamicArray.push(this.newDynamic);

    this.toastr.success('New Row Added Successfully.');
    console.log(this.dynamicArray);
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
  /* Ended calculateSumOfInvoiceAmount() */

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


  invoiceParticularRowDetailsFlag: boolean = true;
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
    // console.log(this.contactPersonsDetailsFlag);
    return this.invoiceParticularRowDetailsFlag;
  }



}
