package site.ahzx.chazuo.domain.VO;

import lombok.Data;
import site.ahzx.chazuo.domain.enums.PricingTypeEnum;

@Data
public class PricingStandardVO {
    private Long id;
    private String name;
    private PricingTypeEnum type;
    private Boolean isActive;
}
