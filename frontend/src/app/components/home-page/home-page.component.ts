import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Filter, singleParamName} from "../../models/Filter";
import {ModalService} from "../../services/modal/modal.service";
import {UsersService} from "../../services/users-service/users.service";
import {AlertService} from "../../services/alert/alert.service";

@Component({
  selector: 'app-home',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {

  filters: Filter[] | undefined;

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private modalService: ModalService,
              private usersService: UsersService,
              private alertService: AlertService) {
  }

  ngOnInit(): void {
    this.checkChangePasswordRoute()
    this.fillFilters()
  }

  /**
   *
   * @private
   */
  private checkChangePasswordRoute() {
    let currentUrl = this.router.parseUrl(this.router.url).root.children['primary']?.segments.map(it => it.path).join('/')
    if (currentUrl === "changePassword") {
      this.activatedRoute.queryParams.subscribe(params => {
        let token = params['token']
        if (!!token) {
          this.usersService.changePassword(token).subscribe({
            next: (res) => {
              this.modalService.open();
            },
            error: (messages: string) => {
              this.alertService.danger(messages)
            },
            complete: () => {
            }
          })
        } else {
          this.alertService.danger("Неправильная ссылка для изменения пароля.")
        }
      })
    }
  }

  /**
   *
   * @private
   */
  private fillFilters() {
    let filterBest = new class implements Filter {
      singleParams: Map<singleParamName, string> = new Map<singleParamName, string>()
        .set(singleParamName.MIN_ESTIMATION, String(5))
        .set(singleParamName.PAGE_SIZE, String(5));
      header: string = "Высокий рейтинг";
      showAll: string = "Смотреть все"
    }
    let filterNew = new class implements Filter {
      singleParams: Map<singleParamName, string> = new Map<singleParamName, string>()
        .set(singleParamName.MIN_RELEASE_DATE, String(2021))
        .set(singleParamName.PAGE_SIZE, String(5));
      header: string = "Новинки";
      showAll: string = "Все новинки"
    }
    this.filters = [filterBest, filterNew]
  }
}
