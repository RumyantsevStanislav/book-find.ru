import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../../services/auth.service";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit {

  isToggle = false;

  constructor(private router: Router, public auth: AuthService) {
  }

  ngOnInit(): void {
  }

  toggle() {
    this.isToggle = !this.isToggle
  }

}
