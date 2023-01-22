package server.entities.dtos.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import server.utils.validation.FieldMatch;
import server.utils.validation.Marker;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@FieldMatch(groups = Marker.OnCreate.class, first = "password", second = "matchingPassword", message = "Пароли не совпадают")
public class RegisteringUser extends AuthUser {

    @NotBlank(groups = Marker.OnCreate.class, message = "Поле 'Повторите пароль' обязательно")
    private String matchingPassword;
}
