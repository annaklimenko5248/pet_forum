package app.web;

import app.model.CommentEntity;
import app.model.PostDto;
import app.model.PostEntity;
import app.service.PostServiceDao;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor()

public class PostController {
    private final PostServiceDao postServiceDao;

    // Добавить пост (доступно USER, ADMIN, MODERATOR)
    @PostMapping("/savePost")
    public void savePost(@RequestBody PostEntity postEntity){
        //Пример работы с DTO
//        PostEntity postEntity = new PostEntity();
//        postEntity.setContent(postDto.getContent());
//        postEntity.setTitle(postDto.getTitle());
//        postEntity.setCreationDate(LocalDate.parse(postDto.getCreationDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        postServiceDao.savePost(postEntity);

    }
    //Principal Java - это интерфейс
    //Классы, реализующие интерфейс Principal - главный, могут использоваться для аутентификации(процедура проверки подлинности) пользователей,
    // авторизации доступа к ресурсам, в нашем случае обновить пост может только владелец поста

    //- Обновить пост (доступно USER, если это владелец поста, а также доступно любому ADMIN, MODERATOR)
    @PutMapping("/updatePost/title/{title}")
    public void updatePost(@PathVariable String title, @RequestBody PostEntity newPostEntity, Principal principal) {
//        PostEntity foundPost = postEntityRepository.findByTitle(title).orElseThrow(() -> new RuntimeException("Post not fount"));
//        if (foundPost.getUserEntity().getLogin().equals(principal.getName())) {
//            foundPost.setTitle(newPostEntity.getTitle());
//            foundPost.setContent(newPostEntity.getContent());
//            foundPost.setCreationDate(newPostEntity.getCreationDate());
//            postEntityRepository.save(foundPost);
//        } else {
//            throw new RuntimeException("Вы не автор поста");
//        }
        postServiceDao.updatePost(title, newPostEntity, principal);
    }

    //- Удалить пост (доступно USER, если это владелец поста, а также доступно любому ADMIN, MODERATOR)
    @DeleteMapping("/deletePost/title/{title}")
    public void deletePost(@PathVariable String title, Principal principal) {
//        PostEntity fountPost = postEntityRepository.findByTitle(title).orElseThrow(() -> new RuntimeException("Post not fount"));
//        if (fountPost.getUserEntity().getLogin().equals(principal.getName())) {
//            postEntityRepository.delete(fountPost);
//        } else {
//            throw new RuntimeException("Вы не автор поста");
//        }
        postServiceDao.deletePost(title, principal);
    }



    //- Добавить комментарий посту (доступно USER, ADMIN, MODERATOR)
    @PutMapping("/addComment/title/{title}")
    public void addComment(@PathVariable String title, @RequestBody CommentEntity newCommentEntity) {
//        PostEntity fountPost = postEntityRepository.findByTitle(title).orElseThrow(() -> new RuntimeException("Post not fount"));
//        fountPost.getCommentList().add(newCommentEntity);
//        postEntityRepository.save(fountPost);
        postServiceDao.addComment(title, newCommentEntity);
    }

    //- Удалить комментарий у поста (доступно USER, если это владелец поста, а также доступно любому ADMIN, MODERATOR)
    @DeleteMapping("/deleteComment/title/{title}")
    public void deleteComment(@PathVariable String title, @RequestBody CommentEntity newCommentEntity, Principal principal) {
//        PostEntity foundPost = postEntityRepository.findByTitle(title).orElseThrow(() -> new RuntimeException("Post not fount"));
//        if (foundPost.getUserEntity().getLogin().equals(principal.getName())) {
//            foundPost.getCommentList().remove(newCommentEntity);
//            postEntityRepository.save(foundPost);
//        } else {
//            throw new RuntimeException("Вы не автор поста");
//        }
        postServiceDao.deleteComment(title, newCommentEntity, principal);
    }

    //- Получить все посты автора (по логину) (доступно всем)
    @GetMapping("/login/{login}")
    public List<PostEntity> getAllPotsByLogin(@PathVariable String login) {
        return postServiceDao.getAllPostByLogin(login);
//        UserEntity fountUser = userRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("User not fount"));
//        List<PostEntity> posts = fountUser.getPosts();
//        return posts;
    }

    // Получить все посты за определенную дату (доступно всем)
    @GetMapping("/date/{date}")
    public List<PostEntity> getAllPostByDate(@PathVariable String date) {
//        List<PostEntity> posts = postEntityRepository.findAllByCreationDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd|MM|yyyy")));
        return postServiceDao.getAllPostByDate(date);
    }



}
