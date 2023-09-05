package app.service;

import app.configuration.other_configuration.LocalDateDeserializer;
import app.model.CommentEntity;
import app.model.PostEntity;
import app.model.UserEntity;
import app.repository.PostEntityRepository;
import app.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceDao {
    private final PostEntityRepository postEntityRepository;
    private final UserRepository userRepository;

    //сохранить пост
    //В этом примере мы создаем объект ObjectMapper и объект SimpleModule,
// затем регистрируем десериализатор для LocalDate в объекте SimpleModule.
// Мы также регистрируем объект SimpleModule в объекте ObjectMapper.
//
//Теперь при десериализации объекта PostEntity, содержащего свойство типа LocalDate,
// будет использоваться зарегистрированный десериализатор.


    public void savePost(PostEntity postEntity) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        SimpleModule module = new SimpleModule();
//        module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
//        objectMapper.registerModule(module);
        postEntityRepository.save(postEntity);
    }

    //обновить пост
    public void updatePost(@PathVariable String title, @RequestBody PostEntity newPostEntity, Principal principal) {
        PostEntity foundPost = postEntityRepository.findByTitle(title).orElseThrow(() -> new RuntimeException("Post not fount"));
        if (foundPost.getUserEntity().getLogin().equals(principal.getName())) {
            foundPost.setTitle(newPostEntity.getTitle());
            foundPost.setContent(newPostEntity.getContent());
            foundPost.setCreationDate(newPostEntity.getCreationDate());
            postEntityRepository.save(foundPost);
        } else {
            throw new RuntimeException("Вы не автор поста");
        }
    }

    //- Удалить пост (доступно USER, если это владелец поста, а также доступно любому ADMIN, MODERATOR)
    public void deletePost(@PathVariable String title, Principal principal) {
        PostEntity fountPost = postEntityRepository.findByTitle(title).orElseThrow(() -> new RuntimeException("Post not fount"));
        if (fountPost.getUserEntity().getLogin().equals(principal.getName())) {
            postEntityRepository.delete(fountPost);
        } else {
            throw new RuntimeException("Вы не автор поста");
        }
    }

    //- Добавить комментарий посту (доступно USER, ADMIN, MODERATOR)
    public void addComment(@PathVariable String title, @RequestBody CommentEntity newCommentEntity) {
        PostEntity fountPost = postEntityRepository.findByTitle(title).orElseThrow(() -> new RuntimeException("Post not fount"));
        fountPost.getCommentList().add(newCommentEntity);
        postEntityRepository.save(fountPost);
    }

    //- Удалить комментарий у поста (доступно USER, если это владелец поста, а также доступно любому ADMIN, MODERATOR)
    public void deleteComment(@PathVariable String title, @RequestBody CommentEntity newCommentEntity, Principal principal) {
        PostEntity foundPost = postEntityRepository.findByTitle(title).orElseThrow(() -> new RuntimeException("Post not fount"));
        if (foundPost.getUserEntity().getLogin().equals(principal.getName())) {
            foundPost.getCommentList().remove(newCommentEntity);
            postEntityRepository.save(foundPost);
        } else {
            throw new RuntimeException("Вы не автор поста");
        }
    }

    //- Получить все посты автора (по логину) (доступно всем)
    public List<PostEntity> getAllPostByLogin(@PathVariable String login) {
        UserEntity fountUser = userRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("User not fount"));
        List<PostEntity> posts = fountUser.getPosts();
        return posts;
    }

    // Получить все посты за определенную дату (доступно всем)
    public List<PostEntity> getAllPostByDate(@PathVariable String date) {
        List<PostEntity> posts = postEntityRepository.findAllByCreationDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd|MM|yyyy")));
        return posts;
    }
}





















