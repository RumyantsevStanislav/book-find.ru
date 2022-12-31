import {Directive, HostBinding, HostListener, OnInit, TemplateRef, ViewContainerRef} from '@angular/core';
import {SignModalComponent} from "./components/shared/sign-modal/sign-modal.component";

@Directive({
  selector: '[signModal]',
  exportAs: 'exportSignModal' //doesn't work with structural directives?
})
export class SignModalDirective implements OnInit {
  constructor(private templateRef: TemplateRef<any>, private viewContainerRef: ViewContainerRef) {
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
    //this.showSignModal()
  }

  public showSignModal() {
    this.viewContainerRef.clear()
    const signModalComponent = this.viewContainerRef.createComponent(SignModalComponent);
    //const signModalComponent = this.viewContainerRef.createEmbeddedView(this.templateRef);
    signModalComponent.instance.title = 'Войти'
    signModalComponent.instance.close.subscribe(() => {
      signModalComponent.destroy()
    })
  }
}
