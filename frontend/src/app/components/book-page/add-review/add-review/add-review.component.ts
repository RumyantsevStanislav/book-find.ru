import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ReviewsService} from "../../../../services/reviews/reviews.service";
import {Book, BookPk, Review} from "../../../../models/Book";
import {PersonalBooksService} from "../../../../services/personal-books/personal-books.service";
import {UsersService} from "../../../../services/users-service/users.service";
import {ModalService} from "../../../../services/modal/modal.service";
import {AlertService} from "../../../../services/alert/alert.service";

@Component({
  selector: 'app-add-review',
  templateUrl: './add-review.component.html',
  styleUrls: ['./add-review.component.css']
})
export class AddReviewComponent implements OnInit {

  @Input() book: Book | undefined;

  reviewForm: FormGroup;
  isShowReviewForm = false;
  buttonText = "Написать рецензию"

  constructor(
    private reviewsService: ReviewsService,
    private personalBooksService: PersonalBooksService,
    private usersService: UsersService,
    private modalService: ModalService,
    private alertService: AlertService) {
    this.reviewForm = new FormGroup({
      title: new FormControl(null, Validators.required),
      text: new FormControl(null, Validators.required),
      author: new FormControl(null, Validators.required)
    })
  }

  ngOnInit(): void {
  }

  toggleReviewForm() {
    if (!this.usersService.isAuthenticated()) {
      this.modalService.open()
      this.alertService.warning("Чтобы оставить рецензию войдите или зарегистрируйтесь.")
      return
    }
    this.isShowReviewForm = !this.isShowReviewForm
    this.buttonText = this.isShowReviewForm ? "Отмена" : "Написать рецензию"
  }

  saveReview() {
    if (this.reviewForm.invalid || this.book == undefined) {
      this.alertService.warning("Необходимо заполнить все поля.")
      return
    }
    const bookPk: BookPk = {
      isbn: this.book.isbn
    }
    const review: Review = {
      //title: this.form.value.title,
      review: this.reviewForm.value.text,
      estimation: 0,
      book: bookPk
      //date: new Date()
    }

    this.reviewsService.create(review)
    //TODO update only reviews
    //this.getBookFull()
    //   .subscribe(() => {
    //   this.form.reset()
    //   this.alert.success('Пост был создан')
    // })
  }
}
