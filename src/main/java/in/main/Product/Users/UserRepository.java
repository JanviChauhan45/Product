package in.main.Product.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    Optional<User> findById(Long id);
    boolean existsByName(String name);
    boolean existsByEmailIgnoreCase(String email);
   Optional<User> findByName(String name);
}
