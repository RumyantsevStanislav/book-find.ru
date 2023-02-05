import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {delay} from "rxjs/operators";
import {map} from 'rxjs/operators';
import {Book, BookFull} from "../../models/Book";
import {UsersService} from "../users-service/users.service";
import {Page} from "../../models/Page";
import {environment} from "../../environments/enviripnment.dev";

@Injectable({
  providedIn: 'root',
})
export class BookService {

  private readonly booksURL = environment.serverUrl + environment.booksUrl;

  constructor(private http: HttpClient, public usersService: UsersService) {
  }

  getBooks(headers: HttpHeaders, params?: HttpParams): Observable<Page<Book>> {
    return this.http.get<Page<Book>>(this.booksURL, {headers: headers, params: params, observe: "body"})
      .pipe(/*delay(1500)*/)
  }

  //todo is it a good way to get one book?
  getBookByIsbn(isbn: number): Observable<BookFull> {
    return this.http.get<BookFull>(this.booksURL + `/${isbn}`)
      .pipe(map((bookFull: BookFull) => {
        return {
          ...bookFull,
          // cast response fields to yours
          // someField: response.someField,
          // date: new Date(book.date)
        }
      }))
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
