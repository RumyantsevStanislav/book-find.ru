import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {UsersService} from "../../../../services/users-service/users.service";
import {MatDialog} from "@angular/material/dialog";
import {ModalService} from "../../../../services/modal/modal.service";

@Component({
  selector: 'app-personal',
  templateUrl: './personal.component.html',
  styleUrls: ['./personal.component.scss'],
})
export class PersonalComponent implements OnInit {

  isVisible: boolean = false

  constructor(private router: Router, public usersService: UsersService,
              public dialog: MatDialog, private modalService: ModalService) {
  }

  ngOnInit(): void {
  }

  logout(event: Event) {
    event.preventDefault();
    this.usersService.logout();
    //this.router.navigate(['/', 'login']).then(r => '/');
    //this.router.navigate(['/', 'registration']).then(r => '/');
  }

  clickOutside() {

  }

  openDialog() {
    //this.modalService.toggle()
    this.modalService.open()
    // const dialogRef = this.dialog.open(SignModalComponent);
    // dialogRef.afterClosed().subscribe(result => {
    //   console.log(`Dialog result: ${result}`);
    // });
  }

  toggleDisplay() {
    this.isVisible = !this.isVisible
  }

}
