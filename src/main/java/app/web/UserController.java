package app.web;

import app.model.UserEntity;
import app.repository.CommentEntityRepository;
import app.repository.PostEntityRepository;
import app.repository.UserRepository;
import app.service.CommentServiceDao;
import app.service.PostServiceDao;
import app.service.UserServiceDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin//не использую в этом проекте, использую с html
//csrf - cross site request forgery - просто проверка, что запрос отправлен с нашего сайта
//cors - cross origin resource sharing - защищает от подмены сайта
//@JsonComponent//Для того, чтобы Spring понимал, что именно наш класс нужно использовать для (де)сериализации
@RestController
@RequiredArgsConstructor //создает конструктор для всех final полей с аннотацией @Autowired
@RequestMapping("/users")
public class UserController {


    private final UserServiceDao userServiceDao;
    private final PostServiceDao postServiceDao;
    private final CommentServiceDao commentServiceDao;
    private final UserRepository userRepository;
    private final PostEntityRepository postEntityRepository;
    private final CommentEntityRepository commentEntityRepository;

    //для BCryptPasswordEncoder нельзя написать private final BCryptPasswordEncoder bCryptPasswordEncoder,
    //потому - что этот класс не помечен аннотацией @Serves
    //поэтому просто создала объект класса

    //регистрация юзера(доступно всем)
    @PostMapping("/register")
    public void registration(@RequestBody UserEntity user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));//зашифровали пароль, хранится в б/д в зашифрованном виде
        userServiceDao.registration(user);
    }

}
