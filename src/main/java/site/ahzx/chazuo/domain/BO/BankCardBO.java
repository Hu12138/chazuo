package site.ahzx.chazuo.domain.BO;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;

@Data
public class BankCardBO {
    private Long id;
    private Long userId;

    @NotBlank(message = "卡号不能为空")
    @Pattern(regexp = "^\\d{16,19}$", message = "卡号格式不正确")
    private String cardNumber;

    @NotBlank(message = "银行名称不能为空")
    private String bankName;

    @NotBlank(message = "卡类型不能为空")
    private String cardType;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String cardPhone;

    @NotBlank(message = "持卡人姓名不能为空")
    private String cardHolder;

    @NotBlank(message = "身份证号不能为空")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[0-9Xx]$", message = "身份证号格式不正确")
    private String idCard;

    @NotBlank(message = "省份不能为空")
    private String province;

    @NotBlank(message = "城市不能为空")
    private String city;
    
    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = "^\\d{6}$", message = "验证码必须是6位数字")
    private String code;

}
