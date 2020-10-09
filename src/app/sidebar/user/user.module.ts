import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreateUserComponent } from './create-user/create-user.component';
import { DeleteUserComponent } from './update/delete-user/delete-user.component';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import {MatPaginatorModule} from '@angular/material/paginator';
import {ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    MatTableModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatPaginatorModule
    // MatHeaderCell,
    // MatHeaderCellDef,
    // MatHeaderRow,
    // MatHeaderRowDef
  ]
})
export class UserModule { }
