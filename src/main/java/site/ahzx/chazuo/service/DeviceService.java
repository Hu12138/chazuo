package site.ahzx.chazuo.service;

import site.ahzx.chazuo.domain.BO.DeviceBO;
import site.ahzx.chazuo.domain.VO.DeviceVO;
import com.github.pagehelper.PageInfo;

public interface DeviceService {
    void addDevice(DeviceBO deviceBO);
    int updateDevice(DeviceBO deviceBO);
    int deleteDevice(Long id);
    DeviceVO getDeviceDetail(Long id);
    PageInfo<DeviceVO> getDeviceList(String phone, Integer pageNum, Integer pageSize);
}
