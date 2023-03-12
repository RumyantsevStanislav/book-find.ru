package server.entities.dtos.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import server.utils.validation.Marker;
import server.utils.validation.PhoneOrEmail;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class AuthUser {
    @NotBlank(groups = Marker.OnCreate.class, message = "Поле 'Телефон или email' обязательно")
    @PhoneOrEmail(groups = Marker.OnCreate.class, message = "Некорректный телефон/email")
    private String phoneOrEmail;

    @NotBlank(groups = Marker.OnCreate.class, message = "Поле 'Пароль' обязательно")
    @Pattern(groups = Marker.OnCreate.class,
            //regexp = "^.*(?=.*\\d)(?=.*[a-zа-яё])(?=.*[A-ZА-ЯЁ]).*$",/*(?=.*[\Q!"#$%&'()*+,\-./:;<=>?@[]^_`{|}\E])*/
            regexp = "^.*(?=.*\\d)(?=.*[a-zа-яёA-ZА-ЯЁ]).*$",
            message = "Обязательно: заглавные буквы, строчные буквы, цифры.")
    @Size(groups = Marker.OnCreate.class, min = 8, message = "Длина пароля не менее 8 символов")
    @Size(groups = Marker.OnCreate.class, max = 20, message = "Длина пароля не более 20 символов")
    private String password;
}
