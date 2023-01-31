import {AbstractControl, ValidatorFn} from "@angular/forms";

export function ConfirmedValidator(controlName: string, matchingControlName: string): ValidatorFn {
  return (formGroup: AbstractControl) => {
    const control = formGroup.get(controlName)?.value;
    const matchingControl = formGroup.get(matchingControlName)?.value;
    if (!control || !matchingControl) {
      return null;
    }
    const valid = control == matchingControl;
    return !valid ? {matchingPasswords: true} : null;
  }
}
