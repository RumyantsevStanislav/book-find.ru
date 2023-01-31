import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from "@angular/common/http";
import {AuthResponse} from "../../models/JWT";
import {RegisteredUser, SystemUser, User} from "../../models/User";
import {Observable, Subject, throwError} from "rxjs";
import {catchError, tap} from "rxjs/operators";
import {ApiMessage} from "../../models/Response";
import {environment} from "../../environments/enviripnment.dev";


@Injectable()
export class UsersService {
  public error$: Subject<string> = new Subject<string>() //stream. subscribing example in login-form.component


  private readonly authURL = environment.serverUrl + environment.authUrl;
  private readonly registerURL = environment.serverUrl + environment.registerUrl;
  private readonly accountURL = environment.serverUrl + environment.accountUrl;
  private readonly updateUserURL = environment.serverUrl + environment.updateUserUrl;
  private readonly updatePasswordURL = environment.serverUrl + environment.updatePasswordUrl;

  constructor(private http: HttpClient) {
  }

  //get token(): string | null {
  public getToken(): string | null {
    const tokenExp = localStorage.getItem('token-exp')
    if (tokenExp === null) {
      return null
    }
    const tokenExpDate = new Date(tokenExp);
    if (new Date() > tokenExpDate) {
      this.logout()
      return null
    }
    return localStorage.getItem('token')
  }

  login(user: User): Observable<any> {
    return this.http.post(this.authURL, user)
      .pipe(
        tap<any>(this.setToken)
        // ,
        // catchError(this.handleError)
      )
  }

  logout() {
    this.setToken(null)
  }

  isAuthenticated(): boolean {
    return !!this.getToken() //!! - cast to boolean
  }

  // private handleError(error: HttpErrorResponse) {
  //   let errorMessage = '';
  //   if (error.error instanceof ErrorEvent) {
  //     // client-side error
  //     errorMessage = `Client Error: ${error.error.message}`;
  //   } else {
  //     // server-side error
  //     errorMessage = `Service Error Code: ${error.status}\nMessage: ${error.error}`;
  //     this.error$.next('Test')
  //   }
  //   return throwError(() => {
  //     return error;
  //   });
  //   // const {message} = error.error.error //it is from firebase
  //   // switch (message) {
  //   //   case 'EMAIL_NOT_FOUND':
  //   //     this.error$.next('email не найден')
  //   //     break
  //   //   case 'INVALID_EMAIL':
  //   //     this.error$.next('Неверный email')
  //   //     break
  //   //   case 'INVALID_PASSWORD':
  //   //     this.error$.next('Неверный пароль')
  //   //     break
  //   // }
  // }

  private setToken(response: AuthResponse | null) {
    if (response) {
      console.log(response)
      const expDate = new Date(new Date().getTime() + +response.expiresIn * 1000 + 3600000)
      localStorage.setItem('token', response.token)
      localStorage.setItem('token-exp', expDate.toString())
    } else {
      localStorage.clear()
    }
  }

  registration(systemUser: SystemUser): Observable<ApiMessage> {
    return this.http.post<ApiMessage>(this.registerURL, systemUser, {
      observe: "body",
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

  getAccount(): Observable<RegisteredUser> {
    return this.http.get<RegisteredUser>(this.accountURL, {
      observe: "body",
    }).pipe(catchError(this.handleError.bind(this)));
  }

  updateUser(registeredUser: RegisteredUser): Observable<ApiMessage> {
    return this.http.put<ApiMessage>(this.updateUserURL, registeredUser, {
      observe: "body",
    }).pipe(catchError(this.handleError.bind(this)));
  }

  updatePassword(newPassword: string, oldPassword: string): Observable<ApiMessage> {
    const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
    return this.http.post<ApiMessage>(this.updatePasswordURL, {}, {
      observe: "body",
      params: new HttpParams().set("password", newPassword).set("oldpassword", oldPassword)
    }).pipe(catchError(this.handleError.bind(this)));
  }
}
