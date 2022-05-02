package khc.yao.restfuldemo.controller.book.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookBean {
    @JsonProperty("isbn")
    private String ISBN;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("isbn10")
    private String ISBN10;

    private String name;

    private String author;

    private String translator;

    private String publisher;

    @JsonProperty("publication_date")
    private LocalDate publicationDate;

    private String price;
}
