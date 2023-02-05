import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse, HttpHeaders, HttpParams} from "@angular/common/http";
import {PersonalBooksService} from "../../services/personal-books/personal-books.service";
import {PersonalBook, PersonalBookImpl} from "../../models/PersonalBook";

@Component({
  selector: 'app-library-page',
  templateUrl: './library-page.component.html',
  styleUrls: ['./library-page.component.scss']
})
export class LibraryPageComponent implements OnInit {

  loading = false;
  personalBooks: PersonalBookImpl[] | undefined

  constructor(
    private personalBooksService: PersonalBooksService) {
  }

  ngOnInit(): void {
    this.getLibrary()
  }

  getLibrary(): void {
    this.loading = true;
    this.personalBooksService.getLibrary().subscribe({
      next: (req) => {
        this.personalBooks = req
        console.log("SUCCESS")
      },
      error: (req: HttpErrorResponse) => {
        //this.apiError = req.;
        console.log('Error ' + req)
        this.loading = false
      },
      complete: () => {
        console.log('Complete')
        // this.router.navigate(['', '/']).then(r => '/')
        this.loading = false
      }
    });
    // this.router.events.subscribe(value => {
    //   console.log('Response', value);
    // });
  }
}
