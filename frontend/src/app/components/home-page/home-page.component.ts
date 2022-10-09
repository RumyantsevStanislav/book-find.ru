import {Component, OnInit} from '@angular/core';
import {Book} from "../../models/Book";
import {Router} from "@angular/router";
import {BookService} from "../../services/books-service/books.service";
import {PersonalBooksService} from "../../services/personal-books/personal-books.service";
import {PersonalBook} from "../../models/PersonalBook";
import {HttpHeaders, HttpParams} from "@angular/common/http";
import {Page} from "../../models/Page";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-home',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {
  searchForm: FormGroup = new FormGroup({size: new FormControl('')});

  sizes = [{id: "1", value: 1}, {id: "2", value: 2}]

  public personalBook: PersonalBook = new PersonalBook(0, '', 0, '');
  booksPage: Page<Book> | undefined;
  loading = false;
  public filterParams: HttpParams = new HttpParams();

  constructor(private router: Router, private bookService: BookService, private personalBooksService: PersonalBooksService) {
  }

  ngOnInit(): void {
    this.searchForm = new FormGroup({
      size: new FormControl("2"),
    })
    let params = new HttpParams().set("s", this.searchForm.get("size")?.value);
    this.getBooks(params)
  }

  search(): void {
    console.log(this.searchForm)
    const formData = {...this.searchForm.value}
    console.log(formData);
    let params = new HttpParams().set("min_price", 0).set("s", this.searchForm.get("size")?.value);
    this.getBooks(params)
  }

  getBooks(params?: HttpParams): void {
    this.loading = true;
    const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
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
    this.personalBook.status = "Прочитано"
    this.personalBook.isbn = isbn
    this.personalBooksService.addToLibrary(this.personalBook).subscribe((req) => {
      console.log(req)
      this.router.navigate(['', '/']).then(r => '/')
    }, error => console.log(error));
  }

}
