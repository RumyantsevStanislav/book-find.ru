import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {AdminComponent} from './shared/components/admin/admin.component';
import {LoginPageComponent} from '../components/login-page/login-page.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AuthService} from "../services/auth.service";
import {SharedModule} from "../shared/shared.module";
import {DashboardPageComponent} from './dashboard-page/dashboard-page.component';
import {AddBookPageComponent} from './add-book-page/add-book-page.component';
import {EditBookPageComponent} from './edit-book-page/edit-book-page.component';
import {AuthGuard} from "../services/auth.guard";

@NgModule({
  declarations: [
    AdminComponent,
    LoginPageComponent,
    DashboardPageComponent,
    AddBookPageComponent,
    EditBookPageComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    RouterModule.forChild([
      {
        path: '', component: AdminComponent, children: [
          {path: '', redirectTo: '/admin/login', pathMatch: 'full'},
          //{path: 'login', component: LoginPageComponent},
          {path: 'dashboard', component: DashboardPageComponent, canActivate: [AuthGuard]},
          {path: 'create', component: AddBookPageComponent, canActivate: [AuthGuard]},
          {path: 'book/:id/edit', component: EditBookPageComponent, canActivate: [AuthGuard]},
        ]
      }
    ])],
  exports: [RouterModule],
  providers: [AuthService, AuthGuard]
})
export class AdminModule {

}
