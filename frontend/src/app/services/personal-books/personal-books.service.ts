import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {PersonalBook, PersonalBookImpl, Status} from "../../models/PersonalBook";
import {environment} from "../../environments/environment.dev";
import {catchError} from "rxjs/operators";
import {Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PersonalBooksService {

  private readonly personalBooksURL = environment.serverUrl + environment.personalBooksUrl;
  submitted: boolean | undefined

  constructor(private http: HttpClient) {
  }

  private postRequest(personalBook: PersonalBook): Observable<PersonalBook> {
    return this.http.post<PersonalBook>(this.personalBooksURL, personalBook)
      .pipe(catchError(this.handleError.bind(this)));
  }

  private getRequest(): Observable<PersonalBookImpl[]> {
    return this.http.get<PersonalBookImpl[]>(this.personalBooksURL)
      .pipe(catchError(this.handleError.bind(this)));
  }

//TODO put common logic into interceptor: alerts, modal, client-side error, return ApiError.
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

  addToLibrary(isbn: string, status: Status) {
    const personalBook: PersonalBook = new PersonalBook(isbn, status.valueOf())
    this.postRequest(personalBook).subscribe({
      next: (res) => {
        //TODO emit status, estimation, comment and update book card.
      },
      error: (res: HttpErrorResponse) => {

        this.submitted = false
      },
      complete: () => {
        this.submitted = false
      }
    });
  }

  getLibrary(): Observable<PersonalBookImpl[]> {
    return this.getRequest()
  }
}

