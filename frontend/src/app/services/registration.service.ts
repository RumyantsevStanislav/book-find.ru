import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {SystemUser} from "../models/User";
import {ApiError, ApiMessage} from "../models/Response";
import {map, Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";

@Injectable()
export class RegistrationService {
  private url = 'http://localhost:8189/book-find/api/v1/users/register';

  constructor(private http: HttpClient) {
  }

  registration(systemUser: SystemUser, headers: HttpHeaders): Observable<ApiMessage> {
    return this.http.post<ApiMessage>(this.url, systemUser, {
      observe: "body",
      headers: headers
    }).pipe(catchError(this.handleError.bind(this)));
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.log('Handle ' + errorMessage);
    return throwError(() => {
      return errorMessage;
    });
  }
}
