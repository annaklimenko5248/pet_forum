package app.model;

import app.configuration.other_configuration.LocalDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "post")
@Entity
@NoArgsConstructor
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;//название поста
    private String content;//содержимое поста
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate creationDate;//дата создания

    //TODO добавить связь ManyToOne
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;//автор поста

    //TODO Добавить связь OneToMany
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "postEntity")
    private List<CommentEntity> commentList = new ArrayList<>();//список комментариев


}
