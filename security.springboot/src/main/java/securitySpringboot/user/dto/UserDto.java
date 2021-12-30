package securitySpringboot.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserDto {
    private String userName;
    private String userEmail;
    private String password;

    @Builder
    public UserDto(String userName, String userEmail, String password) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
    }
}
