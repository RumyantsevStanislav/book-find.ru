import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UsersService} from "../../../services/users-service/users.service";
import {regExpPatterns} from "../../../validators/RegExpPatterns";
import {ConfirmedValidator} from "../../../validators/ConfirmedValidator";
import {PasswordDto} from "../../../models/User";
import {HttpErrorResponse} from "@angular/common/http";
import {AlertService} from "../../../services/alert/alert.service";
import {ModalService} from "../../../services/modal/modal.service";
import {SignModalDirective} from "../../../directives/sign-modal/sign-modal.directive";

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css', '../sign-modal/sign-modal.component.scss']
})
export class ChangePasswordComponent implements OnInit {

  token: string | undefined

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

  constructor(public usersService: UsersService,
              private route: ActivatedRoute,
              private router: Router,
              private alertService: AlertService,
              private modalService: ModalService) {
    this.form = new FormGroup({
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

  ngOnInit(): void {
    this.route.url.subscribe(url => {
    })
    this.route.queryParams.subscribe(params => {
      this.token = params.token
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
  changePassword() {
    if (this.form.invalid || this.token === undefined) {
      return
    }
    this.submitted = true
    const passwordDto: PasswordDto = {
      token: this.token,
      password: this.form.value.passwords.password,
      matchingPassword: this.form.value.passwords.matchingPassword,
    }
    console.log(passwordDto)
    return this.usersService.savePassword(passwordDto).subscribe({
      next: (response) => {
        this.submitted = false
        this.router.navigate(['', '/']).then(r => '/')
        this.modalService.close()
        this.alertService.success(response.message)
        this.isSuccess.emit()
      },
      error: (response: HttpErrorResponse) => {
        this.usersService.error$.next(response.error.messages[0])
        this.form.get('passwords')?.reset()
        this.submitted = false
      },
      complete: () => {
      }
    });
  }

}
