import {Component, Input, OnInit} from '@angular/core';
// import {FormBuilder, FormGroup, Validators} from "@angular/forms";
// import {Router} from "@angular/router";
import {BookService} from "../../services/books-service/books.service";
import {Book} from "../../models/Book";

@Component({
  selector: 'book-card',
  templateUrl: './book-page.component.html',
  styleUrls: ['./book-page.component.scss'],
  providers: [BookService]
})
export class BookPageComponent implements OnInit {
  books: Book[] = [];

  //@Input() book: Book

  constructor(/*private formBuilder: FormBuilder, private router: Router, private bookService: BookService*/) {
  }

//
//   addForm: FormGroup | undefined;
//
  ngOnInit() {
    //this.addForm = this.formBuilder.group({
    //id: [],
    //title: ['', Validators.required],
    //author: ['', Validators.required]
  }

  //);
//
//   }
//observe
//   onSubmit() {
//     // @ts-ignore
//     this.bookService.addBook(this.addForm.value)
//       .subscribe(data => {
//         this.router.navigate(['list-books']);
//       });
//   }
//
// }
}
