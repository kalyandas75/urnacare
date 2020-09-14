export class Account {
    constructor(
        public activated: boolean,
        public authority: string,
        public email: string,
        public firstName: string,
        public langKey: string,
        public lastName: string,
        public imageUrl: string,
    ) {}
}
