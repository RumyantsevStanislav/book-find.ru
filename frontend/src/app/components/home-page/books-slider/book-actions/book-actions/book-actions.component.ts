import {Component, Input, OnInit} from '@angular/core';
import {Status} from "../../../../../models/PersonalBook";
import {Book} from "../../../../../models/Book";
import {PersonalBooksService} from "../../../../../services/personal-books/personal-books.service";
import {UsersService} from "../../../../../services/users-service/users.service";
import {ModalService} from "../../../../../services/modal/modal.service";

@Component({
  selector: 'app-book-actions',
  templateUrl: './book-actions.component.html',
  styleUrls: ['./book-actions.component.scss']
})
export class BookActionsComponent implements OnInit {

  @Input() book: Book | undefined
  isVisible = false;
  status = Status;

  constructor(private personalBooksService: PersonalBooksService,
              private usersService: UsersService,
              private modalService: ModalService) {
  }

  ngOnInit(): void {
  }

  addToLibrary(isbn: string, status: Status) {
    if (!this.usersService.isAuthenticated()) {
      this.modalService.open()
      // setTimeout(() => this.infoPopup = false, 1000);
    } else {
      this.personalBooksService.addToLibrary(isbn, status)
    }
  }

  toggleDisplay() {
    this.isVisible = !this.isVisible
  }
}
