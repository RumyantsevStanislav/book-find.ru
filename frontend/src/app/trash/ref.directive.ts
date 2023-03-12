import {Directive, HostListener, ViewContainerRef} from "@angular/core";

@Directive({
  selector: '[appRef]'
})
export class RefDirective {
  constructor(public containerRef: ViewContainerRef) {
  }

  // TODO: 28.11.2022 figure out how to close modal on click to window
  //
  // @HostListener('click', ['$event']) onClick(event: Event) {
  //   console.log(event)
  // }
}
