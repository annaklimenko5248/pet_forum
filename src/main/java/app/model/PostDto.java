package app.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
//data transfer object
@Data
@NoArgsConstructor
public class PostDto {
    private String title;//название поста
    private String content;//содержимое поста
    //класс десериализатора LocalDateDeserializer, который должен использоваться для десериализации значения этого поля.
    private String creationDate;//дата создания


}
