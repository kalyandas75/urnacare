import { SPECIALITIES } from './specialities';
export const DoctorFormModel = [
    {
        fieldGroupClassName: 'row',
        fieldGroup: [
            {
                className: 'col-md-4',
                type: 'input',
                key: 'firstName',
                templateOptions: {
                    label: 'First Name',
                    required: true
                },
            },
            {
                className: 'col-md-4',
                type: 'input',
                key: 'middleName',
                templateOptions: {
                    label: 'Middle Name',
                },
            },
            {
                className: 'col-md-4',
                type: 'input',
                key: 'lastName',
                templateOptions: {
                    label: 'Last Name',
                    required: true
                }
            },
        ],
    },
    {
        fieldGroupClassName: 'row',
        fieldGroup: [
            {
                className: 'col-md-4',
                type: 'input',
                key: 'email',
                templateOptions: {
                    label: 'Email',
                    type: 'email',
                    required: true,
                    pattern: /^\S+@\S+$/
                },
            },
            {
                className: 'col-md-4',
                type: 'input',
                key: 'mobile',
                templateOptions: {
                    label: 'Mobile',
                    required: true
                },
            },
            {
                className: 'col-md-4',
                type: 'input',
                key: 'phone',
                templateOptions: {
                    label: 'Phone',
                }
            },
        ],
    },
    {
        fieldGroupClassName: 'row',
        fieldGroup: [
            {
                className: 'col-md-4',
                type: 'select',
                key: 'gender',
                templateOptions: {
                    label: 'Gender',
                    required: true,
                    options: [
                        { value: 'Female', label: 'Female'  },
                        { value: 'Male', label: 'Male'  },
                        { value: 'Other', label: 'Other'  }
                    ],
                },
            },
            {
                className: 'col-md-4',
                type: 'input',
                key: 'dob',
                templateOptions: {
                    label: 'Date of Birth',
                    type: 'date'
                },
            },
            {
                className: 'col-md-4',
                type: 'input',
                key: 'languageSpoken',
                templateOptions: {
                    label: 'Languages Spoken',
                }
            },
        ],
    },
    {
        type: 'textarea',
        key: 'address',
        templateOptions: {
            label: 'Address',
            rows: 5
        },
    },
    { template: '<hr />' },
    {
        fieldGroupClassName: 'row',
        fieldGroup: [
            {
                className: 'col-md-6',
                type: 'input',
                key: 'registrationNumber',
                templateOptions: {
                    label: 'Registration Number'
                },
            },
            {
                className: 'col-md-6',
                type: 'textarea',
                key: 'qualifications',
                templateOptions: {
                    label: 'Qualifications',
                    rows: 5
                },
            }
        ],
    },
    {
        fieldGroupClassName: 'row',
        fieldGroup: [
            {
                className: 'col-md-6',
                type: 'select',
                key: 'speciality',
                templateOptions: {
                    label: 'Speciality',
                    required: true,
                    options: getSpecialities(),
                },
            },
            {
                className: 'col-md-6',
                type: 'textarea',
                key: 'specializations',
                templateOptions: {
                    label: 'Specializations',
                    rows: 5
                },
            }
        ],
    },
    {
        fieldGroupClassName: 'row',
        fieldGroup: [
            {
                className: 'col-md-6',
                type: 'textarea',
                key: 'practice',
                templateOptions: {
                    label: 'Practice',
                    rows: 5
                },
            }
        ],
    }
];

export function getSpecialities() {
    return SPECIALITIES.map(speciality => {
        return { value: speciality, label: speciality}
    });
}

export function getAppointmentHours() {
    const hours = [];
    for(let i=8;i<=20;i++) {
        hours.push({value: i, label: i});
    }
    return hours;
}