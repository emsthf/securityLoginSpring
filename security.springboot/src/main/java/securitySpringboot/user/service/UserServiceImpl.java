package securitySpringboot.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import securitySpringboot.user.model.User;
import securitySpringboot.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User addUser(User user) {
        log.info("sava User.");
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        log.info("get all User.");
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        log.info("get User by Id {}.", id);
        return Optional.ofNullable(userRepository.findById(id).get());
    }
}
