import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BookPageComponent} from "./components/book-page/book-page.component";
import {AppRoutingModule} from "./app-routing.module";
import {BookService} from "./services/books-service/books.service";
import {MainComponent} from './shared/main/main.component';
import {HomePageComponent} from './components/home-page/home-page.component';
import {BookComponent} from "./shared/book/book.component";
import {SharedModule} from "./shared/shared.module";
import {AuthService} from "./services/auth.service";
import {AuthGuard} from "./services/auth.guard";
import { RegistrationComponent } from './components/registration/registration.component';
import {RegistrationService} from "./services/registration.service";

@NgModule({
  declarations: [
    AppComponent,
    BookPageComponent,
    MainComponent,
    HomePageComponent,
    BookComponent,
    RegistrationComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    SharedModule,
    AppRoutingModule,
    ReactiveFormsModule,
  ],
  providers: [BookService, BookPageComponent, AuthService, AuthGuard, RegistrationService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
