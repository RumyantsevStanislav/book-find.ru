package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import server.entities.PersonalBook;

import java.util.List;

@Repository
public interface PersonalBooksRepository extends JpaRepository<PersonalBook, Long>, JpaSpecificationExecutor<PersonalBook> {

    List<PersonalBook> findAllByPhoneOrEmail(String phone, String email);

    PersonalBook findByIsbnAndPhoneAndEmail(Long isbn, String phone, String email);
}
