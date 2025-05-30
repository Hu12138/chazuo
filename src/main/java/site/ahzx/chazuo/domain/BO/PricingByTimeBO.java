package site.ahzx.chazuo.domain.BO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import site.ahzx.chazuo.domain.enums.TimeUnitEnum;

@Data
public class PricingByTimeBO {
    @NotNull(message = "标准ID不能为空")
    private Long standardId;
    
    @NotNull(message = "时间单位不能为空")
    private TimeUnitEnum timeUnit;
    
    @NotNull(message = "每元时间不能为空")
    private Integer timePerYuan;
    
    @NotNull(message = "时间单位不能为空")
    private TimeUnitEnum timeUnitPerYuan;
}
