package site.ahzx.chazuo.domain.PO;

import lombok.Data;
import site.ahzx.chazuo.domain.enums.TimeUnitEnum;
import java.util.Date;

@Data
public class PricingByTimePO {
    private Long id;
    private Long standardId;
    private TimeUnitEnum timeUnit;
    private Integer timePerYuan;
    private TimeUnitEnum timeUnitPerYuan;
    private Long createdBy;
    private Date createdAt;
    private Date updatedAt;
}
