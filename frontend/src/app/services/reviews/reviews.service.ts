import {Injectable, ViewChild} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {SignModalDirective} from "../../directives/sign-modal/sign-modal.directive";
import {environment} from "../../../environments/environment";
import {catchError} from "rxjs/operators";
import {throwError} from "rxjs";
import {Review} from "../../models/Book";

@Injectable({
  providedIn: 'root'
})
export class ReviewsService {

  private readonly reviewsURL = environment.serverUrl + environment.reviewsUrl;
  submitted: boolean | undefined

  @ViewChild(SignModalDirective, {static: true}) signModal!: SignModalDirective;

  constructor(private http: HttpClient) {
  }

  private postRequest(review: Review) {
    return this.http.post<any[]>(this.reviewsURL, review)
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

  create(review: Review) {
    this.postRequest(review).subscribe({
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


}

