export interface Book {
  title: string
  //id?: number
  authors: Set<Author>
  cover: Cover
}

export interface Cover {
  path: string
  extension: string
}

export interface Author {
  name: string
  role: string
}

export interface User {
  email: string
  password: string
  returnSecureToken?: boolean
}

export interface AuthResponse {
  idToken: string
  expiresIn: string
}
