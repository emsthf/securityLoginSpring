package securitySpringboot.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import securitySpringboot.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
