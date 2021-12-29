package securitySpringboot.user.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import securitySpringboot.util.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.EAGER;

@Data
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String userEmail;
    private String password;

    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();   // 테이블을 가져오는 것이 아니다. 유저는 여러 개의 롤을 가지게 된다

    @Builder   // 시큐리티로 인해 api를 사용하지 못하기 때문에 다 만들어줘야 한다
    public User(Long id, String userName, String userEmail, String password, Collection<Role> roles) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
        this.roles = roles;
    }
}
