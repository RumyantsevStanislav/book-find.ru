import {NgModule} from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {BookPageComponent} from "./components/book-page/book-page.component";
import {HomePageComponent} from "./components/home-page/home-page.component";
import {RegistrationFormComponent} from "./components/shared/registration-form/registration-form.component";
import {SignModalComponent} from "./components/shared/sign-modal/sign-modal.component";
import {AccountPageComponent} from "./components/account-page/account-page.component";
import {AuthGuard} from "./services/auth.guard";

const routes: Routes = [
  {path: '', redirectTo: '/', pathMatch: 'full'},
  {path: '', component: HomePageComponent},
  {path: 'login', component: SignModalComponent},
  {path: 'registration', component: RegistrationFormComponent},
  {path: 'book/:isbn', component: BookPageComponent},
  {
    path: 'account', component: AccountPageComponent, canActivate: [AuthGuard], children: []
  },
  // {
  //   path: '', component: AppComponent, children: [
  //     //Если попадаем на главную('') - не знаем куда идти, поэтому редиректим.
  //     {path: '', redirectTo: '/', pathMatch: 'full'},
  //     {path: '', component: HomePageComponent},
  //     {path: 'login', component: LoginFormComponent},
  //     {path: 'registration', component: RegistrationFormComponent},
  //     {path: 'book/:id', component: BookPageComponent}
  //   ]
  // },
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
