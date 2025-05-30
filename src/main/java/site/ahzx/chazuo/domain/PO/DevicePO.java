package site.ahzx.chazuo.domain.PO;

import lombok.Data;
import java.util.Date;

@Data
public class DevicePO {
    private Long id;
    private String deviceNo;
    private String deviceName;
    private Long pricingStandardId;
    private Integer status; // 0-禁用,1-启用
    private Long createdBy;
    private Date createdAt;
    private Date updatedAt;
}
