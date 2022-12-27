import {Component, OnInit} from '@angular/core';
import {Book} from "../../models/Book";
import {Router} from "@angular/router";
import {BookService} from "../../services/books-service/books.service";
import {PersonalBooksService} from "../../services/personal-books/personal-books.service";
import {PersonalBook} from "../../models/PersonalBook";
import {HttpHeaders, HttpParams} from "@angular/common/http";
import {Page} from "../../models/Page";
import {FormControl, FormGroup} from "@angular/forms";
import {Filter, singleParamName} from "../../models/Filter";

@Component({
  selector: 'app-home',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {
  searchForm: FormGroup = new FormGroup({size: new FormControl('')});

  filters: Filter[] | undefined;
  page = 1;
  count = 0;
  pageSize = 5;
  pageSizes = [3, 6, 9];

  sizes = [{id: "1", value: 1}, {id: "2", value: 2}, {id: "3", value: 3}, {id: "4", value: 4}, {id: "5", value: 5}]

  public personalBook: PersonalBook = new PersonalBook(0, '', 0, '');
  booksPage: Page<Book> | undefined;
  loading = false;
  public filterParams: HttpParams = new HttpParams();

  constructor(private router: Router, private bookService: BookService, private personalBooksService: PersonalBooksService) {
  }

  ngOnInit(): void {
    let filterBest = new class implements Filter {
      singleParams: Map<singleParamName, string> = new Map<singleParamName, string>()
        .set(singleParamName.MIN_ESTIMATION, String(5))
        .set(singleParamName.PAGE_SIZE, String(5));
      //categories: string[] = ["Категория1", "Категория2"];
      header: string = "Высокий рейтинг";
      showAll: string = "Смотреть все"
    }
    let filterNew = new class implements Filter {
      singleParams: Map<singleParamName, string> = new Map<singleParamName, string>()
        .set(singleParamName.MIN_RELEASE_DATE, String(2021))
        .set(singleParamName.PAGE_SIZE, String(5));
      //categories: string[] = ["Категория1", "Категория2"];
      header: string = "Новинки";
      showAll: string = "Все новинки"
    }
    this.filters = [filterBest, filterNew]

    this.searchForm = new FormGroup({
      size: new FormControl("5"),
    })
    let params = new HttpParams().set("s", this.searchForm.get("size")?.value);
    //this.getBooks(params)
  }

  search(): void {
    console.log(this.searchForm)
    const formData = {...this.searchForm.value}
    console.log(formData);
    let params = new HttpParams().set("min_price", 0).set("s", this.searchForm.get("size")?.value);
    this.getBooks(params)
  }

  /**
   *
   * @param params
   */
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
}
