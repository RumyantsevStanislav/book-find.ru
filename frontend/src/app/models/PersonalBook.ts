import {Book} from "./Book";

export class PersonalBook {
  constructor(
    public isbn: number,
    public status: string,
    public estimation?: number,
    public comment?: string
  ) {
  }
}

export interface PersonalBookImpl extends PersonalBook {
  bookDtoImpl: Book
}

export enum Status {
  WILL_READ = 'Буду читать',
  READING = 'Читаю',
  HAVE_READ = 'Прочитано'
}
