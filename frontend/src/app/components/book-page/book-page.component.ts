import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from "@angular/router";
import {BookService} from "../../services/books/books.service";
import {BookFull, BookPk, Review} from "../../models/Book";
import {Observable, switchMap} from "rxjs";
import {Status} from "../../models/PersonalBook";

@Component({
  selector: 'book-card',
  templateUrl: './book-page.component.html',
  styleUrls: ['./book-page.component.scss'],
  providers: [BookService]
})
export class BookPageComponent implements OnInit {
  bookFull$: Observable<BookFull> | undefined;
  status = Status;

  constructor(
    private router: ActivatedRoute,
    private bookService: BookService
  ) {
  }

  ngOnInit() {
    this.getBookFull()
  }

  private getBookFull() {
    this.bookFull$ = this.router.params.pipe(switchMap((params: Params) => {
        return this.bookService.getBookByIsbn(params['isbn'])
      })
    );
  }
}
