package app.configuration.security;

import app.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Класс реализует настройки доступа по таким-то энпонтам и т. д.(дописать)
 */

//@CrossOrigin//выключаем требования безопасности (отключает требования безопасности на уровне Спринга(использовали, когда использовали формочки)
//тут может не понадобиться
@Configuration
@EnableWebSecurity//дает возможность переопределить метод configure
@RequiredArgsConstructor//создает конструктор для всех полей с аннотацией @Autowired
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //TODO заменить на requiredArgs и поле сделать final
    private final UserDetailsServiceImpl userDetailsService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService); //говорим спрингу использовать наш userDetailsService

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //это если мы не используем шифрование паролей
        // return (PasswordEncoder) NoOpPasswordEncoder.getInstance();
        //а это, если используем
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic() //включает возможность аутентифицироваться через постман
                .and()
                .csrf().disable()//отключаем требования безопасности
                .cors().disable()//а над энпоинтами в классе UserController можем написать @CrossOrigin
                // (тут мы отключаем на уровне браузера, а в классе с энпоинтами на уровне Спринга
                // (но это аннотация может не понадобиться, она нужна была, когда работали с формочками)
                .authorizeRequests()//позволяет задавать энпоинты

                .antMatchers(HttpMethod.POST, "/users/register")//регистрация юзера(доступна всем)
                .permitAll()

                .antMatchers(HttpMethod.POST, "/users/savePost")//добавить пост(доступно USER, ADMIN, MODERATOR)
                .hasAnyRole(UserRole.USER.name(), UserRole.ADMIN.name(), UserRole.MODERATOR.name())//будет проверять роль

                //TODO как сделать владелец поста
                .antMatchers(HttpMethod.PUT, "/users/updatePost/title/{title}")//Обновить пост (доступно USER, если это владелец поста, а также доступно любому ADMIN, MODERATOR)
                .hasAnyRole(UserRole.USER.name(), UserRole.ADMIN.name(), UserRole.MODERATOR.name())

                //TODO как сделать владелец поста
                .antMatchers(HttpMethod.DELETE, "/users/deletePost/title/{title}")//- Удалить пост (доступно USER, если это владелец поста, а также доступно любому ADMIN, MODERATOR)
                .hasAnyRole(UserRole.USER.name(), UserRole.ADMIN.name(), UserRole.MODERATOR.name())

                .antMatchers(HttpMethod.PUT, "users/addComment")//- Добавить комментарий посту (доступно USER, ADMIN, MODERATOR)
                .hasAnyRole(UserRole.USER.name(), UserRole.ADMIN.name(), UserRole.MODERATOR.name())

                .antMatchers(HttpMethod.POST, "/users/putLike")
                .hasAnyRole(UserRole.USER.name(), UserRole.ADMIN.name(), UserRole.MODERATOR.name())//- Поставить лайк комментарию (доступно USER, ADMIN, MODERATOR)

                //TODO как сделать владелец поста
                .antMatchers(HttpMethod.DELETE, "/users/deleteComment")
                .hasAnyRole(UserRole.USER.name(), UserRole.ADMIN.name(), UserRole.MODERATOR.name())

                .antMatchers(HttpMethod.GET, "/users//login/{login}")//- Получить все посты автора (по логину) (доступно всем)
                .permitAll()

                .antMatchers(HttpMethod.GET, "/users/date/{date}")// Получить все посты за определенную дату (доступно всем)
                .permitAll()

                .anyRequest()//остальные методы после авторизации
                .authenticated();

        //если использую формочки, тогда это пишу
        //настройки для формы авторизации (входа в аккаунт)
//        http.formLogin()
//                .loginProcessingUrl("/auth")//авторизоваться
////                .defaultSuccessUrl("/")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .permitAll();
//
//        //настройки для выхода из аккаунта
//        http.logout()
//                .logoutUrl("/exit")
//                .logoutSuccessUrl("/logout")
//                .invalidateHttpSession(true);

    }


}
