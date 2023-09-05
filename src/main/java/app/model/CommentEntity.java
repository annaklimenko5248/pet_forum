package app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "comment")
@NoArgsConstructor
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String text;
    private Integer numberOfLikes;//количество лайков
    private LocalDate creationDate;//дата создания комментария

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;
}
