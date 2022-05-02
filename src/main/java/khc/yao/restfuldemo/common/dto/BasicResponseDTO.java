package khc.yao.restfuldemo.common.dto;

import khc.yao.restfuldemo.common.enumeration.YNFlagEnum;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BasicResponseDTO {
    private String success;
    private String message;

    public BasicResponseDTO() {
        // success 預設失敗 (N)
        success = YNFlagEnum.N.name();
    }
}
