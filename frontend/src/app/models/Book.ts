export interface BookFull extends Book {
  description?: string
  price?: number
  pages?: number
  year?: number
  categories?: Set<Category>
  publisher?: Publisher
  genre?: Genre
  series?: Series
  reviews?: Set<Review>
}

export interface Book {
  title: string
  estimation?: number
  isbn: string
  authors?: Set<Author>
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

export interface Review {
  //todo add title and date
  //title?: string
  review: string
  estimation: number
  book: BookPk
  profileDto?: ProfileDto
  //date?: Date
}

export interface BookPk {
  isbn: string
}

export interface ProfileDto {
  id: number;
  firstName: string
  lastName: string
}
