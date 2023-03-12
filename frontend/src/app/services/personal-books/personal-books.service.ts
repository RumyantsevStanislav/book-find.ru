import {Injectable, ViewChild} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {PersonalBook, PersonalBookImpl} from "../../models/PersonalBook";
import {UsersService} from "../users-service/users.service";
import {SignModalDirective} from "../../directives/sign-modal/sign-modal.directive";
import {environment} from "../../environments/environment.dev";
import {catchError} from "rxjs/operators";
import {Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PersonalBooksService {

  private readonly personalBooksURL = environment.serverUrl + environment.personalBooksUrl;
  submitted: boolean | undefined

  @ViewChild(SignModalDirective, {static: true}) signModal!: SignModalDirective;

  constructor(private http: HttpClient, private usersService: UsersService) {
  }

  private postRequest(personalBook: PersonalBook) {
    return this.http.post<any[]>(this.personalBooksURL, personalBook)
      .pipe(catchError(this.handleError.bind(this)));
  }

  private getRequest(): Observable<PersonalBookImpl[]> {
    return this.http.get<PersonalBookImpl[]>(this.personalBooksURL)
      .pipe(catchError(this.handleError.bind(this)));
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

  addToLibrary(isbn: number) {
    const personalBook: PersonalBook = new PersonalBook(isbn, "Прочитано", 0, '')
    this.postRequest(personalBook).subscribe({
      next: (req) => {
        console.log("SUCCESS")
      },
      error: (req: HttpErrorResponse) => {
        //this.apiError = req.;
        console.log('Error ' + req)
        this.submitted = false
      },
      complete: () => {
        console.log('Complete')
        // this.router.navigate(['', '/']).then(r => '/')
        this.submitted = false
      }
    });
  }

  getLibrary(): Observable<PersonalBookImpl[]> {
    return this.getRequest()
  }
}

