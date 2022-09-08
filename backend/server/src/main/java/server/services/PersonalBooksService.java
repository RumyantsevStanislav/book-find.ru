package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import server.entities.PersonalBook;
import server.repositories.PersonalBooksRepository;

import java.util.List;

@Service
public class PersonalBooksService {
    private PersonalBooksRepository personalBooksRepository;

    @Autowired
    public void setPersonalBooksRepository(PersonalBooksRepository personalBooksRepository) {
        this.personalBooksRepository = personalBooksRepository;
    }

    public List<PersonalBook> getPersonalBooks(String phone, String email) {
        return personalBooksRepository.findAllByPhoneOrEmail(phone, email);
    }

    public ResponseEntity<?> saveOrUpdate(PersonalBook personalBook) {
        Long isbn = personalBook.getIsbn();
        String phone = personalBook.getPhone();
        String email = personalBook.getEmail();
        PersonalBook existingPersonalBook = personalBooksRepository.findByIsbnAndPhoneAndEmail(isbn, phone, email);
        if (existingPersonalBook != null) {
            existingPersonalBook.setStatus(personalBook.getStatus());
            existingPersonalBook.setComment(personalBook.getComment());
            existingPersonalBook.setEstimation(personalBook.getEstimation());
            personalBooksRepository.save(existingPersonalBook);
            return ResponseEntity.ok(existingPersonalBook);

        }
        personalBooksRepository.save(personalBook);
        return ResponseEntity.ok(personalBook);
    }

}
