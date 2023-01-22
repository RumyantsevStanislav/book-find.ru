import {Component, OnInit} from '@angular/core';
import {UsersService} from "../../services/users-service/users.service";
import {RegisteredUser} from "../../models/User";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-account-page',
  templateUrl: './account-page.component.html',
  styleUrls: ['./account-page.component.scss']
})
export class AccountPageComponent implements OnInit {

  user: RegisteredUser | undefined
  submitted: boolean | undefined

  constructor(private usersService: UsersService) {
  }

  ngOnInit(): void {
    this.getAccount()
  }

  private getAccount() {
    this.usersService.getAccount().subscribe({
      next: (req) => {
        this.user = req;
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
