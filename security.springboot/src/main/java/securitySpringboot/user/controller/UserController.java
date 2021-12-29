package securitySpringboot.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import securitySpringboot.user.model.User;
import securitySpringboot.user.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/add")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/user/getAll")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/user/get/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id).get();
    }
}
