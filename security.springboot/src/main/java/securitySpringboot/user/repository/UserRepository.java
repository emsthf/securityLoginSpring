package securitySpringboot.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import securitySpringboot.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);   // 유저 이름으로 유저를 찾는 기능
}
