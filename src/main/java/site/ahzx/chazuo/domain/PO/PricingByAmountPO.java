package site.ahzx.chazuo.domain.PO;

import lombok.Data;
import site.ahzx.chazuo.domain.enums.TimeUnitEnum;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PricingByAmountPO {
    private Long id;
    private Long standardId;
    private TimeUnitEnum timeUnit;
    private BigDecimal amountPerUnit;
    private Long createdBy;
    private Date createdAt;
    private Date updatedAt;
}
