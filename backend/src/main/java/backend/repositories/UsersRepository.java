package backend.repositories;

import backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findOneByPhone(String phone);
    boolean existsByPhone(String phone);
}
