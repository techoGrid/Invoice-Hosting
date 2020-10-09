import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule, MatFormFieldModule, MatIconModule, MatInputModule, MatListModule, MatPaginatorModule, MatSnackBarModule, MatTableModule } from '@angular/material';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';





@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    MatIconModule,
    MatListModule,
    MatTableModule,
    MatInputModule,
    MatFormFieldModule,
    MatPaginatorModule,
    MatButtonModule,
    MatSnackBarModule,
    RouterModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule
  ]
})
export class PrintInvoiceModule { }
