import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {UsersService} from "../../services/users-service/users.service";
import {RegisteredUser} from "../../models/User";
import {HttpErrorResponse} from "@angular/common/http";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {regExpPatterns} from "../../validators/RegExpPatterns";
import {ApiError} from "../../models/Response";
import {ConfirmedValidator} from "../../validators/ConfirmedValidator";

@Component({
  selector: 'app-account-page',
  templateUrl: './account-page.component.html',
  styleUrls: ['./account-page.component.scss']
})
export class AccountPageComponent implements OnInit {

  user: RegisteredUser | undefined
  submitted: boolean | undefined
  personalForm: FormGroup
  phoneForm: FormGroup
  emailForm: FormGroup
  passwordForm: FormGroup
  @Output() isSuccess = new EventEmitter<void>()
  message: string | undefined
  apiError: ApiError | undefined
  showChangePasswordForm = false

  constructor(private usersService: UsersService, private router: Router, private formBuilder: FormBuilder) {
    if (!usersService.isAuthenticated()) {
      this.router.navigate(['/login', '']).then(r => '/')
    }
    this.personalForm = new FormGroup({
      //A control is dirty if the user has changed the value in the UI.
      firstName: new FormControl(this.user?.firstName, [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(20)
      ]),
      lastName: new FormControl(this.user?.lastName, [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(20)
      ])
    })
    this.phoneForm = new FormGroup({
      phone: new FormControl(this.user?.phone, [
        Validators.required,
        Validators.pattern(regExpPatterns.phonePattern)
      ]),
    })
    this.emailForm = new FormGroup({
      email: new FormControl(this.user?.email, [
        Validators.required,
        Validators.pattern(regExpPatterns.emailPattern)
      ])
    })
    this.passwordForm = new FormGroup({
        oldPassword: new FormControl(null, [
          Validators.required,
          Validators.minLength(8),
          Validators.maxLength(20),
          Validators.pattern(regExpPatterns.passwordPattern)
        ]),
        newPassword: new FormControl(null, [
          Validators.required,
          Validators.minLength(8),
          Validators.maxLength(20),
          Validators.pattern(regExpPatterns.passwordPattern)
        ]),
        matchingPassword: new FormControl(null, [
          Validators.required
        ])
      }, {validators: ConfirmedValidator('newPassword', 'matchingPassword')}
    )
  }

  ngOnInit(): void {
    this.getAccount()
  }

  private getAccount() {
    this.usersService.getAccount().subscribe({
      next: (req) => {
        this.user = req;
        this.personalForm.patchValue({
          firstName: this.user.firstName,
          lastName: this.user.lastName
        })
        this.phoneForm.patchValue({
          phone: this.user.phone,
        })
        this.emailForm.patchValue({
          email: this.user.email,
        })
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

  toggleShowChangePasswordForm() {
    this.passwordForm.reset()
    this.showChangePasswordForm = !this.showChangePasswordForm;
  }

  submit() {
    if (this.personalForm.invalid) {
      console.log("return")
      return
    }
    this.submitted = true
    const registeredUser: RegisteredUser = {
      firstName: this.personalForm.value.firstName,
      lastName: this.personalForm.value.lastName,
    }

    this.usersService.updateUser(registeredUser).subscribe({
      next: (req) => {
        //this.form.reset()
        this.isSuccess.emit()
      },
      error: (response: HttpErrorResponse) => {
        let apiError: ApiError
        this.apiError = response.error
        if (this.apiError != undefined) {
          this.message = this.apiError.messages[0]
        }
        //this.personalForm.get('password')?.reset()
      },
      complete: () => {
        console.log('Complete')
        // this.router.navigate(['', '/']).then(r => '/')
        this.submitted = false

      }
    });
  }

  //TODO use twilio on the server to confirm phone by sms
  submitPhone() {

  }

  //TODO implement om the server
  submitEmail() {

  }

  submitPassword() {
    if (this.passwordForm.invalid) {
      console.log("return")
      return
    }
    let oldPassword = this.passwordForm.value.oldPassword
    let newPassword = this.passwordForm.value.newPassword
    this.usersService.updatePassword(newPassword, oldPassword).subscribe({
      next: (req) => {
        console.log("SUCCESS")
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
