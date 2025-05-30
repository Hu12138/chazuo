package site.ahzx.chazuo.domain.VO;

import lombok.Data;

@Data
public class PricingByTimeVO {
    private Long id;
    private Long standardId;
    private String timeUnit;
    private Integer timePerYuan;
    private String timeUnitPerYuan;
}
