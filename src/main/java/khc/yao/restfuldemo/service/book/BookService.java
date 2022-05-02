package khc.yao.restfuldemo.service.book;

import khc.yao.restfuldemo.common.enumeration.YNFlagEnum;
import khc.yao.restfuldemo.common.util.ISBNUtil;
import khc.yao.restfuldemo.controller.book.Input.BookInput;
import khc.yao.restfuldemo.controller.book.dto.UpdateBookDTO;
import khc.yao.restfuldemo.controller.book.dto.common.BookBean;
import khc.yao.restfuldemo.controller.book.dto.AddBookDTO;
import khc.yao.restfuldemo.controller.book.dto.DeleteBookDTO;
import khc.yao.restfuldemo.controller.book.dto.ListAllBooksDTO;
import khc.yao.restfuldemo.service.book.entity.BookDAO;
import khc.yao.restfuldemo.service.book.entity.BookPO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookDAO bookDAO;

    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public Optional<BookPO> getBookByISBN(String ISBN) {
        return bookDAO.findById(ISBN);
    }

    public AddBookDTO insertBook(BookInput bookInput) {
        AddBookDTO returnDTO = new AddBookDTO();
        String ISBN13 = ISBNUtil.getISBN13WithoutSeparator(bookInput.getISBN());
        // 資料庫中，無此 ISBN 的資料才進行新增
        if (getBookByISBN(ISBN13).isPresent()) {
            // 無此 ISBN 的資料無法更新
            returnDTO.setSuccess(YNFlagEnum.N.name());
            returnDTO.setMessage("新增失敗，此筆書籍資料已存在 (ISBN 重複)。");
            return returnDTO;
        }
        BookPO bookPO = BookInputToBookPO(bookInput);
        // 如果使用者輸入 ISBN10，要記錄到資料庫中
        if (ISBNUtil.isISBN10(bookInput.getISBN())) {
            bookPO.setISBN10(ISBNUtil.getISBN10WithoutSeparator(bookInput.getISBN()));
        } else {
            bookPO.setISBN10("");
        }
        // 資料庫紀錄的 ISBN 皆為沒有分隔符號的純數字
        bookPO.setISBN(ISBN13);
        bookPO = bookDAO.save(bookPO);
        returnDTO.setData(BookPOToBookBean(bookPO));
        return returnDTO;
    }

    public DeleteBookDTO deleteBook(String ISBN) {
        DeleteBookDTO returnDTO = new DeleteBookDTO();
        // 資料庫紀錄的 ISBN 皆為沒有分隔符號的純數字
        String ISBN13 = ISBNUtil.getISBN13WithoutSeparator(ISBN);
        Optional<BookPO> optionalBookPO = getBookByISBN(ISBN13);
        // 資料庫中，有此 ISBN 的資料才進行刪除
        if (optionalBookPO.isPresent()) {
            bookDAO.deleteById(ISBN13);
            returnDTO.setData(BookPOToBookBean(optionalBookPO.get()));
            return returnDTO;
        }
        // 無此 ISBN 的資料無法刪除
        returnDTO.setSuccess(YNFlagEnum.N.name());
        returnDTO.setMessage("刪除失敗，查無此筆書籍資料 (查無此筆 ISBN)。");
        return returnDTO;
    }

    public UpdateBookDTO updateBook(BookInput bookInput) {
        UpdateBookDTO returnDTO = new UpdateBookDTO();
        String ISBN13 = ISBNUtil.getISBN13WithoutSeparator(bookInput.getISBN());
        Optional<BookPO> optionalBookPO = getBookByISBN(ISBN13);
        // 資料庫中，有此 ISBN 的資料才進行更新
        if (optionalBookPO.isPresent()) {
            BookPO po = BookInputToBookPO(bookInput);
            // 如果使用者輸入 ISBN10，要記錄到資料庫中
            if (ISBNUtil.isISBN10(bookInput.getISBN())) {
                po.setISBN10(ISBNUtil.getISBN10WithoutSeparator(bookInput.getISBN()));
            } else {
                po.setISBN10(optionalBookPO.get().getISBN10());
            }
            // 資料庫紀錄的 ISBN 皆為沒有分隔符號的純數字
            po.setISBN(ISBN13);
            BookPO bookPO = bookDAO.save(po);
            returnDTO.setData(BookPOToBookBean(bookPO));
            return returnDTO;
        }
        // 無此 ISBN 的資料無法更新
        returnDTO.setSuccess(YNFlagEnum.N.name());
        returnDTO.setMessage("更新失敗，查無此筆資料 (查無此筆 ISBN)。");
        return returnDTO;
    }

    public ListAllBooksDTO listAllBooks() {
        ListAllBooksDTO returnDTO = new ListAllBooksDTO();
        List<BookPO> bookPOList = bookDAO.findAll();
        List<BookBean> bookBeanList = new ArrayList<>();
        for (BookPO po : bookPOList) {
            bookBeanList.add(BookPOToBookBean(po));
        }
        returnDTO.setData(bookBeanList);
        return returnDTO;
    }

    private BookBean BookPOToBookBean(BookPO po) {
        BookBean bookBean = new BookBean();
        bookBean.setISBN(po.getISBN());
        bookBean.setISBN10(po.getISBN10());
        bookBean.setName(po.getName());
        bookBean.setAuthor(po.getAuthor());
        bookBean.setTranslator(po.getTranslator());
        bookBean.setPublisher(po.getPublisher());
        bookBean.setPublicationDate(po.getPublicationDate());
        bookBean.setPrice(po.getPrice().toString());
        return bookBean;
    }

    private BookPO BookInputToBookPO(BookInput bookInput) {
        BookPO bookPO = new BookPO();
        bookPO.setISBN(bookInput.getISBN());
        bookPO.setName(bookInput.getName());
        bookPO.setAuthor(bookInput.getAuthor());
        bookPO.setTranslator(bookInput.getTranslator());
        bookPO.setPublisher(bookInput.getPublisher());
        bookPO.setPublicationDate(bookInput.getPublicationDate());
        bookPO.setPrice(new BigDecimal(bookInput.getPrice()));
        return bookPO;
    }
}
