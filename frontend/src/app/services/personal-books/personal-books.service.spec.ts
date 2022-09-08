import { TestBed } from '@angular/core/testing';

import { PersonalBooksService } from './personal-books.service';

describe('PersonalLibraryService', () => {
  let service: PersonalBooksService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PersonalBooksService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
