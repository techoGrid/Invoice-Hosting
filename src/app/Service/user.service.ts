import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { User } from '../user';
import { Designation } from '../Designation';



@Injectable({
  providedIn: "root",
})
export class UserService {
  private baseURL = '/api';
  constructor(private http: HttpClient) {}

  getUser(): Observable<any> {
    return this.http.get<User>(`${this.baseURL}/user/viewUser`);
  }
  addUser(user: User, id: any): Observable<any> {
    return this.http.post(`${this.baseURL}/user/saveUser`, user, {
      params: { id: id },
    });
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete(`${this.baseURL}/user/deleteUser/${id}`);
  }

  getUserForEdit(id: number): Observable<any> {
    return this.http.get(`${this.baseURL}/user/getUserForEdit/${id}`);
  }

  upDateUserRegistration(user: User, id: number ,desinationID:number): Observable<any> {
    return this.http.put<User[]>(
      `${this.baseURL}/user/userRegistrationUpdate/${id}/${desinationID}`,user
    );
  }

  toCheckUserNameAlreadyExistOrNot(userName: string): Observable<any> {
    return this.http.get(`${this.baseURL}/user/checkUserNameAlreadyExistOrNot/${userName}`);
  }
  toCheckEmailIdAlreadyExistOrNot(email: string): Observable<any> {
    return this.http.get(`${this.baseURL}/user/checkEmailIdAlreadyExistOrNot/${email}`);
  }

  togetDesignationList(): Observable<any> {
    return this.http.get<Designation>(`${this.baseURL}/user/getDesignationList`);
  }

  getUserForDashBoard(): Observable<any> {
    return this.http.get<User>(`${this.baseURL}/user/dettingUserDetailsByName`);
  }
}
