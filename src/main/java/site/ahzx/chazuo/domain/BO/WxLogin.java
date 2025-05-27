package site.ahzx.chazuo.domain.BO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WxLogin {
    @NotBlank(message = "code不能为空")
    private String code;
}
