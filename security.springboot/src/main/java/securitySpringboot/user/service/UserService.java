package securitySpringboot.user.service;

import securitySpringboot.user.model.Role;
import securitySpringboot.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User addUser(User user);
    Role addRole(Role role);   // 역할 추가 추상 메소드
    void addRoleByUser(String userName, String roleName);   // 유저에 역할 부여 추상 메소드
    List<User> getAllUser();
    User getUserByUserName(String userName);
    Optional<User> getUserById(Long id);
}
