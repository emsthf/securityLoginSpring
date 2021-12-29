package securitySpringboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import securitySpringboot.user.model.Role;
import securitySpringboot.user.model.User;
import securitySpringboot.user.service.UserService;

import java.util.ArrayList;

@EnableJpaAuditing
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean   // 기본 데이터 집어넣기. 스프링이 돌아간 후에 작동하게 된다
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.addRole(new Role(null, "ROLE_USER"));   // 유저에는 롤이 들어가도록 설정이 되어 있기 때문에 유저보다 롤을 먼저 만들어줘야 한다. id는 자동 생성
			userService.addRole(new Role(null, "ROLE_MANAGER"));
			userService.addRole(new Role(null, "ROLE_ADMIN"));
			userService.addRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.addUser(User.builder()
					.id(null)
					.password("abcd1234")
					.roles(new ArrayList<>())
					.userName("Tim Cook")
					.userEmail("cook@apple.com")
					.build()
			);
			userService.addUser(User.builder()
					.id(null)
					.password("qwer789")
					.roles(new ArrayList<>())
					.userName("Khalid")
					.userEmail("khalid@gmail.com")
					.build()
			);
			userService.addUser(User.builder()
					.id(null)
					.password("cat1q2w3e")
					.roles(new ArrayList<>())
					.userName("Doja Cat")
					.userEmail("dojacat@apple.com")
					.build()
			);
			userService.addUser(User.builder()
					.id(null)
					.password("marble098")
					.roles(new ArrayList<>())
					.userName("Spiderman")
					.userEmail("spider@gmail.com")
					.build()
			);
			userService.addUser(User.builder()
					.id(null)
					.password("spider1212")
					.roles(new ArrayList<>())
					.userName("Tom Holland")
					.userEmail("tomhall@gmail.com")
					.build()
			);

			userService.addRoleByUser("Tim Cook", "ROLE_USER");
			userService.addRoleByUser("Tim Cook", "ROLE_MANAGER");
			userService.addRoleByUser("Tim Cook", "ROLE_ADMIN");
			userService.addRoleByUser("Tim Cook", "ROLE_SUPER_ADMIN");

			userService.addRoleByUser("Khalid", "ROLE_USER");

			userService.addRoleByUser("Doja Cat", "ROLE_USER");
			userService.addRoleByUser("Doja Cat", "ROLE_MANAGER");
			userService.addRoleByUser("Doja Cat", "ROLE_ADMIN");

			userService.addRoleByUser("Spiderman", "ROLE_USER");
			userService.addRoleByUser("Spiderman", "ROLE_MANAGER");

			userService.addRoleByUser("Tom Holland", "ROLE_USER");
		};
	}
}
