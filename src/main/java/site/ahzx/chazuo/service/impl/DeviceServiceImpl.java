package site.ahzx.chazuo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public int updateDevice(DeviceBO deviceBO) {
        DevicePO devicePO = new DevicePO();
        devicePO.setId(deviceBO.getId());
        devicePO.setDeviceNo(deviceBO.getDeviceNo());
        devicePO.setDeviceName(deviceBO.getDeviceName());
        devicePO.setPricingStandardId(deviceBO.getPricingStandardId());
        devicePO.setStatus(deviceBO.getStatus());
        devicePO.setUpdatedAt(new Date());
        return deviceMapper.updateById(devicePO);
    }

    @Override
    public int deleteDevice(Long id) {
        return deviceMapper.deleteById(id);
    }

    @Override
    public DeviceVO getDeviceDetail(Long id) {
        DevicePO devicePO = deviceMapper.selectById(id);
        if (devicePO == null) {
            return null;
        }
        return convertToVO(devicePO);
    }

    @Override
    public PageInfo<DeviceVO> getDeviceList(String phone, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<DevicePO> devicePOs = deviceMapper.selectListByOpenid(phone);
        if (devicePOs == null) {
            return null;
        }
        List<DeviceVO> deviceVOs = devicePOs.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        return new PageInfo<>(deviceVOs);
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
