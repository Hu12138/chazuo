package site.ahzx.chazuo.domain.VO;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PricingByAmountVO {
    private Long id;
    private Long standardId;
    private String timeUnit;
    private BigDecimal amountPerUnit;
}
