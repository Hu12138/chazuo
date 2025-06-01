package site.ahzx.chazuo.domain.PO;

import lombok.Data;
import site.ahzx.chazuo.domain.enums.PricingTypeEnum;
import site.ahzx.chazuo.domain.enums.TimeUnitEnum;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PricingStandardPO {
    private Long id;
    private String name;
    private PricingTypeEnum type;
    private Boolean isActive;
    private Long createdBy;
    private Date createdAt;
    private Date updatedAt;

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
