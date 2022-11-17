package server.entities.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import server.utils.validation.ValidPassword;

@Getter
@Setter
@ToString
public class PasswordDto {

    private String oldPassword;

    private String token;

    @ValidPassword
    private String newPassword;
}