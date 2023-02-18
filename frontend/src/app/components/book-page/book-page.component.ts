import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from "@angular/router";
import {BookService} from "../../services/books/books.service";
import {BookFull, BookPk, Review} from "../../models/Book";
import {Observable, switchMap} from "rxjs";
import {PersonalBooksService} from "../../services/personal-books/personal-books.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UsersService} from "../../services/users-service/users.service";
import {ReviewsService} from "../../services/reviews/reviews.service";

@Component({
  selector: 'book-card',
  templateUrl: './book-page.component.html',
  styleUrls: ['./book-page.component.scss'],
  providers: [BookService]
})
export class BookPageComponent implements OnInit {
  bookFull$: Observable<BookFull> | undefined;
  isShowReviewForm = false;
  buttonText = "Написать рецензию"
  // @ts-ignore
  form: FormGroup;

  constructor(
    private router: ActivatedRoute,
    private bookService: BookService,
    private personalBooksService: PersonalBooksService,
    private usersService: UsersService,
    private reviewsService: ReviewsService
  ) {
  }

  ngOnInit() {
    this.getBookFull()
    this.form = new FormGroup({
      title: new FormControl(null, Validators.required),
      text: new FormControl(null, Validators.required),
      author: new FormControl(null, Validators.required)
    })
  }

  private getBookFull() {
    this.bookFull$ = this.router.params.pipe(switchMap((params: Params) => {
        return this.bookService.getBookByIsbn(params['isbn'])
      })
    );
  }

  addToLibrary(isbn: number) {
    this.personalBooksService.addToLibrary(isbn);
  }

  toggleReviewForm() {
    this.isShowReviewForm = !this.isShowReviewForm
    this.buttonText = this.isShowReviewForm ? "Отмена" : "Написать рецензию"
  }

  saveReview(isbn: number) {
    if (this.form.invalid || !this.usersService.isAuthenticated()) {
      return
    }

    const bookPk: BookPk = {
      isbn: isbn
    }
    const review: Review = {
      //title: this.form.value.title,
      review: this.form.value.text,
      estimation: 0,
      book: bookPk
      //date: new Date()
    }

    this.reviewsService.create(review)
    //TODO update only reviews
    this.getBookFull()
    //   .subscribe(() => {
    //   this.form.reset()
    //   this.alert.success('Пост был создан')
    // })
  }

}
