package site.ahzx.chazuo.domain.BO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PricingByEnergyBO {
    @NotNull(message = "标准ID不能为空")
    private Long standardId;
    
    private Boolean hasServiceFee = false;
    private BigDecimal serviceFeePerUnit = BigDecimal.ZERO;
    
    @NotNull(message = "电费单价不能为空")
    private BigDecimal energyFeePerUnit;
}
