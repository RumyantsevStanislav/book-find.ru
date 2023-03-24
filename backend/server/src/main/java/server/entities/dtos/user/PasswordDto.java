package server.entities.dtos.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import server.utils.validation.FieldMatch;
import server.utils.validation.Marker;
import server.utils.validation.ValidPassword;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@FieldMatch(groups = Marker.OnCreate.class, first = "password", second = "matchingPassword", message = "Пароли не совпадают")
public class PasswordDto {

    private String token;

    @ValidPassword
    private String password;

    @NotBlank(groups = Marker.OnCreate.class, message = "Поле 'Повторите пароль' обязательно")
    private String matchingPassword;
}
