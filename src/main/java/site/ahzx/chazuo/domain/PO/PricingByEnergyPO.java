package site.ahzx.chazuo.domain.PO;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PricingByEnergyPO {
    private Long id;
    private Long standardId;
    private Boolean hasServiceFee;
    private BigDecimal serviceFeePerUnit;
    private BigDecimal energyFeePerUnit;
    private Long createdBy;
    private Date createdAt;
    private Date updatedAt;
}
