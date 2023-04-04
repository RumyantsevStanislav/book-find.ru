import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {SystemUser} from "../../../models/User";
import {HttpErrorResponse} from "@angular/common/http";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {phoneOrEmailValidator} from "../../../validators/PhoneOrEmailValidator";
import {ConfirmedValidator} from "../../../validators/ConfirmedValidator";
import {UsersService} from "../../../services/users-service/users.service";
import {regExpPatterns} from "../../../validators/RegExpPatterns";
import {ModalService} from "../../../services/modal/modal.service";
import {AlertService} from "../../../services/alert/alert.service";

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

  constructor(public usersService: UsersService, private modalService: ModalService, private alertService: AlertService) {
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
        this.submitted = false
        this.modalService.close()
        this.alertService.success(response.message)
      },
      error: (response: HttpErrorResponse) => {
        this.usersService.error$.next(response.error.messages[0])
        this.form.get('phoneOrEmail')?.reset()
        this.submitted = false
      },
      complete: () => {
      }
    });
  }
}
