package securitySpringboot.user.dto;

import lombok.Data;

@Data
public class RoleToUserForm {
    private String userName;
    private String roleName;
}
