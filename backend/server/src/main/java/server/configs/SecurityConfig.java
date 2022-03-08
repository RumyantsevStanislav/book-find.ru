package server.configs;

import server.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity // (debug = true) - будут сыпаться логи работы безопасности
//Защита на уровне методов, а не эндпоинтов. Пример - @Secured над мотодом в BooksService. Существует ещё на уровне представлений (views)
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //Spring объект UserDetails - username, password, List<GrantedAuthority>
    private UserDetailsService userDetailsService;

    private UsersService usersService;

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/authenticated/**").authenticated() //Только для аутентифицированных
                .antMatchers("/admins/**").hasRole("ADMIN") // Только для роли ADMIN
                //.antMatchers("/admins/**").hasAnyRole("ADMIN", "SUPERADMIN") //Автоматически добавляет ROLE_
                //.antMatchers("/emails/**").hasAnyAuthority("CAN_READ_EMAIL") //Не добавляет ROLE_
                .anyRequest().permitAll() //Все остальные - незащищённая область
                .and()
//Стандартное окно в браузере (вместо формы formLogin). Логин/пароль подшиваются в header, можно использовать для отправки JSON
                //.httpBasic()
                .formLogin() //Форма, куда вписать логин/пароль
                //.loginPage("login") //Использование своей формы ввода логина/пароля. Опционально
                //.loginProcessingUrl("/authenticateTheUser") //Адрес, куда отправить логин/пароль. Опционально
                //.successForwardUrl("/pageForAuthenticateUsers") //Редирект после успешной авторизации. Опционально
                .and()
                .csrf().disable()//обычно отключают для REST
                .logout().logoutSuccessUrl("/"); //Можно настроить процесс logout, например почистить cookies
    }

    //In memory authentication. Настроили своих юзеров. Спринг не будет выдавать пароль при запуске.
//    @Bean
//    public UserDetailsService users() {
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2y$12$mFUdPh8.ESnhu.eyDjxrYuSigUIOboDP94mt7vuNhf604Yw0iuKQa")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2y$12$mFUdPh8.ESnhu.eyDjxrYuSigUIOboDP94mt7vuNhf604Yw0iuKQa")
//                .roles("USER", "ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }

//    @Bean
//    public JdbcUserDetailsManager users(DataSource dataSource) {
//Можно создать своих юзеров
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2y$12$mFUdPh8.ESnhu.eyDjxrYuSigUIOboDP94mt7vuNhf604Yw0iuKQa")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2y$12$mFUdPh8.ESnhu.eyDjxrYuSigUIOboDP94mt7vuNhf604Yw0iuKQa")
//                .roles("USER", "ADMIN")
//                .authorities("ROLE_USER", "ROLE_ADMIN", "CAN_READ_SOMETHING")
//                .build();
//Если созданные юзеры существуют - можем их заменить.
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//        if (users.userExists(user.getUsername())) {
//            users.deleteUser(user.getUsername());
//        }
//        if (users.userExists(admin.getUsername())) {
//            users.deleteUser(admin.getUsername());
//        }
//        users.createUser(user);
//        users.createUser(admin);
//        return users;
//    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //По умолчанию Spring использует таблицы users и authorities для хранения пользователей (cм. sql.txt)
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(usersService); //или userDetailsService для InMemory  и таблицы из sql.txt
        return authenticationProvider;
    }

}
