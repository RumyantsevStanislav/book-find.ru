import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from "@angular/common/http";
import {AuthResponse} from "../../models/JWT";
import {RegisteredUser, SystemUser, User} from "../../models/User";
import {Observable, Subject, throwError} from "rxjs";
import {catchError, tap} from "rxjs/operators";
import {ApiMessage} from "../../models/Response";
import {environment} from "../../environments/environment.dev";

/**
 * Сервис для работы с пользователем.
 */
@Injectable()
export class UsersService {
  public error$: Subject<string> = new Subject<string>()
  /**
   *
   * @private
   */
  private readonly authURL = environment.serverUrl + environment.authUrl;
  /**
   *
   * @private
   */
  private readonly registerURL = environment.serverUrl + environment.registerUrl;
  /**
   *
   * @private
   */
  private readonly accountURL = environment.serverUrl + environment.accountUrl;
  /**
   *
   * @private
   */
  private readonly updateUserURL = environment.serverUrl + environment.updateUserUrl;
  /**
   *
   * @private
   */
  private readonly updatePasswordURL = environment.serverUrl + environment.updatePasswordUrl;
  /**
   *
   * @private
   */
  private readonly resetPasswordURL = environment.serverUrl + environment.resetPasswordUrl;
  /**
   *
   * @private
   */
  private readonly changePasswordURL = environment.serverUrl + environment.changePasswordUrl;

  constructor(private http: HttpClient) {
  }

  /**
   * Аутентификация пользователя.
   * @param user
   */
  login(user: User): Observable<AuthResponse> {
    return this.http.post(this.authURL, user)
      .pipe(
        tap<any>(this.setToken)
        // ,
        // catchError(this.handleError)
      )
  }

  /**
   * Получение токена авторизации из local storage с проверкой на expired.
   */
  getToken(): string | null {
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

  /**
   * Проверка на аутентификацию.
   */
  isAuthenticated(): boolean {
    return !!this.getToken() //!! - cast to boolean
  }

  /**
   * Регистрация пользователя.
   * @param systemUser
   */
  registration(systemUser: SystemUser): Observable<ApiMessage> {
    //const headers = new HttpHeaders().set('Content-Type', 'application/json')
    return this.http.post<ApiMessage>(this.registerURL, systemUser, {
      observe: "body",
    }).pipe(
      //catchError(this.handleError.bind(this))
    );
  }

  /**
   * Удаление токена авторизации.
   */
  logout() {
    this.setToken(null)
  }

  /**
   * Восстановление пароля.
   */
  resetPassword(phoneOrEmail: string) {
    const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
    return this.http.post<ApiMessage>(this.resetPasswordURL, {}, {
      observe: "body",
      params: new HttpParams().set("phoneOrEmail", phoneOrEmail)
    }).pipe(
      //catchError(this.handleError.bind(this))
    );
  }

  /**
   * Проверка токена перед заполнением формы изменения пароля {@link ChangePasswordComponent}.
   */
  changePassword(token: string): Observable<ApiMessage> {
    return this.http.get<ApiMessage>(this.changePasswordURL, {
      observe: "body",
      params: new HttpParams().set("token", token)
    }).pipe(catchError(this.handleError.bind(this)));
  }

  /**
   * Получение данных о пользователе.
   */
  getAccount(): Observable<RegisteredUser> {
    return this.http.get<RegisteredUser>(this.accountURL, {
      observe: "body",
    }).pipe(catchError(this.handleError.bind(this)));
  }

  /**
   * Обновление данных пользователя.
   *
   * @param registeredUser
   */
  updateUser(registeredUser: RegisteredUser): Observable<ApiMessage> {
    return this.http.put<ApiMessage>(this.updateUserURL, registeredUser, {
      observe: "body",
    }).pipe(catchError(this.handleError.bind(this)));
  }

  /**
   * Изменение пароля.
   *
   * @param newPassword
   * @param oldPassword
   */
  updatePassword(newPassword: string, oldPassword: string): Observable<ApiMessage> {
    const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
    return this.http.post<ApiMessage>(this.updatePasswordURL, {}, {
      observe: "body",
      params: new HttpParams().set("password", newPassword).set("oldpassword", oldPassword)
    }).pipe(catchError(this.handleError.bind(this)));
  }

  /**
   * Запись токена в localStorage.
   * @param response
   * @private
   */
  private setToken(response: AuthResponse | null) {
    if (response) {
      console.log(response)
      const expDate = new Date(new Date().getTime() + +response.expiresIn * 1000 + 3_600_000)
      localStorage.setItem('token', response.token)
      localStorage.setItem('token-exp', expDate.toString())
    } else {
      localStorage.clear()
    }
  }

  /**
   * Обработка ошибок.
   *
   * @private
   * @param httpErrorResponse
   */
  private handleError(httpErrorResponse: HttpErrorResponse) {
    let errorMessage = '';
    if (httpErrorResponse.error instanceof ErrorEvent) {
      // client-side error
      errorMessage = `Error: ${httpErrorResponse.error.message}`;
    } else {
      // server-side error
      errorMessage = httpErrorResponse.error.messages
      this.error$.next(httpErrorResponse.error.messages)
    }
    return throwError(() => {
      return errorMessage;
    });
  }
}
