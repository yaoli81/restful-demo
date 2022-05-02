package khc.yao.restfuldemo.controller.book.Input;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter @Setter
public class BookInput {
    @Schema(required = true, description = "格式 ISBN 13 或 ISBN 10 皆可", example = "978-986-7794-52-9")
    @NotNull(message = "isbn (ISBN) 不能為空。")
    @JsonProperty("isbn")
    private String ISBN;

    @Schema(required = true, description = "書名", example = "Head First Design Patterns: 深入淺出設計模式")
    @NotNull(message = "name (書名) 不能為空。")
    private String name;

    @Schema(required = true, description = "作者", example = "Eric Freeman, Elisabeth Robson")
    @NotNull(message = "author (作者) 不能為空。")
    private String author;

    // 由於 "譯者" 可以為空，直接給預設值
    @Schema(description = "譯者", example = "蔡學鏞")
    private String translator = "";

    @Schema(required = true, description = "出版商", example = "美商歐萊禮股份有限公司台灣分公司")
    @NotNull(message = "publisher (出版商) 不能為空。")
    private String publisher;

    @Schema(required = true, description = "出版日期，格式為 yyyy-MM-dd", example = "2022-05-01")
    @NotNull(message = "publication_date (出版日期) 不能為空。")
    @JsonProperty("publication_date")
    private LocalDate publicationDate;

    @Schema(required = true, description = "定價，可輸入小數。", example = "880.00")
    @NotNull(message = "price (定價) 不能為空。")
    @DecimalMin(value = "0", message = "定價 (price) 格式有誤，請輸入 0 以上的金額 (包含 0)。")
    private String price;
}
