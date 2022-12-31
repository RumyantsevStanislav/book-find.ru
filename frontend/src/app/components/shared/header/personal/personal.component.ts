import {Component, OnInit, ViewChild, ViewContainerRef} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../../../services/auth.service";
import {SignModalDirective} from "../../../../sign-modal.directive";

@Component({
  selector: 'app-personal',
  templateUrl: './personal.component.html',
  styleUrls: ['./personal.component.scss'],
})
export class PersonalComponent implements OnInit {

  isToggle = false;

  @ViewChild(SignModalDirective, {static: true}) adHost!: SignModalDirective;

  constructor(private router: Router, public auth: AuthService) {
  }

  ngOnInit(): void {
  }

  logout(event: Event) {
    event.preventDefault();
    this.auth.logout();
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
