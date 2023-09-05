package app.configuration.security;

import app.model.UserEntity;
import app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor//создает конструктор для всех полей с аннотацией @Autowired
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    //могу не писать
//    @Autowired
//    public UserDetailsServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity myUser = userRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("User not found"));
        //Перенесли данные из нашего класса UserEntity в UserDetails (реализованный на базе класса User)
        UserDetails user = User.builder()
                .username(myUser.getLogin())
                .password(myUser.getPassword())
                .roles(myUser.getUserRole().name())
                .build();
        return user;
    }
}