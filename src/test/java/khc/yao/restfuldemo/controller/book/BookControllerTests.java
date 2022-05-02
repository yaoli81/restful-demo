package khc.yao.restfuldemo.controller.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import khc.yao.restfuldemo.controller.book.dto.AddBookDTO;
import khc.yao.restfuldemo.controller.book.dto.DeleteBookDTO;
import khc.yao.restfuldemo.controller.book.dto.ListAllBooksDTO;
import khc.yao.restfuldemo.controller.book.dto.UpdateBookDTO;
import khc.yao.restfuldemo.controller.book.dto.common.BookBean;
import khc.yao.restfuldemo.service.book.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class BookControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("Controller 測試：新增一本書籍")
    public void insertBook() throws Exception {
        FakeBookInput fakeBookInput = new FakeBookInput();
        fakeBookInput.setIsbn("9789867794529");
        fakeBookInput.setName("Head First Design Patterns: 深入淺出設計模式");
        fakeBookInput.setAuthor("Eric Freeman, Elisabeth Robson");
        fakeBookInput.setTranslator("蔡學鏞");
        fakeBookInput.setPublisher("美商歐萊禮股份有限公司台灣分公司");
        fakeBookInput.setPublication_date("2005-09-21");
        fakeBookInput.setPrice("792");

        AddBookDTO dto = new AddBookDTO();
        dto.setData(fakeBookInputToBookBean(fakeBookInput));
        doReturn(dto).when(bookService).insertBook(any());

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(fakeBookInput)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is("Y")))
                .andDo(print());
    }

    @Test
    @DisplayName("Controller 測試：刪除一本書籍")
    public void deleteBook() throws Exception {
        FakeBookInput fakeBookInput = new FakeBookInput();
        fakeBookInput.setIsbn("9789867794529");
        fakeBookInput.setName("Head First Design Patterns: 深入淺出設計模式");
        fakeBookInput.setAuthor("Eric Freeman, Elisabeth Robson");
        fakeBookInput.setTranslator("蔡學鏞");
        fakeBookInput.setPublisher("美商歐萊禮股份有限公司台灣分公司");
        fakeBookInput.setPublication_date("2005-09-21");
        fakeBookInput.setPrice("792");

        DeleteBookDTO dto = new DeleteBookDTO();
        dto.setData(fakeBookInputToBookBean(fakeBookInput));
        doReturn(dto).when(bookService).deleteBook(any());

        mockMvc.perform(delete("/api/books/{isbn}", fakeBookInput.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is("Y")))
                .andDo(print());
    }

    @Test
    @DisplayName("Controller 測試：更新一本書籍資料")
    public void updateBook() throws Exception {
        FakeBookInput fakeBookInput = new FakeBookInput();
        fakeBookInput.setIsbn("9789867794529");
        fakeBookInput.setName("Head First Design Patterns: 深入淺出設計模式");
        fakeBookInput.setAuthor("Eric Freeman, Elisabeth Robson");
        fakeBookInput.setTranslator("蔡學鏞");
        fakeBookInput.setPublisher("美商歐萊禮股份有限公司台灣分公司");
        fakeBookInput.setPublication_date("2005-09-21");
        fakeBookInput.setPrice("792");

        UpdateBookDTO dto = new UpdateBookDTO();
        dto.setData(fakeBookInputToBookBean(fakeBookInput));
        doReturn(dto).when(bookService).updateBook(any());

        mockMvc.perform(put("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(fakeBookInput)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is("Y")))
                .andDo(print());
    }

    @Test
    @DisplayName("Controller 測試：列出所有書籍")
    public void listAllBooks() throws Exception {
        ListAllBooksDTO dto = new ListAllBooksDTO();
        List<BookBean> bookBeanList = new ArrayList<>();
        BookBean bookBean1 = new BookBean();
        bookBean1.setISBN("9789867794529");
        bookBean1.setISBN10("9867794524");
        bookBean1.setName("Head First Design Patterns: 深入淺出設計模式");
        bookBean1.setAuthor("Eric Freeman, Elisabeth Robson");
        bookBean1.setTranslator("蔡學鏞");
        bookBean1.setPublisher("美商歐萊禮股份有限公司台灣分公司");
        bookBean1.setPublicationDate(LocalDate.parse("2005-09-21"));
        bookBean1.setPrice("792");
        bookBeanList.add(bookBean1);
        dto.setData(bookBeanList);
        doReturn(dto).when(bookService).listAllBooks();

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is("Y")))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andDo(print());
    }

     static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private BookBean fakeBookInputToBookBean(FakeBookInput fakeBookInput) {
        BookBean bookBean = new BookBean();
        bookBean.setISBN(fakeBookInput.getIsbn());
        bookBean.setName(fakeBookInput.getName());
        bookBean.setAuthor(fakeBookInput.getAuthor());
        bookBean.setTranslator(fakeBookInput.getTranslator());
        bookBean.setPublisher(fakeBookInput.getPublisher());
        bookBean.setPublicationDate(LocalDate.parse(fakeBookInput.getPublication_date()));
        bookBean.setPrice(fakeBookInput.getPrice());
        return bookBean;
    }
}
