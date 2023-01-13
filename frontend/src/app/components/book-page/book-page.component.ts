import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from "@angular/router";
import {BookService} from "../../services/books-service/books.service";
import {Book, BookFull} from "../../models/Book";
import {Observable, switchMap} from "rxjs";

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
    private bookService: BookService
  ) {
  }

  ngOnInit() {
    this.bookFull$ = this.router.params.pipe(switchMap((params: Params) => {
        return this.bookService.getBookByIsbn(params['isbn'])
      })
    );

  }
}
