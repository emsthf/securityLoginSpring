package securitySpringboot.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import securitySpringboot.user.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);   // 시큐리티 때문에 api를 만들 수 없다. 그래서 미리 다 만들어두기 위해 만드는 것.
}
