import {Component, OnInit} from '@angular/core';
import {Book} from "../../models/Book";
import {ActivatedRoute, Router} from "@angular/router";
import {BookService} from "../../services/books/books.service";
import {PersonalBook} from "../../models/PersonalBook";
import {HttpHeaders, HttpParams} from "@angular/common/http";
import {Page} from "../../models/Page";
import {FormControl, FormGroup} from "@angular/forms";
import {Filter, singleParamName} from "../../models/Filter";
import {ModalService} from "../../services/modal/modal.service";
import {UsersService} from "../../services/users-service/users.service";
import {AlertService} from "../../services/alert/alert.service";

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

  //url: string | undefined

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private bookService: BookService,
              private modalService: ModalService,
              private usersService: UsersService,
              private alertService: AlertService) {
    // this.activatedRoute.url.subscribe(url => {
    //   console.log(url[0]?.path)
    //   this.url = url[0]?.path
    // })
  }

  ngOnInit(): void {
    //console.log(this.router.url)
    let currentUrl = this.router.parseUrl(this.router.url).root.children['primary']?.segments.map(it => it.path).join('/')
    //console.log(currentUrl)
    if (currentUrl === "changePassword") {
      this.activatedRoute.queryParams.subscribe(params => {
        let token = params['token']
        if (!!token) {
          this.usersService.changePassword(token).subscribe({
            next: (req) => {
              this.modalService.open();
            },
            error: (messages: string) => {
              this.alertService.danger(messages)
            },
            complete: () => {
            }
          })
        } else {
          this.alertService.danger("Неправильная ссылка для изменения пароля.")
        }
      })
    }
    //document.body.
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
