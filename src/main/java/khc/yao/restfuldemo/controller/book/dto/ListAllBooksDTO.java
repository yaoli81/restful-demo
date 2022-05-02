package khc.yao.restfuldemo.controller.book.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import khc.yao.restfuldemo.common.dto.BasicResponseDTO;
import khc.yao.restfuldemo.common.enumeration.YNFlagEnum;
import khc.yao.restfuldemo.controller.book.dto.common.BookBean;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListAllBooksDTO extends BasicResponseDTO {
    private List<BookBean> data;

    public ListAllBooksDTO() {
        super();
        setSuccess(YNFlagEnum.Y.name());
    }
}
