import {Injectable} from '@angular/core';
import {UsersService} from "../users-service/users.service";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import {AlertService} from "../alert/alert.service";
import {ModalService} from "../modal/modal.service";
import {ApiError} from "../../models/Response";

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptor implements HttpInterceptor {

  constructor(private authService: UsersService,
              private alertService: AlertService,
              private modalService: ModalService) {
  }

  intercept(
    request
      :
      HttpRequest<any>,
    next
      :
      HttpHandler
  ):
    Observable<HttpEvent<any>> {
    const token = this.authService.getToken();
    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
    }
    return next.handle(request)
      .pipe(
        catchError((err) => {
          if (err.status === 401) {
            this.authService.logout();
            this.modalService.open();
            let apiError: ApiError = err.error
            if (apiError != undefined) {
              this.alertService.warning(apiError.messages[0])
            }
          }
          //const error = err.error.message || err.statusText;
          return throwError(err);
        })
      );
  }
}
