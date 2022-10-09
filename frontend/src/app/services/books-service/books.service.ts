import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {delay} from "rxjs/operators";

import {Book} from "../../models/Book";
import {AuthService} from "../auth.service";
import {Page} from "../../models/Page";

@Injectable({
  providedIn: 'root',
})
export class BookService {

  private url = 'http://localhost:8189/book-find/api/v1/books/';

  constructor(private http: HttpClient, public auth: AuthService) {
  }

  getBooks(headers: HttpHeaders, params?: HttpParams): Observable<Page<Book>> {
    return this.http.get<Page<Book>>(this.url, {headers: headers, params: params, observe: "body"})
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
  //___________________________
  // private baseUrl = 'http://localhost:8080/springboot-crud-rest/api/v1/employees';
  //
  // constructor(private http: HttpClient) { }
  //
  // getEmployee(id: number): Observable<any> {
  //   return this.http.get(`${this.baseUrl}/${id}`);
  // }
  //
  // createEmployee(employee: Object): Observable<Object> {
  //   return this.http.post(`${this.baseUrl}`, employee);
  // }
  //
  // updateEmployee(id: number, value: any): Observable<Object> {
  //   return this.http.put(`${this.baseUrl}/${id}`, value);
  // }
  //
  // deleteEmployee(id: number): Observable<any> {
  //   return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
  // }
  //
  // getEmployeesList(): Observable<any> {
  //   return this.http.get(`${this.baseUrl}`);
  // }
}
