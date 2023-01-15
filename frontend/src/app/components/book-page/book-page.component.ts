import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from "@angular/router";
import {BookService} from "../../services/books-service/books.service";
import {BookFull} from "../../models/Book";
import {Observable, switchMap} from "rxjs";
import {PersonalBooksService} from "../../services/personal-books/personal-books.service";

@Component({
  selector: 'book-card',
  templateUrl: './book-page.component.html',
  styleUrls: ['./book-page.component.scss'],
  providers: [BookService]
})
export class BookPageComponent implements OnInit {
  bookFull$: Observable<BookFull> | undefined;

  constructor(
    private router: ActivatedRoute,
    private bookService: BookService,
    private personalBooksService: PersonalBooksService
  ) {
  }

  ngOnInit() {
    this.bookFull$ = this.router.params.pipe(switchMap((params: Params) => {
        return this.bookService.getBookByIsbn(params['isbn'])
      })
    );
  }

  addToLibrary(isbn: number) {
    this.personalBooksService.addToLibrary(isbn);
  }

}
