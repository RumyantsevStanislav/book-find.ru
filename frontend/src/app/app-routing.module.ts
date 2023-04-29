import {NgModule} from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {BookPageComponent} from "./components/book-page/book-page.component";
import {HomePageComponent} from "./components/home-page/home-page.component";
import {AccountPageComponent} from "./components/account-page/account-page.component";
import {AuthGuard} from "./services/auth.guard";
import {LibraryPageComponent} from "./components/library-page/library-page.component";
import {SearchPageComponent} from "./components/search-page/search-page.component";

const routes: Routes = [
  {path: '', redirectTo: '/', pathMatch: 'full'},
  {path: '', component: HomePageComponent},
  {path: 'book/:isbn', component: BookPageComponent},
  {path: 'search', component: SearchPageComponent},
  {path: 'changePassword', component: HomePageComponent},
  {
    path: 'account', canActivate: [AuthGuard], children: [
      //Если попадаем на ('') - не знаем куда идти, поэтому редиректим.
      {path: '', redirectTo: '/account', pathMatch: 'full'},
      {path: '', component: AccountPageComponent},
      {path: 'library', component: LibraryPageComponent},
    ]
  },
  {path: 'admin', loadChildren: () => import ("./admin/admin.module").then(m => m.AdminModule)}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    preloadingStrategy: PreloadAllModules
  })],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
