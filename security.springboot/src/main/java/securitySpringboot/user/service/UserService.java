package securitySpringboot.user.service;

import securitySpringboot.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User addUser(User user);
    List<User> getAllUser();
    Optional<User> getUserById(Long id);
}
