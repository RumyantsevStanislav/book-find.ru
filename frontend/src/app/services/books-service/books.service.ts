import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {delay} from "rxjs/operators";

import {Book} from "../../models/Book";
import {AuthService} from "../auth.service";

@Injectable({
  providedIn: 'root',
})
export class BookService {

  private url = 'http://localhost:8189/book-find/api/v1/books/dto';

  constructor(private http: HttpClient, public auth: AuthService) {
  }

  getBooks(): Observable<Book[]> {
    let token = this.auth.getToken()
    if (token == null) {
      token = ""
    }
    const headers = new HttpHeaders()
      .set('Authorization', "Bearer " + token);
    return this.http.get<Book[]>(this.url, {observe: "body", headers: headers})
      .pipe(delay(1500))
  }

  // getBooks(): Observable<any> {
  //   return this.http.get<Book[]>(`${this.url}`);
  // }
  // addBook(book: Object): Observable<Object> {
  //   return this.http.post(`${this.url}`, book);
  // }
  // deleteBook(id: number): Observable<any> {
  //   return this.http.delete(`${this.url}/${id}`, {responseType: 'text'});
  // }
}
