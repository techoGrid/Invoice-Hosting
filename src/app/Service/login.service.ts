import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { User } from '../sidebar/user/edit-user/edit-user.component';



@Injectable({
  providedIn: "root",
})
export class LoginService {
  private baseURL = '/api';

  constructor(private http: HttpClient) {}

  checkUserCredentials(user: User): Observable<any> {
    return this.http.post(`${this.baseURL}/index`, user);
  }

  getHomeDetails(uId, designation): Observable<any> {
    return this.http.get(`${this.baseURL}/home/${uId}/${designation}`);
  }
  sendEmail(emailId:any):Observable<any>{ 
    return this.http.put(`${this.baseURL}/forgotPassword` ,null, {params:{"emailId":emailId}});
  }
  changePassword(nPwd:string,cPwd:string){
      return this.http.get(`${this.baseURL}/changePassword/${nPwd}/${cPwd}`);
    
  }
}

