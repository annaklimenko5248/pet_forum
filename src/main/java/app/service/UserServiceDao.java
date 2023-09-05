package app.service;

import app.model.UserEntity;
import app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceDao {

    private final UserRepository userRepository;

    public void registration(UserEntity user) {
        List<UserEntity> users = userRepository.findAll();
        boolean isExist = users.stream().anyMatch(u -> u.getLogin().equalsIgnoreCase(user.getLogin()));
        if (!isExist) {
            userRepository.save(user);
        } else {
            throw new RuntimeException("Login is busy");
        }
    }
}
