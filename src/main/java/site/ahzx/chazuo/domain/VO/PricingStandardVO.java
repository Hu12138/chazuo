package site.ahzx.chazuo.domain.VO;

import lombok.Data;
import site.ahzx.chazuo.domain.enums.PricingTypeEnum;
import site.ahzx.chazuo.domain.enums.TimeUnitEnum;
import java.math.BigDecimal;

@Data
public class PricingStandardVO {
    private Long id;
    private String name;
    private PricingTypeEnum type;
    private Boolean isActive;
    
    // 按电量收费字段
    private Boolean hasServiceFee;
    private BigDecimal serviceFeePerUnit;
    private BigDecimal energyFeePerUnit;
    
    // 按时间收费字段
    private TimeUnitEnum timeUnit;
    private Integer timePerYuan;
    private TimeUnitEnum timeUnitPerYuan;
    
    // 按金额收费字段
    private TimeUnitEnum amountTimeUnit;
    private BigDecimal amountPerUnit;
}
