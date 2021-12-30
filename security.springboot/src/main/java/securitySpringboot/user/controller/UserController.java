package securitySpringboot.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import securitySpringboot.user.dto.RoleToUserForm;
import securitySpringboot.user.dto.UserDto;
import securitySpringboot.user.model.Role;
import securitySpringboot.user.model.User;
import securitySpringboot.user.service.UserService;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")   // ????
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class UserController {

    private final UserService userService;

    // 지금은 단계는 관리자 계정이 아니므로 회원가입 기능은 시큐리티 로그인이 안되어 있어도 풀어줘야 한다
    @PostMapping("/user/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/add").toUriString());   // 위에 포스트맵핑이 있지만 이쪽으로넘기는 것
        return ResponseEntity.created(uri).body( userService.addUser(user) );   // 리액트를 붙일 때는 토큰 방식으로 할 것이기 때문에 이렇게 할 필요 없다??
    }

    @PostMapping("/user/adddto")
    public ResponseEntity<User> addUserDto(@RequestBody UserDto userDto) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/adddto").toUriString());
        return ResponseEntity.created(uri).body( userService.addUserDto(userDto) );
    }

    // Role을 추가하는 api
    @PostMapping("/role/add")
    public ResponseEntity<Role> addRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/add").toUriString());
        return ResponseEntity.created(uri).body( userService.addRole(role) );
    }

    @PostMapping("/user/addroletouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm roleToUserForm) {
        userService.addRoleByUser(roleToUserForm.getUserName(), roleToUserForm.getRoleName());
        return ResponseEntity.ok().build();
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
