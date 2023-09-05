package app.configuration.other_configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//@JsonComponent//Для того, чтобы Spring понимал, что именно наш класс нужно использовать для (де)сериализации
//В методе deserialize мы получаем строку из JSON-элемента, затем парсим ее обратно в объект LocalDate с использованием
// того же форматтера.
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String dateStr = jsonParser.getValueAsString();
        return LocalDate.parse(dateStr, formatter);
    }
}
