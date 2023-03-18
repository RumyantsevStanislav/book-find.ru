import {Component, OnInit, ViewChild} from '@angular/core';
import {ModalService} from "../../../services/modal/modal.service";
import {Observable} from "rxjs";
import {SignModalDirective} from "../../../directives/sign-modal/sign-modal.directive";
import {RegistrationFormComponent} from "../registration-form/registration-form.component";
import {PasswordRecoveringFormComponent} from "../password-recovering-form/password-recovering-form.component";
import {ComponentType} from "@angular/cdk/portal";
import {LoginFormComponent} from "../login-form/login-form.component";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {UsersService} from "../../../services/users-service/users.service";
import {AlertService} from "../../../services/alert/alert.service";


@Component({
  selector: 'app-sign-modal',
  templateUrl: './sign-modal.component.html',
  styleUrls: ['./sign-modal.component.scss']
})
export class SignModalComponent implements OnInit {

  //@Input() title =''
  //@Output() close = new EventEmitter<void>()
  title: string | undefined
  isSignIn = true;
  isSignUp = false;
  isPasswordRecovering = false;
  buttonText: string | undefined
  signUpButtonText = "Зарегистрироваться"
  signInButtonText = "Войти"
  signUpTitle = "Регистрация"
  signInTitle = "Вход"
  passwordRecoveringTitle = "Восстановление пароля"
  token: string | undefined

  @ViewChild(SignModalDirective, {static: false}) signModal!: SignModalDirective;


  display$: Observable<'open' | 'close'> | undefined;

  constructor(private modalService: ModalService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private usersService: UsersService,
              private alertService: AlertService) {
  }

  ngOnInit(): void {
    this.display$ = this.modalService.watch();
    //this.router.events.subscribe((url: any) => console.log(url));
    // this.router.events.pipe(
    //   filter(event => event instanceof NavigationEnd)
    // ).subscribe(event => {
    //   console.log(event);
    // });
    this.router.events.subscribe(
      (event: any) => {
        if (event instanceof NavigationEnd) {
          if (event.url.split("?")[0] == "/changePassword") {
            this.activatedRoute.queryParams.subscribe(params => {
              this.token = params['token']
              if (!!this.token) {
                this.usersService.changePassword(this.token).subscribe({
                  next: (req) => {

                  },
                  error: (messages: string) => {
                    this.alertService.danger(messages)
                    this.closeModal()
                  },
                  complete: () => {
                  }
                })
              } else {
                this.alertService.danger("Неправильная ссылка для изменения пароля.")
                this.closeModal()
              }
            })
            this.title = "Изменение пароля"
            this.isPasswordRecovering = false
            this.isSignIn = !this.isSignIn
            this.isSignUp = !this.isSignUp
            this.buttonText = this.isSignIn ? this.signUpButtonText : this.signInButtonText
          } else {
            this.title = this.signInTitle
            this.buttonText = this.signUpButtonText
          }
        }
      }
    )
  }

  //TODO when mousedown on modal and mouseup outside - modal closed
  closeModal() {
    this.modalService.close();
    this.isSignIn = true
    this.isSignUp = false
    this.buttonText = this.isSignIn ? this.signUpButtonText : this.signInButtonText
    this.title = this.isSignIn ? this.signInTitle : this.signUpTitle
    this.signModal.showSignModal(LoginFormComponent)
  }

//TODO needed refactoring
  toggleSignType() {
    this.isPasswordRecovering = false
    this.isSignIn = !this.isSignIn
    this.isSignUp = !this.isSignUp
    this.title = this.isSignIn ? this.signInTitle : this.signUpTitle
    this.buttonText = this.isSignIn ? this.signUpButtonText : this.signInButtonText
    const component: ComponentType<any> = this.isSignIn ? LoginFormComponent : RegistrationFormComponent
    this.signModal.showSignModal(component)
  }

  recoverPassword() {
    this.isSignIn = false
    this.isSignUp = true
    this.buttonText = this.isSignIn ? this.signUpButtonText : this.signInButtonText
    this.title = this.passwordRecoveringTitle;
    this.isPasswordRecovering = true;
    this.signModal.showSignModal(PasswordRecoveringFormComponent)
  }

  // public clickOutsideSignModal() {
  //   this.close.emit();
  // }
}

export type modalType = 'signIn' | 'signUp' | 'passwordRecovering' | 'passwordChanging'
