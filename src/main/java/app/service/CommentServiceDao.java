package app.service;

import app.model.CommentEntity;
import app.repository.CommentEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CommentServiceDao {

   private final CommentEntityRepository commentEntityRepository;

    //- Поставить лайк комментарию (доступно USER, ADMIN, MODERATOR)
    public void putLike(@PathVariable String date, @PathVariable Integer like){
        CommentEntity fountComment = commentEntityRepository.findByCreationDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .orElseThrow(() -> new RuntimeException("Comment not fount"));
        fountComment.setNumberOfLikes(fountComment.getNumberOfLikes() + like);
        commentEntityRepository.save(fountComment);
    }
}
