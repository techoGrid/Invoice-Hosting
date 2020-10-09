import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PrintService } from 'src/app/Service/print.service';
import pdfMake from 'pdfmake/build/pdfmake';
import pdfFonts from 'pdfmake/build/vfs_fonts';
pdfMake.vfs = pdfFonts.pdfMake.vfs;
declare var $: any;


export class addRow {
  particulars: string;
  particularsAmount: string;

}
export class BillGenerator {

  clientName: string;
  position: number = 1;
  clientType: string;
  gstType: string;
  particulars: number;
  totalAmount: number;
  invoiceDate: string;
  invoiceNo: string;
  invoiceAmount: number;
  invoiceId: number;
  invoiceType: string;
  month: string;
  year: string;
  financialYear: string;
  cgstAmount: string;
  sgstAmount: string;
  igstAmount: string;
  cgstPercentage: string;
  sgstPercentage: string;
  igstPercentage: string;
  clientAddress: string;
  modifyRemarks: string;
  deleteRemarks: string;
  particularsAmount: number;
  gstin: string;
  icon: string;
}
@Component({
  selector: 'app-printinvoice',
  templateUrl: './printinvoice.component.html',
  styleUrls: ['./printinvoice.component.css']
})

export class PrintinvoiceComponent implements OnInit {
  id;
  billGenerator: BillGenerator;
  generate;
  newInvoice: any = {};
  invoiceArray: Array<addRow> = [];
  constructor(
    private activate: ActivatedRoute,
    private printService: PrintService,
    private router:Router
  ) {
    this.activate.params.subscribe(data => {
      this.id = data.id;
    });
  }
  invoiceDetails: any;
  ngOnInit() {
    // this.billGenerator = new BillGenerator();
    this.printService.getInvoiceForPrint(this.id).subscribe(response => {
      this.invoiceDetails = response;
      this.getDataForPariculars(response)
      this.showHideFunctionality(this.invoiceDetails);
    });
  }

  cgstAndSgstFlag = false;
  igstFlag = false;
  showHideFunctionality(data) {
        console.log(data.gstType);
        
    if (data.gstType == "Local") {
      console.log(this.cgstAndSgstFlag);
      
      this.cgstAndSgstFlag = true;
    } else if (data.gstType == "Interstate") {
      console.log(this.igstFlag);
      
      this.igstFlag = true;
    }
    else {
      this.cgstAndSgstFlag = false;
      this.igstFlag = false;
    }
  }

 

  particulars: any = [];
  getParticularAmount(details) {

    let particularsAmount: any = [];
    this.particulars = details.particulars.split(',');
    particularsAmount = details.particularsAmount.split(',');
  }

  // generatePdf() {
  //   const pdfPageDetails = this.getPdfPageDetails();
  //   pdfMake.createPdf(pdfPageDetails).open();
  // }
  /* getPdfPageDetails Method Start */
  getPdfPageDetails() {
    return {
      /* a string or { width: number, height: number } */
      pageSize: 'A4',
      /* by default we use portrait, you can change it to landscape if you wish */
      pageOrientation: 'portrait',
      /* [left, top, right, bottom] or [horizontal, vertical] or just a number for equal margins */
      pageMargins: [40, 60, 40, 60],
      info: {
        title: 'Bill Details',
        author: 'Vikash Kumar',
        subject: 'Invoice Details'
      },
      /* watermark:{
         text:"Vetologic",
         fontSize:120,
         bold: true,
         opacity: 0.1,
       },*/

      /* Content Start */
      content: [
        {
          text: 'INVOICE',
          bold: true,
          decoration: 'underline',
          fontSize: 18,
          alignment: 'center',
          margin: [0, 0, 0, 20]
        },
        {
          columns: [
            [{
              text: 'Our GSTIN: XXXXXXXXX',
              bold: true,
              alignment: 'left',
              colspan: 10,
              fontSize: 11,
              fontFamily: 'TimesRoman',
              marginTop: 15
            },
            {
              text: 'Bill No :  ' + this.billGenerator.invoiceNo,
              bold: true,
              alignment: 'left',
              colspan: 8,
              fontSize: 11,
              fontFamily: 'TimesRoman',
              marginTop: 15
            },
            {
              text: 'Date :  ' + this.billGenerator.invoiceDate,
              bold: true,
              alignment: 'right',
              colspan: 2,
              fontSize: 11,
              fontFamily: 'TimesRoman'
            },
            {
              text: 'To,',
              alignment: 'left',
              marginTop: 15,
              fontSize: 11,
              fontFamily: 'TimesRoman'
            },
            {
              text: this.billGenerator.clientName,
              alignment: 'left',
              colspan: 4,
              fontSize: 11,
              fontFamily: 'TimesRoman'
            },
            {
              text: this.billGenerator.clientAddress,
              alignment: 'left',
              colspan: 4,
              fontSize: 11,
              fontFamily: 'TimesRoman'
            },
            {
              text: 'GSTIN : ' + this.billGenerator.gstin,
              alignment: 'left',
              colspan: 10,
              fontSize: 11,
              marginTop: 12,
              fontFamily: 'TimesRoman'
            },
            {
              text: 'Dear Sir/Madam',
              alignment: 'left',
              colspan: 10,
              fontSize: 11,
              marginTop: 35,
              fontFamily: 'TimesRoman'
            },
            {
              text: 'Table of Content',
              bold: true,
              decoration: 'underline',
              decorationColor: 'black',
              fontSize: 15,
              alignment: 'center',
              fontFamily: 'TimesRoman',
              margin: [30, 20, 20, 20],
            },
            { /* Particulars Details Table Start */
              // margin: [left, top, right, bottom]
              margin: [30, 0, 20, 0],

              table: {
                // headers are automatically repeated if the table spans over multiple pages
                // you can declare how many rows should be treated as headers
                headerRows: 1,
                widths: [50, 280, 100],
                body: [ /* Table Body Start */
                  [
                    {
                      text: 'Sl.No',
                      alignment: 'center',
                      style: 'tableHeader',
                      fontFamily: 'TimesRoman',
                      fillColor: 'lightgrey'
                    },
                    {
                      text: 'Particulars',
                      alignment: 'center',
                      style: 'tableHeader',
                      fontFamily: 'TimesRoman',
                      fillColor: 'lightgrey',
                    },
                    {
                      text: 'Amt.in Rs.',
                      alignment: 'center',
                      style: 'tableHeader',
                      fontFamily: 'TimesRoman',
                      fillColor: 'lightgrey'
                    },
                  ],
                  [
                    { text: this.billGenerator.invoiceId, alignment: 'center', fontFamily: 'TimesRoman' },
                    { text: this.billGenerator.particulars },
                    { text: this.billGenerator.particularsAmount, fontFamily: 'TimesRoman', alignment: 'right' },
                  ],
                  [
                    {
                      text: 'Sub-Total Amount', alignment: 'left', fontFamily: 'TimesRoman', bold: true, colSpan: 2, border:
                        [true, false, true, false], marginTop: 5
                    },
                    { text: ' ' },
                    {
                      text: this.billGenerator.particularsAmount, fontFamily: 'TimesRoman', alignment: 'right', border:
                        [true, false, true, false], marginTop: 5
                    },
                  ],
                  [
                    {
                      text: 'Add: CGST @ ' + this.billGenerator.cgstPercentage, alignment: 'left', fontFamily: 'TimesRoman', colSpan: 2, border:
                        [true, false, true, false], marginTop: 5
                    },
                    { text: ' ' },
                    {
                      text: this.billGenerator.cgstAmount, alignment: 'right', fontFamily: 'TimesRoman', border:
                        [true, false, true, false], marginTop: 5
                    },
                  ],
                  [
                    {
                      text: 'Add: SGST @ ' + this.billGenerator.sgstPercentage, fontFamily: 'TimesRoman', alignment: 'left', colSpan: 2, border:
                        [true, false, true, false], marginTop: 5
                    },
                    { text: ' ' },
                    {
                      text: this.billGenerator.sgstAmount, alignment: 'right', fontFamily: 'TimesRoman', border:
                        [false, false, true, false], marginTop: 5
                    },
                  ],
                  [
                    { text: 'Total Amount', alignment: 'center', colSpan: 2, marginTop: 5 },
                    { text: ' ' },
                    { text: this.billGenerator.totalAmount, alignment: 'right', fontFamily: 'TimesRoman', marginTop: 5 },
                  ],
                ] /* Table Body End */
              }
            }, /* Particulars Details Table End */
            {
              text: 'for <COMPANY NAME>',
              bold: true,
              fontFamily: 'TimesRoman',
              marginTop: 30
            }
            ]
          ]
        },
      ] /* Content End */

    }
  } /* getPdfPageDetails method End */

  printInvoice(cmpName) {
    
    $(document).ready(function () {
      $("#print").hide();
      
    });
    let printContents = document.getElementById(cmpName).innerHTML;
    let originalContents = document.body.innerHTML;
    document.body.innerHTML = printContents;
    window.print();
    document.body.innerHTML = originalContents;
  }
  getDataForPariculars(data) {

    let particulars: any = [];
    let particularsAmount: any = [];
    particulars = data.particulars.split(',');
    particularsAmount = data.particularsAmount.split(',');
    if (particulars.length == particularsAmount.length) {
      for (let i = 0; i < particulars.length; i++) {
        this.newInvoice = { particulars: particulars[i], particularsAmount: particularsAmount[i] };
        this.invoiceArray.push(this.newInvoice);
      }
    }
  }
  Back(){
    this.router.navigate(["default/printService"]);
  }
}
