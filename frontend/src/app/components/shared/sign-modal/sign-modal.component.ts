import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {ModalService} from "../../../services/modal/modal.service";
import {Observable} from "rxjs";
import {SignModalDirective} from "../../../directives/sign-modal/sign-modal.directive";
import {RegistrationFormComponent} from "../registration-form/registration-form.component";
import {PasswordRecoveringFormComponent} from "../password-recovering-form/password-recovering-form.component";
import {ComponentType} from "@angular/cdk/portal";
import {LoginFormComponent} from "../login-form/login-form.component";


@Component({
  selector: 'app-sign-modal',
  templateUrl: './sign-modal.component.html',
  styleUrls: ['./sign-modal.component.scss']
})
export class SignModalComponent implements OnInit {

  @Input() title = ''
  @Output() close = new EventEmitter<void>()

  isSignIn = true;
  isSignUp = false;
  isPasswordRecovering = false;
  buttonText: string | undefined
  signUpButtonText = "Зарегистрироваться"
  signInButtonText = "Войти"
  signUpTitle = "Регистрация"
  signInTitle = "Вход"
  passwordRecoveringTitle = "Восстановление пароля"

  @ViewChild(SignModalDirective, {static: false}) signModal!: SignModalDirective;


  display$: Observable<'open' | 'close'> | undefined;

  constructor(private modalService: ModalService) {
  }

  ngOnInit(): void {
    this.display$ = this.modalService.watch();
    this.title = this.signInTitle
    this.buttonText = this.signUpButtonText
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

  public clickOutsideSignModal() {
    this.close.emit();
  }
}
