import { Component, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent { 
  title = 'Invoice';
  text: string;

  constructor(private spinner: NgxSpinnerService) { }

  startSpinner(text: string) {
    this.text = text;
    this.spinner.show( )
  }

  stopSpinner() {
    this.spinner.hide();
  }
}
