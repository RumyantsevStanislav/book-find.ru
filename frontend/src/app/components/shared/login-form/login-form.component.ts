import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../services/auth.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {User} from "../../../models/User";
import {HttpErrorResponse} from "@angular/common/http";
import {ApiError} from "../../../models/Response";
import {phoneOrEmailValidator} from "../../PhoneOrEmailValidator";

@Component({
  selector: 'app-login',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {

  form: FormGroup
  submitted = false
  message: string | undefined
  apiError: ApiError | undefined

  constructor(public auth: AuthService, private router: Router, private route: ActivatedRoute) {
    this.form = new FormGroup({
      phoneOrEmail: new FormControl(null, [
        Validators.required,
        phoneOrEmailValidator(),
      ]),
      password: new FormControl(null, [
        Validators.required,
        Validators.minLength(3)
      ])
    })
  }

  ngOnInit() {
    this.route.queryParams.subscribe((params: Params) => {
      if (params['loginAgain']) {
        this.message = 'Введите данные'
      }
    })
    this.auth.error$.next("Test")
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

    this.auth.login(user).subscribe({
      next: (req) => {
        this.form.reset()
        this.router.navigate(['', '/']).then(r => '/')
        this.submitted = false
      },
      error: (response: HttpErrorResponse) => {
        let apiError: ApiError
        this.apiError = response.error
        if (this.apiError != undefined) {
          this.message = this.apiError.messages[0]
        }
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
