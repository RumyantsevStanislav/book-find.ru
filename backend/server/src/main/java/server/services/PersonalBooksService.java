package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import server.entities.PersonalBook;
import server.repositories.PersonalBooksRepository;

import java.util.List;

/**
 * Service for working with personal set of book.
 */
@Service
public class PersonalBooksService {
    /**
     * Bean, responsible for the access to personal book schema into DB.
     */
    private PersonalBooksRepository personalBooksRepository;

    @Autowired
    public void setPersonalBooksRepository(PersonalBooksRepository personalBooksRepository) {
        this.personalBooksRepository = personalBooksRepository;
    }

    /**
     * Getting list of {@link PersonalBook} by user's phone or email.
     *
     * @param phone user's phone.
     * @param email user's email.
     * @return list of {@link PersonalBook}.
     */
    public List<PersonalBook> getPersonalBooks(String phone, String email) {
        return personalBooksRepository.findAllByPhoneOrEmail(phone, email);
    }

    /**
     * Save {@link PersonalBook} entity into DB, or update if exists.
     *
     * @param personalBook entity of saved personal book.
     */
    public void saveOrUpdate(@NonNull final PersonalBook personalBook) {
        String isbn = personalBook.getIsbn();
        String phone = personalBook.getPhone();
        String email = personalBook.getEmail();
        // TODO: 04.04.2023 resolve cascade fields in Book entity (see entity in debug mode).
        // TODO: 04.04.2023  use DTO instead of full object 
        personalBooksRepository.findByIsbnAndPhoneAndEmail(isbn, phone, email).ifPresentOrElse((existingPersonalBook) -> {
            existingPersonalBook.setStatus(personalBook.getStatus());
            existingPersonalBook.setComment(personalBook.getComment());
            existingPersonalBook.setEstimation(personalBook.getEstimation());
            personalBooksRepository.save(existingPersonalBook);
        }, () -> personalBooksRepository.save(personalBook));
    }

}
