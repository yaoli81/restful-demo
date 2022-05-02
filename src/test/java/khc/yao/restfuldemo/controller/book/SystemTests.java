package khc.yao.restfuldemo.controller.book;

import khc.yao.restfuldemo.controller.book.Input.BookInput;
import khc.yao.restfuldemo.controller.book.dto.AddBookDTO;
import khc.yao.restfuldemo.controller.book.dto.ListAllBooksDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@AutoConfigureMockMvc
@SpringBootTest
public class SystemTests {

    @Test
    @DisplayName("RestTemplate 測試，請記得啟動程式。")
    public void systemTests() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://127.0.0.1:8080/api/books";
        BookInput bookInput = new BookInput();
        // ISBN 設定 ISBN 10，並有帶分隔符號
        bookInput.setISBN("986-7794-52-4");
        bookInput.setName("Head First Design Patterns: 深入淺出設計模式");
        bookInput.setAuthor("Eric Freeman, Elisabeth Robson");
        bookInput.setTranslator("蔡學鏞");
        bookInput.setPublisher("美商歐萊禮股份有限公司台灣分公司");
        bookInput.setPublicationDate(LocalDate.parse("2005-09-21"));
        bookInput.setPrice("792");
        // 新增 1 筆書籍資料
        Assertions.assertThat(restTemplate.postForObject(url, bookInput, AddBookDTO.class).getSuccess()).isEqualTo("Y");
        System.out.println("新增 1 筆書籍資料成功");
        // 檢查 ISBN 是否有轉換成 ISBN 13，並沒帶分隔符號
        Assertions.assertThat(restTemplate.getForObject(url, ListAllBooksDTO.class).getData().get(0).getISBN()).isEqualTo("9789867794529");
        System.out.println("ISBN 10 轉換成 ISBN 13 成功");
        // 更新書籍資料
        bookInput.setPublisher("歐萊禮");
        restTemplate.put(url, bookInput);
        // 確認資料是否有更新
        Assertions.assertThat(restTemplate.getForObject(url, ListAllBooksDTO.class).getData().get(0).getPublisher()).isEqualTo("歐萊禮");
        System.out.println("更新 1 筆書籍資料成功");
        // 刪除書籍資料
        restTemplate.delete(url + "/" + bookInput.getISBN());
        // 檢查資料是否有刪除
        Assertions.assertThat(restTemplate.getForObject(url, ListAllBooksDTO.class).getData()).hasSize(0);
        System.out.println("刪除 1 筆書籍資料成功");
    }
}
