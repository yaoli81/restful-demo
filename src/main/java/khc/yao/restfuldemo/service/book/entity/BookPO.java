package khc.yao.restfuldemo.service.book.entity;

import khc.yao.restfuldemo.common.constant.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = CommonConstant.TBL_BOOK, schema = CommonConstant.DATABASE_SCHEMA)
public class BookPO {
    @Id
    @Column(name = "isbn")
    private String ISBN;

    @Column(name = "isbn10")
    private String ISBN10;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "translator")
    private String translator;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "price")
    private BigDecimal price;
}
