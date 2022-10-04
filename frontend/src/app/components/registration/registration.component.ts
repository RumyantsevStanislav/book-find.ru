import {Component, OnInit} from '@angular/core';
import {SystemUser} from "../../models/User";
import {HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {RegistrationService} from "../../services/registration.service";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  public systemUser: SystemUser = new SystemUser('', '', '', '', '', '', '');
  visiblePassword = 'password';


  constructor(private registrationService: RegistrationService, private router: Router) {
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
    console.log(this.systemUser);
    const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
    let body = 'phone=' + this.systemUser.userName +
      '&password=' + this.systemUser.password +
      '&matchingPassword=' + this.systemUser.matchingPassword +
      '&firstName=' + this.systemUser.firstName +
      '&lastName=' + this.systemUser.lastName +
      '&email=' + this.systemUser.email;
    return this.registrationService.registration(body, headers).subscribe((req) => {
      console.log(req)
      this.router.navigate(['', '/']).then(r => '/')
    }, error => console.log(error));
  }
}
