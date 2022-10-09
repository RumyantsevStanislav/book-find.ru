import {NgModule} from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {BookPageComponent} from "./components/book-page/book-page.component";
import {MainComponent} from "./shared/main/main.component";
import {HomePageComponent} from "./components/home-page/home-page.component";
import {LoginPageComponent} from "./components/login-page/login-page.component";
import {RegistrationComponent} from "./components/registration/registration.component";

const routes: Routes = [
  {path: '', redirectTo: '/', pathMatch: 'full'},
  {path: '', component: HomePageComponent},
  {path: 'login', component: LoginPageComponent},
  {path: 'registration', component: RegistrationComponent},
  {path: 'book/:id', component: BookPageComponent},
  // {
  //   path: '', component: AppComponent, children: [
  //     //Если попадаем на главную('') - не знаем куда идти, поэтому редиректим.
  //     {path: '', redirectTo: '/', pathMatch: 'full'},
  //     {path: '', component: HomePageComponent},
  //     {path: 'login', component: LoginPageComponent},
  //     {path: 'registration', component: RegistrationComponent},
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
