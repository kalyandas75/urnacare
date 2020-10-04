import { AbstractControl, ValidatorFn } from '@angular/forms';
import { environment } from 'src/environments/environment';

export const SERVICE_API_URL = '/api';
export const PROBLEM_BASE_URL = '../problem';
export const EMAIL_ALREADY_USED_TYPE = PROBLEM_BASE_URL + '/email-already-used';
export const LOGIN_ALREADY_USED_TYPE = PROBLEM_BASE_URL + '/login-already-used';
export const EMAIL_NOT_FOUND_TYPE = PROBLEM_BASE_URL + '/email-not-found';
export const ITEMS_PER_PAGE = 20;
export const SPECIALITIES = [
    'Allergy and Immunology',
    'Anesthesiology',
    'Cardiology',
    'Dentistry',
    'Dermatology',
    'Endocrinology',
    'ENT',
    'Gastroenterology',
    'General',
    'Neurology',
    'Obstetrics and Gynecology',
    'Oncology',
    'Ophthalmology',
    'Orthopaedic Surgery',
    'Pediatrics',
    'Psychiatry',
    'Pulmonology',
    'Radiology',
    'Rheumatology',
    'Sexology',
    'Urology'
];
export const PHONE_REGEX = /^[1-9]{1}[0-9]{9}$/;

export const FORMULATIONS = [
    "Tablet",
    "Capsule",
    "Injection",
    "Drops",
    "Tonic",
    "Lotion",
    "Cream",
    "Powder",
    "Device",
  ];
export const UNITS = ["mg", "g", "ml", "ltr", "nos"];

export function ValidateValueInList(values: any[], term?: any): ValidatorFn {
    return (control: AbstractControl): { [key: string]: boolean } | null => {
      if(!!term && (!values || values.length < 1 || !control.value || !control.value.id)) {
        return { 'valueNotInList': true};
      }
      return null;
    };
}


