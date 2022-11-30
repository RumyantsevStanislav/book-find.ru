import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../services/auth.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {User} from "../../../models/User";

@Component({
  selector: 'app-login',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {

  form: FormGroup
  submitted = false
  message: string | undefined

  constructor(public auth: AuthService, private router: Router, private route: ActivatedRoute) {
    this.form = new FormGroup({
      username: new FormControl(null, [
        Validators.required,
        Validators.email || Validators.pattern('[- +()0-9]+')
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
  }

  submit() {
    if (this.form.invalid) {
      return
    }
    this.submitted = true
    const user: User = {
      username: this.form.value.username,
      password: this.form.value.password,
    }

    this.auth.login(user).subscribe(() => {
      this.form.reset()
      this.router.navigate(['', '/']).then(r => '/')
      this.submitted = false
    }, () => {
      this.submitted = false
    })
  }
}
