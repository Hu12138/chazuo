package site.ahzx.chazuo.domain.VO;

import lombok.Data;

@Data
public class DeviceVO {
    private Long id;
    private String deviceNo;
    private String deviceName;
    private Long pricingStandardId;
    private Integer status;
}
