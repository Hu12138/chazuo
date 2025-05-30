package site.ahzx.chazuo.domain.PO;

import lombok.Data;
import site.ahzx.chazuo.domain.enums.PricingTypeEnum;
import java.util.Date;

@Data
public class PricingStandardPO {
    private Long id;
    private String name;
    private PricingTypeEnum type; // enum('by_energy','by_time','by_amount')
    private Boolean isActive;
    private Long createdBy;
    private Date createdAt;
    private Date updatedAt;
}
