package site.ahzx.chazuo.domain.BO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import site.ahzx.chazuo.domain.enums.PricingTypeEnum;
import site.ahzx.chazuo.domain.enums.TimeUnitEnum;
import java.math.BigDecimal;

@Data
public class PricingStandardBO {
    @NotBlank(message = "收费标准名称不能为空")
    private String name;
    
    @NotNull(message = "收费类型不能为空")
    private PricingTypeEnum type;
    
    private Boolean isActive = true;
    
    // 按电量收费字段
    private Boolean hasServiceFee = false;
    private BigDecimal serviceFeePerUnit = BigDecimal.ZERO;
    private BigDecimal energyFeePerUnit;
    
    // 按时间收费字段
    private TimeUnitEnum timeUnit;
    private Integer timePerYuan;
    private TimeUnitEnum timeUnitPerYuan;
    
    // 按金额收费字段
    private BigDecimal amountPerUnit;
    
    // 公共字段
    private Long createdBy;
    
    // 分页字段
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
