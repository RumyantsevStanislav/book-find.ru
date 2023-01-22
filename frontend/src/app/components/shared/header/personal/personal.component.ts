import {Component, OnInit, ViewChild, ViewContainerRef} from '@angular/core';
import {Router} from "@angular/router";
import {UsersService} from "../../../../services/users-service/users.service";
import {SignModalDirective} from "../../../../sign-modal.directive";

@Component({
  selector: 'app-personal',
  templateUrl: './personal.component.html',
  styleUrls: ['./personal.component.scss'],
})
export class PersonalComponent implements OnInit {

  isToggle = false;

  @ViewChild(SignModalDirective, {static: true}) signModal!: SignModalDirective;

  constructor(private router: Router, public usersService: UsersService) {
  }

  ngOnInit(): void {
  }

  logout(event: Event) {
    event.preventDefault();
    this.usersService.logout();
    this.router.navigate(['/', 'login']).then(r => '/');
    this.router.navigate(['/', 'registration']).then(r => '/');
  }

  toggle() {
    this.isToggle = !this.isToggle
  }

  clickOutside() {
    this.isToggle = false;
  }

}
