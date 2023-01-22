import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {UsersService} from "../../../services/users-service/users.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {User} from "../../../models/User";
import {HttpErrorResponse} from "@angular/common/http";
import {ApiError} from "../../../models/Response";
import {phoneOrEmailValidator} from "../../PhoneOrEmailValidator";
import {SignModalDirective} from "../../../sign-modal.directive";

@Component({
  selector: 'app-login',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {
//TODO figure out css styles
  form: FormGroup
  submitted = false
  @Output() isSuccess = new EventEmitter<void>()
  message: string | undefined
  apiError: ApiError | undefined

  constructor(public usersService: UsersService, private router: Router, private route: ActivatedRoute) {
    this.form = new FormGroup({
      phoneOrEmail: new FormControl(null, [
        Validators.required,
        phoneOrEmailValidator(),
      ]),
      password: new FormControl(null, [
        Validators.required,
        Validators.minLength(8)
      ])
    })
  }

  ngOnInit() {
    this.route.queryParams.subscribe((params: Params) => {
      if (params['loginAgain']) {
        this.message = 'Введите данные'
      }
    })
    this.usersService.error$.next("Test")
  }

  submit() {
    if (this.form.invalid) {
      return
    }
    this.submitted = true
    const user: User = {
      phoneOrEmail: this.form.value.phoneOrEmail,
      password: this.form.value.password,
    }

    this.usersService.login(user).subscribe({
      next: (req) => {
        this.form.reset()
        this.isSuccess.emit()
        this.router.navigate(['', '/']).then(r => '/')
        this.submitted = false
      },
      error: (response: HttpErrorResponse) => {
        let apiError: ApiError
        this.apiError = response.error
        if (this.apiError != undefined) {
          this.message = this.apiError.messages[0]
        }
        this.form.get('password')?.reset()
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
