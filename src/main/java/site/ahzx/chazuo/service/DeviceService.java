package site.ahzx.chazuo.service;

import site.ahzx.chazuo.domain.BO.DeviceBO;
import site.ahzx.chazuo.domain.VO.DeviceVO;
import java.util.List;

public interface DeviceService {
    void addDevice(DeviceBO deviceBO);
    void updateDevice(DeviceBO deviceBO);
    void deleteDevice(Long id);
    DeviceVO getDeviceDetail(Long id);
    List<DeviceVO> getDeviceList();
}
