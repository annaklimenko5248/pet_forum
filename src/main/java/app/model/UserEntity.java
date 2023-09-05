package app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "my_user")
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String login;
    private String password;

    //в базе енам должен сохранться в виде строки
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public UserEntity(String login, String password, UserRole userRole) {
        this.login = login;
        this.password = password;
        this.userRole = userRole;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userEntity")
    List<PostEntity> posts = new ArrayList<>();
}
