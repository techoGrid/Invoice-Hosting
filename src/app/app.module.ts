import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { SidebarModule } from "./sidebar/sidebar.module";
import { UserModule } from "./sidebar/user/user.module";
import { HeaderModule } from "./header/header.module";
import { HomeModule } from "./sidebar/home/home.module";
import { RouterModule } from "@angular/router";
import { SidebarComponent } from "./sidebar/sidebar/sidebar.component";
import { HeaderComponent } from "./header/header/header.component";
import { FooterComponent } from "./footer/footer/footer.component";
import { DataTablesModule } from "angular-datatables";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { LoginModule } from "./login/login.module";
import { LoginComponent } from "./login/login/login.component";
import { CreateClientComponent } from "./sidebar/client/create-client/create-client.component";
import { CreateInvoiceComponent } from "./sidebar/sevice-invoice/create-invoice/create-invoice.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { DeleteInvoiceComponent } from "./sidebar/sevice-invoice/update/delete-invoice/delete-invoice.component";
import {
  MatPaginatorModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatFormFieldModule,
  MatInputModule,
} from "@angular/material";
import { DeleteClientComponent } from "./sidebar/client/update/delete-client/delete-client.component";
import { PrintServiceComponent } from "./sidebar/print-invoice/print-service/print-service.component";
import { PrintInvoiceModule } from "./sidebar/print-invoice/print-invoice.module";
import { DefaultModule } from "./default/default.module";
import { DefaultComponent } from "./default/default/default.component";
import { EditUserComponent } from "./sidebar/user/edit-user/edit-user.component";
import { ReportComponent } from "./sidebar/report/report/report.component";
import { PrintinvoiceComponent } from "./sidebar/print-invoice/printinvoice/printinvoice.component";
import { EditClientComponent } from "./sidebar/client/edit-client/edit-client.component";
import { DeleteUserComponent } from "./sidebar/user/update/delete-user/delete-user.component";
import { CreateUserComponent } from "./sidebar/user/create-user/create-user.component";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { EditInvoiceComponent } from "./sidebar/sevice-invoice/edit-invoice/edit-invoice.component";
import { HomeComponent } from './sidebar/home/home/home.component';
import { ForgotpasswordComponent } from './forgotpassword/forgotpassword.component';
import { AuthenticationService } from './Service/authentication.service';
import { HttpInterceptorService } from './Service/http-interceptor.service';
import { NgxSpinnerModule } from 'ngx-spinner';
import { ToastrModule } from 'ngx-toastr';
import { DashboardComponent } from './sidebar/dashboard/dashboard/dashboard.component';
import { DashboardModule } from './sidebar/dashboard/dashboard.module';


@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    CreateClientComponent,
    CreateInvoiceComponent,
    DeleteInvoiceComponent,
    DeleteClientComponent,
    PrintServiceComponent,
    ReportComponent,
    DefaultComponent,
    PrintinvoiceComponent,
    EditUserComponent,
    EditClientComponent,
    DeleteUserComponent,
    CreateUserComponent,
    EditInvoiceComponent,
    HomeComponent,
    ForgotpasswordComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SidebarModule,
    UserModule,
    HeaderModule,
    HomeModule,
    RouterModule,
    DataTablesModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    DefaultModule,
    LoginModule,
    FormsModule,
    MatPaginatorModule,
    PrintInvoiceModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatInputModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxSpinnerModule,
    DashboardModule
  ],

  providers: [
    AuthenticationService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
