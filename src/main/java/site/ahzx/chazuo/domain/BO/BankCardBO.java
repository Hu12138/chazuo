package site.ahzx.chazuo.domain.BO;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;

@Data
public class BankCardBO {
    private Long id;
    
    @NotBlank(message = "卡号不能为空")
    @Pattern(regexp = "^\\d{16,19}$", message = "卡号格式不正确")
    private String cardNumber;

    @NotBlank(message = "银行名称不能为空")
    private String bankName;

    @NotBlank(message = "卡类型不能为空")
    private String cardType;

    @NotBlank(message = "持卡人姓名不能为空")
    private String cardHolder;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    private boolean defaultCard;
}
