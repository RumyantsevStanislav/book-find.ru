import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {delay} from "rxjs/operators";
import {PersonalBook} from "../../models/PersonalBook";

@Injectable({
  providedIn: 'root'
})
export class PersonalBooksService {

  private url = 'http://localhost:8189/book-find/api/v1/library';

  constructor(private http: HttpClient) {
  }

  addToLibrary(personalBook: PersonalBook) {
    return this.http.post<any[]>(this.url, personalBook)
      .pipe(delay(1500))
  }

}

