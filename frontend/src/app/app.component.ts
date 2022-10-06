import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
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
