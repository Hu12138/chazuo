package site.ahzx.chazuo.domain.PO;

import lombok.Data;

import java.util.Date;

@Data
public class BankCardPO {

    private Long id;

    private String userId;

    private String cardNumber;

    private String bankName;

    private String cardType;

    private String cardHolder;

    private String cardPhone;

    private String idCard;

    private String province;

    private String city;

    private Integer status;

    private Date createdTime;

    private Date updatedTime;
}