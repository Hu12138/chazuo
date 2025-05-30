package site.ahzx.chazuo.domain.BO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import site.ahzx.chazuo.domain.enums.PricingTypeEnum;

@Data
public class PricingStandardBO {
    @NotBlank(message = "收费标准名称不能为空")
    private String name;
    
    @NotNull(message = "收费类型不能为空")
    private PricingTypeEnum type; // by_energy/by_time/by_amount
    
    private Boolean isActive = true;
}
