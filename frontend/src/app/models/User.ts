export interface User {
  username: string
  password: string
}

export class SystemUser {
  constructor(
    public userName: string,
    public firstName: string,
    public lastName: string,
    public password: string,
    public matchingPassword: string,
    public email: string,
    public birthDay: string
  ) {
  }
}
