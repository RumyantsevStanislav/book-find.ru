import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {BookService} from "./services/books-service/books.service";
import {Book} from "./shared/interfaces";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {


  constructor() {
  }

  ngOnInit() {

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
