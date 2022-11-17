package server.entities.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import server.utils.validation.FieldMatch;
import server.utils.validation.Marker;
import server.utils.validation.PhoneOrEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@FieldMatch(first = "password", second = "matchingPassword", message = "Пароли не совпадают")
public class SystemUser {
    @NotBlank(groups = Marker.OnCreate.class, message = "Поле 'Телефон или email' обязательно")
    @PhoneOrEmail(groups = Marker.OnCreate.class, message = "Некорректный телефон/email")
    private String phoneOrEmail;

    @NotBlank(groups = Marker.OnUpdate.class, message = "Поле 'Телефон' обязательно")
    @Pattern(groups = Marker.OnUpdate.class, regexp = "^((\\+7)?|7?|8?)\\d$", message = "Некорректный телефон")
    @Size(groups = Marker.OnUpdate.class, min = 10, max = 12, message = "Телефон должен состоять из 10-12 символов")
    private String phone;

    @NotBlank(groups = Marker.OnUpdate.class, message = "Поле 'email' обязательно")
    @Email(groups = Marker.OnUpdate.class, message = "Некорректный email")
    private String email;

    @NotBlank(groups = Marker.OnCreate.class, message = "Поле 'Пароль' обязательно")
    @Pattern(groups = Marker.OnCreate.class, regexp = "^.*(?=.*\\d)(?=.*[a-zа-яё])(?=.*[A-ZА-ЯЁ]).*$",/*(?=.*[\Q!"#$%&'()*+,\-./:;<=>?@[]^_`{|}\E])*/
            message = "Обязательно: заглавные буквы, строчные буквы, цифры.")
    @Size(groups = Marker.OnCreate.class, min = 8, message = "Длина пароля не менее 8 символов")
    @Size(groups = Marker.OnCreate.class, max = 20, message = "Длина пароля не более 20 символов")
    private String password;

    @NotBlank(groups = Marker.OnCreate.class, message = "Поле 'Повторите пароль' обязательно")
    private String matchingPassword;

    @NotBlank(groups = Marker.OnUpdate.class, message = "Поле 'Имя' обязательно")
    @Size(groups = Marker.OnUpdate.class, min = 2, message = "Не менее 2-х символов")
    private String firstName;

    @NotBlank(groups = Marker.OnUpdate.class, message = "Поле 'Фамилия' обязательно")
    @Size(groups = Marker.OnUpdate.class, min = 2, message = "Не менее 2-х символов")
    private String lastName;
}
