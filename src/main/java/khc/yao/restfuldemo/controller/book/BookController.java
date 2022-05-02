package khc.yao.restfuldemo.controller.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import khc.yao.restfuldemo.common.exception.ISBNNotVaildException;
import khc.yao.restfuldemo.common.util.ISBNUtil;
import khc.yao.restfuldemo.controller.book.Input.BookInput;
import khc.yao.restfuldemo.controller.book.dto.AddBookDTO;
import khc.yao.restfuldemo.controller.book.dto.DeleteBookDTO;
import khc.yao.restfuldemo.controller.book.dto.ListAllBooksDTO;
import khc.yao.restfuldemo.controller.book.dto.UpdateBookDTO;
import khc.yao.restfuldemo.service.book.BookService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    /**
     * 新增一本書籍
     */
    @PostMapping
    @Operation(summary = "新增一本書籍")
    public @ResponseBody AddBookDTO insertBook(@Validated @RequestBody BookInput bookInput) {
        if (ISBNUtil.isValid(bookInput.getISBN())) {
            return service.insertBook(bookInput);
        }
        throw new ISBNNotVaildException();
    }

    /**
     * 刪除一本書籍
     */
    @DeleteMapping("/{isbn}")
    @Operation(summary = "刪除一本書籍")
    public DeleteBookDTO deleteBook(
            @Schema(required = true, description = "格式 ISBN 13 或 ISBN 10 皆可", example = "978-986-7794-52-9")
            @PathVariable(value = "isbn") String ISBN) {
        if (ISBNUtil.isValid(ISBN)) {
            return service.deleteBook(ISBN);
        }
        throw new ISBNNotVaildException();
    }

    /**
     * 更新一本書籍資料
     */
    @PutMapping
    @Operation(summary = "更新一本書籍資料")
    public UpdateBookDTO updateBook(@Validated @RequestBody BookInput bookInput) {
        if (ISBNUtil.isValid(bookInput.getISBN())) {
            return service.updateBook(bookInput);
        }
        throw new ISBNNotVaildException();
    }

    /**
     * 列出所有書籍
     */
    @GetMapping
    @Operation(summary = "列出所有書籍")
    public ListAllBooksDTO listAllBooks() {
        return service.listAllBooks();
    }
}
