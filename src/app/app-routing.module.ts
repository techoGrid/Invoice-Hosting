import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { CreateUserComponent } from "./sidebar/user/create-user/create-user.component";
import { SidebarComponent } from "./sidebar/sidebar/sidebar.component";
import { HeaderComponent } from "./header/header/header.component";
import { HomeComponent } from "./sidebar/home/home/home.component";
import { DeleteUserComponent } from "./sidebar/user/update/delete-user/delete-user.component";
import { LoginComponent } from "./login/login/login.component";
import { CreateClientComponent } from "./sidebar/client/create-client/create-client.component";
import { CreateInvoiceComponent } from "./sidebar/sevice-invoice/create-invoice/create-invoice.component";
import { DeleteInvoiceComponent } from "./sidebar/sevice-invoice/update/delete-invoice/delete-invoice.component";
import { DeleteClientComponent } from "./sidebar/client/update/delete-client/delete-client.component";
import { PrintServiceComponent } from "./sidebar/print-invoice/print-service/print-service.component";
import { DefaultComponent } from "./default/default/default.component";
import { EditUserComponent } from "./sidebar/user/edit-user/edit-user.component";
import { PrintinvoiceComponent } from "./sidebar/print-invoice/printinvoice/printinvoice.component";
import { ReportComponent } from "./sidebar/report/report/report.component";
import { EditClientComponent } from "./sidebar/client/edit-client/edit-client.component";
import { EditInvoiceComponent } from "./sidebar/sevice-invoice/edit-invoice/edit-invoice.component";
import { ForgotpasswordComponent } from './forgotpassword/forgotpassword.component';
import { DashboardComponent } from './sidebar/dashboard/dashboard/dashboard.component';



const routes: Routes = [
  { path: "", component: LoginComponent },
  { path:"forgot",component:ForgotpasswordComponent},
//{ path:"changeForgot",component:ChangepasswordComponent},
  {path: "default",component: DefaultComponent,
  children: [{ path: "sidebar", component: SidebarComponent },
               { path: "home", component: HomeComponent },
               { path: "header", component: HeaderComponent },
               { path: "createUser", component: CreateUserComponent },
               { path: "update", component: DeleteUserComponent },
               { path: "createClient", component: CreateClientComponent },
               { path: "createinvoice", component: CreateInvoiceComponent },
               { path: "deleteInvoice", component: DeleteInvoiceComponent },
               { path: "updateClient", component: DeleteClientComponent },
               { path: "printService", component: PrintServiceComponent },
               { path: "printInvoiceCopy/:id", component: PrintinvoiceComponent },
               { path: "report", component: ReportComponent },
               { path: "editUser/:id", component: EditUserComponent },
               { path: "editClient/:id", component: EditClientComponent },
               { path: "editInvoice/:id", component: EditInvoiceComponent },
               {path:"dashboard" , component:DashboardComponent}
              
              ]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
