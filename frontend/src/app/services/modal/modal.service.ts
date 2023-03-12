import {Injectable} from '@angular/core';

import {Observable, Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  private display: Subject<'open' | 'close'> =
    new Subject<"open" | "close">();

  watch(): Observable<'open' | 'close'> {
    return this.display.asObservable();
  }

  open() {
    this.display.next('open');
  }

  close() {
    this.display.next('close');
  }
}
