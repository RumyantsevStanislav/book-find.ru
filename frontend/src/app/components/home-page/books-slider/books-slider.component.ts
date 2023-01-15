import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {PersonalBook} from "../../../models/PersonalBook";
import {Page} from "../../../models/Page";
import {Book} from "../../../models/Book";
import {HttpHeaders, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {BookService} from "../../../services/books-service/books.service";
import {PersonalBooksService} from "../../../services/personal-books/personal-books.service";
import {Filter} from "../../../models/Filter";
import {AuthService} from "../../../services/auth.service";
import {MatDialog} from "@angular/material/dialog";
import {SignModalDirective} from "../../../sign-modal.directive";

@Component({
  selector: 'app-books-slider',
  templateUrl: './books-slider.component.html',
  styleUrls: ['./books-slider.component.scss']
})
export class BooksSliderComponent implements OnInit {

  @Input() filter: Filter | undefined
  @ViewChild(SignModalDirective, {static: true}) signModal!: SignModalDirective;

  infoPopup = false

  searchForm: FormGroup = new FormGroup({size: new FormControl('')});
  page = 1;
  count = 0;
  pageSize = 5;
  pageSizes = [3, 6, 9];

  sizes = [{id: "1", value: 1}, {id: "2", value: 2}, {id: "3", value: 3}, {id: "4", value: 4}, {id: "5", value: 5}]

  public personalBook: PersonalBook = new PersonalBook(0, '', 0, '');
  booksPage: Page<Book> | undefined;
  loading = false;
  public filterParams: HttpParams = new HttpParams();

  constructor(private router: Router,
              private bookService: BookService,
              private personalBooksService: PersonalBooksService,
              private authService: AuthService,
              public dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.getBooks(this.filter)
  }

  search(): void {
    console.log(this.searchForm)
    const formData = {...this.searchForm.value}
    console.log(formData);
    // @ts-ignore
    let params = new HttpParams().set("min_price", 0).set("s", this.searchForm.get("size")?.value);
    this.getBooks(this.filter)
  }

  /**
   *
   * @param filter
   */
  getBooks(filter?: Filter): void {
    this.loading = true;
    const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
    let params = new HttpParams();
    if (filter && filter.singleParams) {
      filter.singleParams.forEach((value, key) => params = params.set(key, value)
      )
    }
    if (filter && filter.multiParams) {
      filter.multiParams.forEach((values, key) => {
          values.forEach(value => params = params.append(key, value))
        }
      )
    }
    params = params.set("s", 5)
    this.bookService.getBooks(headers, params).subscribe(booksPage => {
      console.log('Response', booksPage)
      this.booksPage = booksPage;
      this.loading = false;
      // this.router.events.subscribe(value => {
      //   console.log('Response', value);
      // });
    })
  }

  addToLibrary(isbn: number) {
    this.personalBooksService.addToLibrary(isbn)
  }
}
