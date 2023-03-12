import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {MatBasicComponent} from './ng-material/mat-basic/mat-basic.component';
import {ViewContainerRef} from '@angular/core';
import {SignModalComponent} from "./components/shared/sign-modal/sign-modal.component";
import {RefDirective} from "./trash/ref.directive";

import {ModalService} from "./services/modal/modal.service";
import {SignModalDirective} from "./directives/sign-modal/sign-modal.directive";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent implements OnInit {

  //@ViewChild(RefDirective, {static: false}) refDir: RefDirective | undefined

  @ViewChild(SignModalDirective, {static: true}) signModal!: SignModalDirective;


  constructor(private viewContainerRef: ViewContainerRef, public dialog: MatDialog,
              private modalService: ModalService) {
  }

  // var cors = require("cors")
  // router.use(cors())
  ngOnInit() {

  }

  openDialog() {
    const dialogRef = this.dialog.open(MatBasicComponent);
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }

//   addBook(): void {
//     this.router.navigate(['add-book'])
//       .then((e) => {
//         if (e) {
//           console.log("Navigation is successful!");
//         } else {
//           console.log("Navigation has failed!");
//         }
//       });
//   };
}
