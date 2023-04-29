import {Component, OnInit} from '@angular/core';
import {HttpParams} from "@angular/common/http";
import {FormControl, FormGroup} from "@angular/forms";
import {Page} from "../../models/Page";
import {Book} from "../../models/Book";
import {BookService} from "../../services/books/books.service";
import {ActivatedRoute} from "@angular/router";
import {Filter, multiParamName, singleParamName} from "../../models/Filter";

@Component({
  selector: 'app-search-page',
  templateUrl: './search-page.component.html',
  styleUrls: ['./search-page.component.scss']
})
export class SearchPageComponent implements OnInit {

  searchForm: FormGroup = new FormGroup({size: new FormControl('')});
  //page = 1;
  count = 0;
  pageSize = 10;
  pageSizes = [3, 6, 9];

  booksPage: Page<Book> | undefined;
  filter: Filter
  loading = false;

  sizes = [{id: "1", value: 1}, {id: "2", value: 2}, {id: "3", value: 3}, {id: "4", value: 4}, {id: "5", value: 5}]

  constructor(private bookService: BookService,
              private activatedRoute: ActivatedRoute,) {
    this.filter = new class implements Filter {
      multiParams = new Map<multiParamName, string[]>()
      singleParams = new Map<singleParamName, string>()
    }
  }

  ngOnInit(): void {
    //this.getBooks(params)
    this.searchForm = new FormGroup({
      size: new FormControl("5"),
    })
    let params = new HttpParams().set("s", this.searchForm.get("size")?.value);
    //categories: string[] = ["Категория1", "Категория2"];
    //document.body.

    this.activatedRoute.queryParams.subscribe(params => {
      let queryText = params['search']
      if (!!queryText) {
        this.filter.singleParams?.set(singleParamName.SEARCH, queryText)
          .set(singleParamName.PAGE_SIZE, String(15));
        console.log(this.filter)
        this.getBooks(this.filter)
      }
      // else {
      //   this.alertService.danger("Неправильная ссылка для изменения пароля.")
      // }
    })
  }

  search($event: number): void {
    // const formData = {...this.searchForm.value}
    // console.log(formData);
    // @ts-ignore
    //let params = new HttpParams().set("min_price", 0).set("s", this.searchForm.get("size")?.value);
    this.filter?.singleParams?.set(singleParamName.PAGE_NUMBER, $event.valueOf() - 1)
    this.getBooks(this.filter)

  }

  /**
   *
   * @param filter
   */
  getBooks(filter?: Filter): void {
    this.loading = true;
    this.bookService.getBooks(filter).subscribe(booksPage => {
      console.log('Response', booksPage)
      this.booksPage = booksPage;
      console.log(booksPage.pageable.pageNumber)
      this.loading = false;
    })
  }

}
