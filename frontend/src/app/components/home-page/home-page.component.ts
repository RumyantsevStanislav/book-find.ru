import {Component, OnInit} from '@angular/core';
import {Book} from "../../models/Book";
import {Router} from "@angular/router";
import {BookService} from "../../services/books-service/books.service";

@Component({
  selector: 'app-home',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  books: Book[] = [];
  loading = false;

  constructor(private router: Router, private bookService: BookService) {
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

}
