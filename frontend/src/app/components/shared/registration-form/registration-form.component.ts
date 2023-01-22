import {Component, OnInit} from '@angular/core';
import {SystemUser} from "../../../models/User";
import {HttpErrorResponse, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {phoneOrEmailValidator} from "../../PhoneOrEmailValidator";
import {ConfirmedValidator} from "../../ConfirmedValidator";
import {ApiError, ApiMessage} from "../../../models/Response";
import {Page} from "../../../models/Page";
import {Book} from "../../../models/Book";
import {UsersService} from "../../../services/users-service/users.service";

@Component({
  selector: 'app-registration',
  templateUrl: './registration-form.component.html',
  styleUrls: ['./registration-form.component.scss']
})
export class RegistrationFormComponent implements OnInit {

  visiblePassword = 'password';
  form: FormGroup
  submitted = false
  passwordPattern = new RegExp('^.*(?=.*\\d)(?=.*[a-zа-яё])(?=.*[A-ZА-ЯЁ]).*$')
  apiMessage: ApiMessage | undefined;
  apiError: ApiError | null | undefined;

  constructor(private usersService: UsersService, private router: Router) {
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
            Validators.pattern(this.passwordPattern)
          ]),
          matchingPassword: new FormControl(null, [
            Validators.required
          ])
        }, {validators: ConfirmedValidator('password', 'matchingPassword')}
      )
    })
  }

  ngOnInit(): void {
  }

  changeVisiblePassword() {
    if (this.visiblePassword == 'password')
      this.visiblePassword = 'text';
    else
      this.visiblePassword = 'password';
  }

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
    const headers = new HttpHeaders().set('Content-Type', 'application/json')
    return this.usersService.registration(systemUser, headers).subscribe({
      next: (req) => {
        this.apiMessage = req;
        console.log('Next ' + this.apiMessage.message)
        this.submitted = false
      },
      error: (req: HttpErrorResponse) => {
        //this.apiError = req.;
        console.log('Error ' + req)
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
