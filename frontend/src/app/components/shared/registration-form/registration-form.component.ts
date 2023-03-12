import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {SystemUser} from "../../../models/User";
import {HttpErrorResponse} from "@angular/common/http";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {phoneOrEmailValidator} from "../../../validators/PhoneOrEmailValidator";
import {ConfirmedValidator} from "../../../validators/ConfirmedValidator";
import {ApiError, ApiMessage} from "../../../models/Response";
import {UsersService} from "../../../services/users-service/users.service";
import {regExpPatterns} from "../../../validators/RegExpPatterns";

@Component({
  selector: 'app-registration',
  templateUrl: './registration-form.component.html',
  styleUrls: ['./registration-form.component.scss', '../sign-modal/sign-modal.component.scss']
})
export class RegistrationFormComponent {
  /**
   * Видимость вводимого пароля.
   */
  visiblePassword = 'password';
  /**
   * Объект формы.
   */
  form: FormGroup
  /**
   * Флаг доступности кнопки submit на форме.
   */
  submitted = false
  /**
   * Флаг успешного завершения аутентификации.
   */
  @Output() isSuccess = new EventEmitter<void>()
  /**
   * Сообщение для пользователя.
   */
  message: string | undefined
  /**
   * Объект, возвращаемый при ошибке на запрос.
   */
  apiError: ApiError | undefined
  /**
   * Объект, возвращаемый при успешной регистрации.
   */
  apiMessage: ApiMessage | undefined;

  constructor(public usersService: UsersService) {
    this.form = new FormGroup({
      phoneOrEmail: new FormControl(null, [
        Validators.required,
        phoneOrEmailValidator(),
      ]),
      passwords: new FormGroup({
          password: new FormControl(null, [
            Validators.required,
            Validators.minLength(8),
            Validators.maxLength(20),
            Validators.pattern(regExpPatterns.passwordPattern)
          ]),
          matchingPassword: new FormControl(null, [
            Validators.required
          ])
        }, {validators: ConfirmedValidator('password', 'matchingPassword')}
      )
    })
  }

  /**
   * Изменение видимости вводимого пароля.
   */
  changeVisiblePassword() {
    if (this.visiblePassword == 'password')
      this.visiblePassword = 'text';
    else
      this.visiblePassword = 'password';
  }

  /**
   * Запрос на регистрацию пользователя.
   */
  registration() {
    if (this.form.invalid) {
      return
    }
    this.submitted = true
    const systemUser: SystemUser = {
      phoneOrEmail: this.form.value.phoneOrEmail,
      password: this.form.value.passwords.password,
      matchingPassword: this.form.value.passwords.matchingPassword,
    }
    return this.usersService.registration(systemUser).subscribe({
      next: (response) => {
        this.apiMessage = response;
        this.submitted = false
      },
      error: (response: HttpErrorResponse) => {
        this.apiError = response.error
        if (this.apiError != undefined) {
          this.usersService.error$.next(this.apiError.messages[0])
        }
        this.form.get('phoneOrEmail')?.reset()
        this.submitted = false
      },
      complete: () => {
      }
    });
  }
}
