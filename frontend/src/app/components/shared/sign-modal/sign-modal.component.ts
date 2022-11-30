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

  constructor() {
  }

  ngOnInit(): void {
  }

  toggleSignType() {
    this.isSignIn = !this.isSignIn
    this.isSignUp = !this.isSignUp
  }

  clickOutsideSignModal() {
    this.close.emit();
  }


}
