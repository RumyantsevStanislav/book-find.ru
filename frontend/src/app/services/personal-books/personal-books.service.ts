import {Injectable, ViewChild} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {PersonalBook} from "../../models/PersonalBook";
import {UsersService} from "../users-service/users.service";
import {SignModalDirective} from "../../sign-modal.directive";

@Injectable({
  providedIn: 'root'
})
export class PersonalBooksService {

  private url = 'http://localhost:8189/book-find/api/v1/library';
  @ViewChild(SignModalDirective, {static: true}) signModal!: SignModalDirective;
  public personalBook: PersonalBook = new PersonalBook(0, '', 0, '');

  constructor(private http: HttpClient, private usersService: UsersService) {
  }

  private request(personalBook: PersonalBook) {
    return this.http.post<any[]>(this.url, personalBook)
      .pipe(/*delay(1500)*/)
  }

  addToLibrary(isbn: number) {
    if (!this.usersService.isAuthenticated()) {
      this.signModal.showSignModal()
      // this.infoPopup = true
      // setTimeout(() => this.infoPopup = false, 1000);
      //this.router.navigate(['/', 'login']).then(r => '/')
    } else {
      this.personalBook.status = "Прочитано"
      this.personalBook.isbn = isbn
      this.request(this.personalBook).subscribe((req) => {
        console.log(req)

        //this.router.navigate(['', '/']).then(r => '/')
      }, error => console.log(error));
    }
  }

}

