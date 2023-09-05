package app.web;

import app.service.CommentServiceDao;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceDao commentServiceDao;


    //TODO правильно ли?
    //- Поставить лайк комментарию (доступно USER, ADMIN, MODERATOR)
    @PutMapping("/putLike/date/{date}/like/{like}")
    public void putLike(@PathVariable String date, @PathVariable Integer like) {
//        CommentEntity fountComment = commentEntityRepository.findByCreationDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy")))
//                .orElseThrow(() -> new RuntimeException("Comment not fount"));
//        fountComment.setNumberOfLikes(fountComment.getNumberOfLikes() + like);
//        commentEntityRepository.save(fountComment);
        commentServiceDao.putLike(date, like);
    }
}
