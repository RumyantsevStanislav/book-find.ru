import {NgModule} from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {BookPageComponent} from "./components/book-page/book-page.component";
import {NavigationComponent} from "./components/shared/navigation/navigation.component";
import {HomePageComponent} from "./components/home-page/home-page.component";
import {LoginFormComponent} from "./components/shared/login-form/login-form.component";
import {RegistrationFormComponent} from "./components/shared/registration-form/registration-form.component";
import {SignModalComponent} from "./components/shared/sign-modal/sign-modal.component";

const routes: Routes = [
  {path: '', redirectTo: '/', pathMatch: 'full'},
  {path: '', component: HomePageComponent},
  {path: 'login', component: SignModalComponent},
  {path: 'registration', component: RegistrationFormComponent},
  {path: 'book/:id', component: BookPageComponent},
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
