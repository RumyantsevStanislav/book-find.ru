import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-sign-modal',
  templateUrl: './sign-modal.component.html',
  styleUrls: ['./sign-modal.component.scss']
})
export class SignModalComponent implements OnInit {

  @Input() title = 'Default title'
  @Output() close = new EventEmitter<void>()

  isSignIn = true;
  isSignUp = false;
  buttonText = "Регистрация"

  constructor() {
  }

  ngOnInit(): void {
  }

  toggleSignType() {
    this.isSignIn = !this.isSignIn
    this.isSignUp = !this.isSignUp
    this.title = this.isSignIn ? "Войти" : "Регистрация"
    this.buttonText = this.isSignIn ? "Регистрация" : "Войти"
  }

  public clickOutsideSignModal() {
    this.close.emit();
  }


}
