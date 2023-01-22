import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {UsersService} from "../../../services/users-service/users.service";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit {

  isToggle = false;

  constructor(private router: Router, public usersService: UsersService) {
  }

  ngOnInit(): void {
  }

  toggle() {
    this.isToggle = !this.isToggle
  }

}
