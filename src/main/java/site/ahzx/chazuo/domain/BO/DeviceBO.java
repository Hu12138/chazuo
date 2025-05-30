package site.ahzx.chazuo.domain.BO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeviceBO {
    @NotBlank(message = "设备编号不能为空")
    private String deviceNo;
    
    @NotBlank(message = "设备名称不能为空") 
    private String deviceName;
    
    @NotNull(message = "收费标准ID不能为空")
    private Long pricingStandardId;
    
    private Integer status = 1;
}
