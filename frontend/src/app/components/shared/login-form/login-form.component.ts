import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {UsersService} from "../../../services/users-service/users.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {User} from "../../../models/User";
import {HttpErrorResponse} from "@angular/common/http";
import {phoneOrEmailValidator} from "../../../validators/PhoneOrEmailValidator";
import {ModalService} from "../../../services/modal/modal.service";

@Component({
  selector: 'app-login',
  templateUrl: './login-form.component.html',
  //TODO figure out css styles
  styleUrls: ['./login-form.component.scss', '../sign-modal/sign-modal.component.scss']
})
/**
 * Компонент формы авторизации.
 */
export class LoginFormComponent implements OnInit {
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
              private router: Router,
              private route: ActivatedRoute,
              private modalService: ModalService) {
    this.form = new FormGroup({
      phoneOrEmail: new FormControl(null, [
        Validators.required,
        phoneOrEmailValidator(),
      ]),
      password: new FormControl(null, [
        Validators.required,
      ])
    })
  }

  ngOnInit() {
    //TODO figure out
    this.route.queryParams.subscribe((params: Params) => {
      if (params['loginAgain']) {
        this.message = 'Введите данные'
      }
    })
  }

  /**
   * Запрос на аутентификацию.
   */
  login() {
    if (this.form.invalid) {
      return
    }
    this.submitted = true
    const user: User = {
      phoneOrEmail: this.form.value.phoneOrEmail,
      password: this.form.value.password,
    }
    this.usersService.login(user).subscribe({
      next: (response) => {
        this.form.reset()
        //this.isSuccess.emit()
        this.modalService.close()
        this.router.navigate(['', '/']).then(r => '/')
        this.submitted = false
      },
      error: (response: HttpErrorResponse) => {
        this.usersService.error$.next(response.error.messages[0])
        this.form.get('password')?.reset()
        this.submitted = false

      },
      complete: () => {
      }
    });
  }
}
