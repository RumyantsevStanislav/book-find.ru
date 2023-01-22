package server.entities.dtos.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import server.utils.validation.Marker;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class AccountUser {

    @NotBlank(groups = Marker.OnUpdate.class, message = "Поле 'Телефон' обязательно")
    @Pattern(groups = Marker.OnUpdate.class, regexp = "^((\\+7)?|7?|8?)\\d$", message = "Некорректный телефон")
    @Size(groups = Marker.OnUpdate.class, min = 10, max = 12, message = "Телефон должен состоять из 10-12 символов")
    private String phone;

    @NotBlank(groups = Marker.OnUpdate.class, message = "Поле 'email' обязательно")
    @Email(groups = Marker.OnUpdate.class, message = "Некорректный email")
    private String email;

    @NotBlank(groups = Marker.OnUpdate.class, message = "Поле 'Имя' обязательно")
    @Size(groups = Marker.OnUpdate.class, min = 2, message = "Не менее 2-х символов")
    private String firstName;

    @NotBlank(groups = Marker.OnUpdate.class, message = "Поле 'Фамилия' обязательно")
    @Size(groups = Marker.OnUpdate.class, min = 2, message = "Не менее 2-х символов")
    private String lastName;
}
