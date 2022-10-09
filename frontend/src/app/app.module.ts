import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BookPageComponent} from "./components/book-page/book-page.component";
import {AppRoutingModule} from "./app-routing.module";
import {BookService} from "./services/books-service/books.service";
import {MainMenuComponent} from './shared/main-menu/main-menu.component';
import {HomePageComponent} from './components/home-page/home-page.component';
import {BookComponent} from "./shared/book/book.component";
import {SharedModule} from "./shared/shared.module";
import {AuthService} from "./services/auth.service";
import {AuthGuard} from "./services/auth.guard";
import {RegistrationComponent} from './components/registration/registration.component';
import {RegistrationService} from "./services/registration.service";
import {TokenInterceptorService} from "./services/interceptors/token-interceptor.service";
import {HTTP_INTERCEPTORS} from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent,
    BookPageComponent,
    MainMenuComponent,
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
  providers: [BookService, BookPageComponent, AuthService, AuthGuard, RegistrationService, {
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptorService,
    multi: true,
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
