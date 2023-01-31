package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.entities.User;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query("update User u set u.phone = :phone, u.email = :email, u.firstName = :firstName, u.lastName = :lastName")
    void update(@Param("phone") String phone, @Param("email") String email, @Param("firstName") String firstName, @Param("lastName") String lastName);

    //    @Query("select count(u) from User u where upper(u.email) = upper(?1)")
    //    long test(String email);
    Optional<User> findByPhone(String phone);

    Optional<User> findByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);
}
