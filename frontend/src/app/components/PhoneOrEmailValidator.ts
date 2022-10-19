import {AbstractControl, ValidationErrors, ValidatorFn} from "@angular/forms";

export function phoneOrEmailValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;
    if (!value) {
      return null;
    }
    let phonePattern = new RegExp('^(\\+7|7|8)?\\d{10}$');
    let emailPattern = new RegExp('^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$');
    const isEmail = emailPattern.test(value);
    const isPhone = phonePattern.test(value);
    const phoneOrEmailValid = isEmail || isPhone;
    return !phoneOrEmailValid ? {phoneOrEmail: true} : null;
  }
}
