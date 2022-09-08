export interface Book {
  title: string
  isbn: number
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
