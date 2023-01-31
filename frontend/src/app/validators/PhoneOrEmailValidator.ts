import {regExpPatterns} from "./RegExpPatterns";

export function phoneOrEmailValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;
    if (!value) {
      return null;
    }
    const isEmail = regExpPatterns.emailPattern.test(value);
    const isPhone = regExpPatterns.phonePattern.test(value);
    const phoneOrEmailValid = isEmail || isPhone;
    return !phoneOrEmailValid ? {phoneOrEmail: true} : null;
  }
}

import {AbstractControl, ValidationErrors, ValidatorFn} from "@angular/forms";
