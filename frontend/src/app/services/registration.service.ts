import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable()
export class RegistrationService {
  private url = 'http://localhost:8189/book-find/register/process';

  constructor(private http: HttpClient) {
  }

  registration(body: string, header: HttpHeaders) {
    return this.http.post(this.url, body, {observe: "body", headers: header});
  }
}