export interface BookFull {
  title: string
  description?: string
  price?: number
  pages?: number
  year?: number
  estimation?: number
  isbn: number
  categories?: Set<Category>
  authors?: Set<Author>
  publisher?: Publisher
  genre?: Genre
  series?: Series
  cover: Cover
// TODO demands
}

export interface Book {
  title: string
  estimation: number
  isbn: number
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

export interface Category {
  title: string
}

export interface Publisher {
  title: string
  description: string
}

export interface Genre {
  path: string
}

export interface Series {
  title: string
  description: string
}
