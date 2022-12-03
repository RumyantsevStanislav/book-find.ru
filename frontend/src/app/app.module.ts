import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BookPageComponent} from "./components/book-page/book-page.component";
import {AppRoutingModule} from "./app-routing.module";
import {BookService} from "./services/books-service/books.service";
import {NavigationComponent} from './components/shared/navigation/navigation.component';
import {HomePageComponent} from './components/home-page/home-page.component';
import {BookComponent} from "./shared/book/book.component";
import {SharedModule} from "./shared/shared.module";
import {AuthService} from "./services/auth.service";
import {AuthGuard} from "./services/auth.guard";
import {RegistrationFormComponent} from './components/shared/registration-form/registration-form.component';
import {RegistrationService} from "./services/registration.service";
import {TokenInterceptor} from "./services/interceptors/token.interceptor";
import {ErrorInterceptor} from "./services/interceptors/error.interceptor";
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
import {RefDirective} from "./ref.directive";
import {ClickOutsideDirective} from "./clickOutside.directive";
import {HeaderComponent} from './components/shared/header/header.component';
import {SidebarComponent} from './components/shared/header/sidebar/sidebar.component';
import {LogoComponent} from './components/shared/header/logo/logo.component';
import {SearchComponent} from './components/shared/header/search/search.component';
import {PersonalComponent} from './components/shared/header/personal/personal.component';
import {AdminModule} from "./admin/admin.module";


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
    ClickOutsideDirective,
    HeaderComponent,
    SidebarComponent,
    LogoComponent,
    SearchComponent,
    PersonalComponent
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
  ],
  providers: [BookService, BookPageComponent, AuthService, AuthGuard, RegistrationService, {
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi: true,
  }],
  entryComponents: [SignModalComponent],
  bootstrap: [AppComponent]
})
export class AppModule {
}
