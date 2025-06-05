package site.ahzx.chazuo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.ahzx.chazuo.domain.PO.DevicePO;
import java.util.List;

@Mapper
public interface DeviceMapper {
    int insert(DevicePO device);
    int updateById(DevicePO device);
    int deleteById(Long id);
    DevicePO selectById(Long id);
    List<DevicePO> selectList();
    List<DevicePO> selectListByOpenid(@Param("openid") String openid);
}
