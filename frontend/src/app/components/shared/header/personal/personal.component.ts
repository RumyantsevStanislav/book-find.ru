import {Component, OnInit, ViewChild} from '@angular/core';
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

  isToggle = false;

  constructor(private router: Router, public usersService: UsersService,
              public dialog: MatDialog, private modalService: ModalService) {
  }

  // @ViewChild('modal', { read: ViewContainerRef })
  // entry!: ViewContainerRef;
  // sub!: Subscription;

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

  openDialog() {
    //this.modalService.toggle()
    this.modalService.open()
    // const dialogRef = this.dialog.open(SignModalComponent);
    // dialogRef.afterClosed().subscribe(result => {
    //   console.log(`Dialog result: ${result}`);
    // });
  }

}
