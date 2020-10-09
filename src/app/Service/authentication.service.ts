import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { map } from "rxjs/operators";
import { isNull } from 'util';

@Injectable({
  providedIn: "root",
})
export class AuthenticationService {
  SESSION_USER_KEY = "user";
  SESSION_TOKEN_KEY = "token";
  SESSION_ROLE_KEY = 'role';

  constructor(private httpClient: HttpClient) {}
  
  authenticate(username: string, password: string) {
    return this.httpClient.post<any>('http://localhost:8085/authenticate', { username, password }).pipe(map((data) => {
      if (!isNull(data)) {
        let jwtToken = 'Bearer ' + data.jwtToken;
        // alert(jwtToken)
        sessionStorage.setItem(this.SESSION_TOKEN_KEY, jwtToken);
        sessionStorage.setItem(this.SESSION_USER_KEY, data.username);
        sessionStorage.setItem(this.SESSION_ROLE_KEY, data.userRole);
        return true;
      } else {
        return false;
      }
    }));
  }
  isUserLoggedIn() {
    let user = sessionStorage.getItem(this.SESSION_USER_KEY);
    if (user === null) return false;
    return true;
  }

  getLoggedUser() {
    let user = sessionStorage.getItem(this.SESSION_USER_KEY);
    if (user === null) return '';
    return user;
  }

  getUserDetails() {
    const userDetails = {
      USER_ROLE : sessionStorage.getItem('role').replace('ROLE_',''),
      USER_NAME : sessionStorage.getItem('user')
    };

     return userDetails;
  }

  getLoggedUserRole() {
    let role = sessionStorage.getItem(this.SESSION_ROLE_KEY);
    if (role === null) return '';
    return role;
  }

  logout() {
    sessionStorage.removeItem(this.SESSION_ROLE_KEY);
    sessionStorage.removeItem(this.SESSION_USER_KEY);
    sessionStorage.removeItem(this.SESSION_TOKEN_KEY);
    return true;
  }

}
