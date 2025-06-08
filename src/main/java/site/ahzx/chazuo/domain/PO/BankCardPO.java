package site.ahzx.chazuo.domain.PO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BankCardPO {
    private Long id;
    private Long userId;
    private String cardNumber;
    private String bankName;
    private String cardType;
    private String cardHolder;
    private String phone;
    private Boolean isDefault;
    private Integer status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
