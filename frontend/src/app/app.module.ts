import {BrowserModule} from '@angular/platform-browser';
import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BookPageComponent} from "./components/book-page/book-page.component";
import {AppRoutingModule} from "./app-routing.module";
import {BookService} from "./services/books/books.service";
import {NavigationComponent} from './components/shared/navigation/navigation.component';
import {HomePageComponent} from './components/home-page/home-page.component';
import {BookComponent} from "./shared/book/book.component";
import {SharedModule} from "./shared/shared.module";
import {UsersService} from "./services/users-service/users.service";
import {AuthGuard} from "./services/auth.guard";
import {RegistrationFormComponent} from './components/shared/registration-form/registration-form.component';
import {TokenInterceptor} from "./services/interceptors/token.interceptor";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {MatInputModule} from "@angular/material/input";
import {MatTableModule} from "@angular/material/table";
import {MatSortModule} from "@angular/material/sort";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {NgbAlertModule, NgbPaginationModule} from '@ng-bootstrap/ng-bootstrap';
import {NgMaterialModule} from './ng-material/ng-material.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {SignModalComponent} from './components/shared/sign-modal/sign-modal.component';
import {RefDirective} from "./trash/ref.directive";
import {ClickOutsideDirective} from "./trash/clickOutside.directive";
import {HeaderComponent} from './components/shared/header/header.component';
import {SidebarComponent} from './components/shared/header/sidebar/sidebar.component';
import {LogoComponent} from './components/shared/header/logo/logo.component';
import {SearchComponent} from './components/shared/header/search/search.component';
import {PersonalComponent} from './components/shared/header/personal/personal.component';
import {AdminModule} from "./admin/admin.module";
import {NgxPaginationModule} from 'ngx-pagination';
import {BooksSliderComponent} from './components/home-page/books-slider/books-slider.component';
import {SignModalDirective} from "./directives/sign-modal/sign-modal.directive";
import {ColoryDirective} from "./trash/colory.directive";
import {AccountPageComponent} from './components/account-page/account-page.component';
import {LibraryPageComponent} from './components/library-page/library-page.component';
import {AlertComponent} from "./components/shared/alert/alert.component";
import {
  PasswordRecoveringFormComponent
} from './components/shared/password-recovering-form/password-recovering-form.component';
import {ChangePasswordComponent} from './components/shared/change-password/change-password.component';
import {SwiperDirective} from './directives/swiper/swiper.directive';
import {NgOptimizedImage} from "@angular/common";
import {SearchPageComponent} from './components/search-page/search-page/search-page.component';
import { BookActionsComponent } from './components/home-page/books-slider/book-actions/book-actions/book-actions.component';
//Check breakpoints screen size
//import {LayoutModule} from '@angular/cdk/layout';

@NgModule({
  declarations: [
    AppComponent,
    BookPageComponent,
    NavigationComponent,
    HomePageComponent,
    BookComponent,
    RegistrationFormComponent,
    SignModalComponent,
    RefDirective,
    SignModalDirective,
    ClickOutsideDirective,
    HeaderComponent,
    SidebarComponent,
    LogoComponent,
    SearchComponent,
    PersonalComponent,
    BooksSliderComponent,
    ColoryDirective,
    AccountPageComponent,
    LibraryPageComponent,
    AlertComponent,
    PasswordRecoveringFormComponent,
    ChangePasswordComponent,
    SwiperDirective,
    SearchPageComponent,
    BookActionsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    SharedModule,
    AppRoutingModule,
    ReactiveFormsModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatProgressSpinnerModule,
    NgbPaginationModule,
    NgbAlertModule,
    NgMaterialModule,
    BrowserAnimationsModule,
    AdminModule,
    NgxPaginationModule,
    NgOptimizedImage,
    //LayoutModule
  ],
  providers: [BookService, BookPageComponent, UsersService, AuthGuard, {
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi: true,
  }],
  entryComponents: [SignModalComponent],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule {
}
