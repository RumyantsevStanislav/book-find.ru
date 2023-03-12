import {Component, EventEmitter, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UsersService} from "../../../services/users-service/users.service";
import {ActivatedRoute, Router} from "@angular/router";
import {phoneOrEmailValidator} from "../../../validators/PhoneOrEmailValidator";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-password-recovering',
  templateUrl: './password-recovering-form.component.html',
  styleUrls: ['./password-recovering-form.component.css', '../sign-modal/sign-modal.component.scss']
})
export class PasswordRecoveringFormComponent {

  form: FormGroup
  submitted = false
  @Output() isSuccess = new EventEmitter<void>()
  message: string | undefined
  success: boolean | undefined

  constructor(public usersService: UsersService, private router: Router) {
    this.form = new FormGroup({
      phoneOrEmail: new FormControl(null, [
        Validators.required,
        phoneOrEmailValidator(),
      ])
    })
  }

  submit() {
    if (this.form.invalid) {
      return
    }
    this.submitted = true
    const phoneOrEmail = this.form.value.phoneOrEmail
    this.usersService.resetPassword(phoneOrEmail).subscribe({
      next: (req) => {
        this.form.reset()
        this.isSuccess.emit()
        this.success = true
        this.router.navigate(['', '/']).then(r => '/')
        this.submitted = false
      },
      error: (response: HttpErrorResponse) => {
        this.usersService.error$.next(response.error.messages[0])
        this.form.get('phoneOrEmail')?.reset()
        this.submitted = false

      },
      complete: () => {
        console.log('Complete')
        // this.router.navigate(['', '/']).then(r => '/')
        this.submitted = false

      }
    });
  }

}
