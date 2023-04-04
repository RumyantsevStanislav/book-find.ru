import {Component, OnInit} from '@angular/core';
import {HttpHeaders, HttpParams} from "@angular/common/http";
import {FormControl, FormGroup} from "@angular/forms";
import {Page} from "../../../models/Page";
import {Book} from "../../../models/Book";
import {BookService} from "../../../services/books/books.service";

@Component({
  selector: 'app-search-page',
  templateUrl: './search-page.component.html',
  styleUrls: ['./search-page.component.css']
})
export class SearchPageComponent implements OnInit {

  searchForm: FormGroup = new FormGroup({size: new FormControl('')});
  page = 1;
  count = 0;
  pageSize = 10;
  pageSizes = [3, 6, 9];

  booksPage: Page<Book> | undefined;
  loading = false;

  sizes = [{id: "1", value: 1}, {id: "2", value: 2}, {id: "3", value: 3}, {id: "4", value: 4}, {id: "5", value: 5}]
  public filterParams: HttpParams = new HttpParams();

  constructor(private bookService: BookService) {
  }

  ngOnInit(): void {
    //this.getBooks(params)


    this.searchForm = new FormGroup({
      size: new FormControl("5"),
    })
    let params = new HttpParams().set("s", this.searchForm.get("size")?.value);
    //categories: string[] = ["Категория1", "Категория2"];
    //document.body.
  }

  search(): void {
    console.log(this.searchForm)
    const formData = {...this.searchForm.value}
    console.log(formData);
    // @ts-ignore
    let params = new HttpParams().set("min_price", 0).set("s", this.searchForm.get("size")?.value);
    //this.getBooks(this.filter)
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
