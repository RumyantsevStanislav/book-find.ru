import {Component, OnInit} from '@angular/core';
import {Book} from "../../models/Book";
import {Router} from "@angular/router";
import {BookService} from "../../services/books-service/books.service";
import {PersonalBooksService} from "../../services/personal-books/personal-books.service";
import {PersonalBook} from "../../models/PersonalBook";

@Component({
  selector: 'app-home',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {
  public personalBook: PersonalBook = new PersonalBook(0, '', 0, '');

  books: Book[] = [];
  loading = false;

  constructor(private router: Router, private bookService: BookService, private personalBooksService: PersonalBooksService) {
  }

  ngOnInit(): void {
    this.loading = true;
    this.bookService.getBooks().subscribe(books => {
      console.log('Response', books)
      this.books = books;
      this.loading = false;
      // this.router.events.subscribe(value => {
      //   console.log('Response', value);
      // });
    })
  }

  addToLibrary(isbn: number) {
    this.personalBook.status = "Прочитано"
    this.personalBook.isbn = isbn
    this.personalBooksService.addToLibrary(this.personalBook).subscribe((req) => {
      console.log(req)
      this.router.navigate(['', '/']).then(r => '/')
    }, error => console.log(error));
  }

}
