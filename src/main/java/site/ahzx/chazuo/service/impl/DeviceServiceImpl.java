package site.ahzx.chazuo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.ahzx.chazuo.domain.BO.DeviceBO;
import site.ahzx.chazuo.domain.PO.DevicePO;
import site.ahzx.chazuo.domain.VO.DeviceVO;
import site.ahzx.chazuo.mapper.DeviceMapper;
import site.ahzx.chazuo.service.DeviceService;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceMapper deviceMapper;

    @Override
    public void addDevice(DeviceBO deviceBO) {
        DevicePO devicePO = new DevicePO();
        devicePO.setDeviceNo(deviceBO.getDeviceNo());
        devicePO.setDeviceName(deviceBO.getDeviceName());
        devicePO.setPricingStandardId(deviceBO.getPricingStandardId());
        devicePO.setStatus(deviceBO.getStatus());
        devicePO.setCreatedBy(deviceBO.getCreatedBy());
        devicePO.setCreatedAt(new Date());
        devicePO.setUpdatedAt(new Date());
        deviceMapper.insert(devicePO);
    }

    @Override
    public void updateDevice(DeviceBO deviceBO) {
        DevicePO devicePO = new DevicePO();
        devicePO.setId(deviceBO.getId());
        devicePO.setDeviceNo(deviceBO.getDeviceNo());
        devicePO.setDeviceName(deviceBO.getDeviceName());
        devicePO.setPricingStandardId(deviceBO.getPricingStandardId());
        devicePO.setStatus(deviceBO.getStatus());
        devicePO.setUpdatedAt(new Date());
        deviceMapper.updateById(devicePO);
    }

    @Override
    public void deleteDevice(Long id) {
        deviceMapper.deleteById(id);
    }

    @Override
    public DeviceVO getDeviceDetail(Long id) {
        DevicePO devicePO = deviceMapper.selectById(id);
        return convertToVO(devicePO);
    }

    @Override
    public List<DeviceVO> getDeviceList() {
        List<DevicePO> devicePOs = deviceMapper.selectList();
        return devicePOs.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    private DeviceVO convertToVO(DevicePO devicePO) {
        DeviceVO deviceVO = new DeviceVO();
        deviceVO.setId(devicePO.getId());
        deviceVO.setDeviceNo(devicePO.getDeviceNo());
        deviceVO.setDeviceName(devicePO.getDeviceName());
        deviceVO.setPricingStandardId(devicePO.getPricingStandardId());
        deviceVO.setStatus(devicePO.getStatus());
        return deviceVO;
    }
}
