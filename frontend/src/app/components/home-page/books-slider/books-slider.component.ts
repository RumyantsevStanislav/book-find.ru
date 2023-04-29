import {AfterViewInit, Component, Input, OnInit} from '@angular/core';
import {Page} from "../../../models/Page";
import {Book} from "../../../models/Book";
import {HttpHeaders, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {BookService} from "../../../services/books/books.service";
import {Filter} from "../../../models/Filter";
import {register} from 'swiper/element/bundle';
import {A11y, Mousewheel, Navigation, Pagination, SwiperOptions} from "swiper";

register();

@Component({
  selector: 'app-books-slider',
  templateUrl: './books-slider.component.html',
  styleUrls: ['./books-slider.component.scss'],
  //TODO figure out
  //ViewEncapsulation.None
})
export class BooksSliderComponent implements OnInit, AfterViewInit {

  @Input() filter: Filter | undefined
  booksPage: Page<Book> | undefined;
  loading = false;

  public config: SwiperOptions = {
    modules: [Navigation, Pagination, A11y, Mousewheel],
    //autoHeight: true,
    navigation: true,
    //pagination: {clickable: true, dynamicBullets: true},
    //centeredSlides: true,
    breakpoints: {
      0: {
        slidesPerView: 1,
        spaceBetween: 5,
      },
      281: {
        slidesPerView: 2,
      },
      480: {
        //slidesPerView: "auto",
        slidesPerView: 3,
        //centeredSlides: false
      },
      840: {
        slidesPerView: 4,
      },
      1024: {
        slidesPerView: 5,
        spaceBetween: 10,
      },
      1280: {
        slidesPerView: 6,
        spaceBetween: 20,
      }
      // '@0.75': {
      //   slidesPerView: 2,
      //   spaceBetween: 20,
      // },
      // '@1.00': {
      //   slidesPerView: 3,
      //   spaceBetween: 40,
      // },
      // '@1.50': {
      //   slidesPerView: 4,
      //   spaceBetween: 50,
      // },

    }
  }

  constructor(private router: Router,
              private bookService: BookService) {
  }

  ngOnInit(): void {
    this.getBooks(this.filter)
  }

  ngAfterViewInit() {
  }

  /**
   *
   * @param filter
   */
  getBooks(filter?: Filter): void {
    this.loading = true;
    this.bookService.getBooks(filter).subscribe(booksPage => {
      this.booksPage = booksPage;
      this.loading = false;
      // this.router.events.subscribe(value => {
      //   console.log('Response', value);
      // });
    })
  }
}
