export interface User {
  username: string
  password: string
}

export interface SystemUser {
  phoneOrEmail: string,
  phone?: string,
  email?: string,
  password: string,
  matchingPassword: string,
  firstName?: string,
  lastName?: string,
  birthDay?: string
}
