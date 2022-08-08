import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {AuthResponse} from "../models/JWT";
import {User} from "../models/User";
import {Observable, Subject, throwError} from "rxjs";
import {catchError, tap} from "rxjs/operators";

@Injectable()
export class AuthService {
  public error$: Subject<string> = new Subject<string>()

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
    return this.http.post(`http://localhost:8189/book-find/auth`, user)
      .pipe(
        tap<any>(this.setToken),
        catchError(this.handleError.bind(this))
      )
  }

  logout() {
    this.setToken(null)
  }

  isAuthenticated(): boolean {
    return !!this.getToken()
  }

  private handleError(error: HttpErrorResponse) {
    console.log(error)
    this.error$.next(error.error.message)
    // const {message} = error.error.error //it is from firebase
    // switch (message) {
    //   case 'EMAIL_NOT_FOUND':
    //     this.error$.next('email не найден')
    //     break
    //   case 'INVALID_EMAIL':
    //     this.error$.next('Неверный email')
    //     break
    //   case 'INVALID_PASSWORD':
    //     this.error$.next('Неверный пароль')
    //     break
    // }
    return throwError(error)
  }

  private setToken(response: AuthResponse | null) {
    if (response) {
      console.log(response)
      const expDate = new Date(new Date().getTime() + +response.expiresIn * 1000+3600000)
      localStorage.setItem('token', response.token)
      localStorage.setItem('token-exp', expDate.toString())
    } else {
      localStorage.clear()
    }
  }
}
