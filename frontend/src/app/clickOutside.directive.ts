import {Directive, ElementRef, EventEmitter, HostListener, Output} from "@angular/core";

@Directive({
  selector: '[clickOutside]'
})
export class ClickOutsideDirective {

  @Output() clickOutside = new EventEmitter<void>();

  constructor(private element: ElementRef) {
  }

  @HostListener('document:click', ['$event.target'])
  public onClick(target: any) {
    const clickOutside = this.element.nativeElement.contains(target);
    if (!clickOutside) {
      this.clickOutside.emit(target);
    }
  }
}
