export interface User {
  phoneOrEmail: string
  password: string
}

export interface SystemUser extends User {
  matchingPassword: string
}

export interface RegisteredUser {
  phone?: string
  email?: string
  firstName?: string
  lastName?: string
  //birthDay?: string
}

export interface PasswordDto {
  token: string
  password: string
  matchingPassword: string
}
