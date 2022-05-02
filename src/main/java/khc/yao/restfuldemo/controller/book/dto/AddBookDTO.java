package khc.yao.restfuldemo.controller.book.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import khc.yao.restfuldemo.common.dto.BasicResponseDTO;
import khc.yao.restfuldemo.common.enumeration.YNFlagEnum;
import khc.yao.restfuldemo.controller.book.dto.common.BookBean;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddBookDTO extends BasicResponseDTO {
    private BookBean data;

    public AddBookDTO() {
        super();
        setSuccess(YNFlagEnum.Y.name());
    }
}
