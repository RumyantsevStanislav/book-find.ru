import {ComponentRef, Directive, OnInit, TemplateRef, ViewContainerRef} from "@angular/core";
import {ComponentType} from "@angular/cdk/portal";
import {LoginFormComponent} from "../../components/shared/login-form/login-form.component";
import {ChangePasswordComponent} from "../../components/shared/change-password/change-password.component";


@Directive({
  selector: '[signModal]',
  exportAs: 'exportSignModal' //doesn't work with structural directives?
})
export class SignModalDirective implements OnInit {

  signModalComponent: ComponentRef<any> | undefined

  constructor(private templateRef: TemplateRef<any>,
              private viewContainerRef: ViewContainerRef) {
  }

  /**
   * Для добавления свойств элемента
   */

  //@HostBinding
  /**
   * Для обработки событий
   */
  //@HostListener

  ngOnInit(): void {
    if (window.location.pathname === "/changePassword") {
      this.signModalComponent = this.viewContainerRef.createComponent(ChangePasswordComponent);
    } else {
      this.signModalComponent = this.viewContainerRef.createComponent(LoginFormComponent);
    }
    //const signModalComponent = this.viewContainerRef.createEmbeddedView(this.templateRef);
    // signModalComponent.instance.title = 'Войти'
    // signModalComponent.instance.close.subscribe(() => {
    //   signModalComponent.destroy()
    // })
  }

  public showSignModal<T>(component: ComponentType<T>) {
    this.viewContainerRef.clear()
    this.signModalComponent = this.viewContainerRef.createComponent(component);
    //const signModalComponent = this.viewContainerRef.createEmbeddedView(this.templateRef);
    // signModalComponent.instance.title = 'Войти'
    // signModalComponent.instance.close.subscribe(() => {
    //   signModalComponent.destroy()
    // })
  }
}
