import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarComponent } from './sidebar/sidebar.component';
import { HomeComponent } from './home/home/home.component';
import { RouterModule } from '@angular/router';
import { MatTableModule, MatHeaderCell, MatHeaderCellDef, MatHeaderRow, MatHeaderRowDef } from '@angular/material/table';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';



@NgModule({
  declarations: [
    
  ],
  imports: [
    CommonModule,
    RouterModule,
    MatTableModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatInputModule,
    // MatHeaderCell,
    // MatHeaderCellDef,
    // MatHeaderRow,
    // MatHeaderRowDef
  ],
  exports:[
    MatTableModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatInputModule,
    MatHeaderCell,
    MatHeaderCellDef,
    MatHeaderRow,
    MatHeaderRowDef
  ]
})
export class SidebarModule { }
