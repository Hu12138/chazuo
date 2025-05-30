package site.ahzx.chazuo.domain.VO;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PricingByEnergyVO {
    private Long id;
    private Long standardId;
    private Boolean hasServiceFee;
    private BigDecimal serviceFeePerUnit;
    private BigDecimal energyFeePerUnit;
}
