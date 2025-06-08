package site.ahzx.chazuo.domain.VO;

import lombok.Data;

@Data
public class BankCardVO {
    private Long id;
    private String cardNumber;
    private String bankName;
    private String cardType;
    private String cardHolder;
    private Boolean isDefault;
    
    // 只显示卡号后4位
    public String getCardNumber() {
        if (cardNumber != null && cardNumber.length() > 4) {
            return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
        }
        return cardNumber;
    }
}
