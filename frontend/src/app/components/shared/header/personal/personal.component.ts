import {Component, OnInit, ViewContainerRef} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../../../services/auth.service";
import {SignModalComponent} from "../../sign-modal/sign-modal.component";

@Component({
  selector: 'app-personal',
  templateUrl: './personal.component.html',
  styleUrls: ['./personal.component.scss']
})
export class PersonalComponent implements OnInit {

  isToggle = false;

  constructor(private router: Router, public auth: AuthService, private viewContainerRef: ViewContainerRef) {
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

  showSignModal() {
    const signModalComponent = this.viewContainerRef.createComponent(SignModalComponent);
    signModalComponent.instance.title = 'Dynamic title'
    signModalComponent.instance.close.subscribe(() => {
      signModalComponent.destroy()
    })
  }

}
