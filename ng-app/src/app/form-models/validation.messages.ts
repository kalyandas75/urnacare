export function minlengthValidationMessage(err, field) {
    return `${field.templateOptions.label} should have atleast ${field.templateOptions.minLength} characters`;
  }
  
  export function maxlengthValidationMessage(err, field) {
    return `${field.templateOptions.label} should be less than ${field.templateOptions.maxLength} characters`;
  }
  
  export function minValidationMessage(err, field) {
    return `${field.templateOptions.label} should be more than ${field.templateOptions.min}`;
  }
  
  export function maxValidationMessage(err, field) {
    return `${field.templateOptions.label} should be less than ${field.templateOptions.max}`;
  }

  export function patternValidationMessage(err, field) {
    return `${field.templateOptions.label} is invalid`;
  }

  export function requiredValidationMessage(err, field) {
    return `${field.templateOptions.label} is required`;
  }


  //export function 

export const ValidationMessages = [
    { name: 'required', message: requiredValidationMessage },
    { name: 'minlength', message: minlengthValidationMessage },
    { name: 'maxlength', message: maxlengthValidationMessage },
    { name: 'min', message: minValidationMessage },
    { name: 'max', message: maxValidationMessage },
    { name: 'pattern', message: patternValidationMessage },
  ];