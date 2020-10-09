import { Injectable } from "@angular/core";
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpResponse,
} from "@angular/common/http";
import { Observable } from "rxjs";
import { AuthenticationService } from "./authentication.service";
import { NgxSpinnerService } from 'ngx-spinner';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: "root",
})
export class HttpInterceptorService implements HttpInterceptor {
  constructor(private authenticationService: AuthenticationService, private spinner: NgxSpinnerService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
  //  this.spinner.show(); // spinner starts
    if (sessionStorage.getItem(this.authenticationService.SESSION_USER_KEY) && sessionStorage.getItem(this.authenticationService.SESSION_TOKEN_KEY)) {
      req = req.clone({
        setHeaders: {
          Authorization: sessionStorage.getItem(this.authenticationService.SESSION_TOKEN_KEY)
        }
      });
    }
    return next.handle(req).pipe(
      tap((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          
        }
      }, (error) => {
       this.spinner.hide();
      })
    );
  }
}
