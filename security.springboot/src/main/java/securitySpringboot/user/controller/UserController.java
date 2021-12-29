package securitySpringboot.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import securitySpringboot.user.model.User;
import securitySpringboot.user.service.UserService;

import java.util.List;

@CrossOrigin(origins = "*")   // ????
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/user/add")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

//    @GetMapping("/user/getAll")
//    public List<User> getAllUser() {
//        return userService.getAllUser();
//    }

    @GetMapping("/user/getAll")
    public ResponseEntity< List<User> > getAllUser() {   // ResponseEntity로 감싼다
        return ResponseEntity.ok().body( userService.getAllUser() );
    }

    @GetMapping("/user/get/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id).get();
    }
}
