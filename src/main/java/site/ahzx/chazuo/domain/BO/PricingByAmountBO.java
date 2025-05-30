package site.ahzx.chazuo.domain.BO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import site.ahzx.chazuo.domain.enums.TimeUnitEnum;
import java.math.BigDecimal;

@Data
public class PricingByAmountBO {
    @NotNull(message = "标准ID不能为空")
    private Long standardId;
    
    @NotNull(message = "时间单位不能为空")
    private TimeUnitEnum timeUnit;
    
    @NotNull(message = "每单位时间价格不能为空")
    private BigDecimal amountPerUnit;
}
