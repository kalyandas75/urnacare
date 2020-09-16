export const PatientFormModel = [
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
        },
    },
    {
        fieldGroupClassName: 'row',
        fieldGroup: [
            {
                className: 'col-md-4',
                type: 'input',
                key: 'weight',
                templateOptions: {
                    label: 'Weight(In kg)',
                    type: 'number'
                },
            },
            {
                className: 'col-md-4',
                type: 'input',
                key: 'height',
                templateOptions: {
                    label: 'Height(In ft and in)'
                },
            }
        ],
    }
];