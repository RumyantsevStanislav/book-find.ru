import {ComponentRef, Directive, OnInit, TemplateRef, ViewContainerRef} from "@angular/core";
import {ComponentType} from "@angular/cdk/portal";
import {LoginFormComponent} from "../../components/shared/login-form/login-form.component";


@Directive({
  selector: '[signModal]',
  exportAs: 'exportSignModal' //doesn't work with structural directives?
})
export class SignModalDirective implements OnInit {
  constructor(private templateRef: TemplateRef<any>, private viewContainerRef: ViewContainerRef) {
  }

  signModalComponent: ComponentRef<any> | undefined

  /**
   * Для добавления свойств элемента
   */

  //@HostBinding
  /**
   * Для обработки событий
   */
  //@HostListener

  ngOnInit(): void {
    this.signModalComponent = this.viewContainerRef.createComponent(LoginFormComponent);
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
