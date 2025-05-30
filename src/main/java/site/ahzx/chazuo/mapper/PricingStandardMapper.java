package site.ahzx.chazuo.mapper;

import org.apache.ibatis.annotations.Mapper;
import site.ahzx.chazuo.domain.PO.*;

import java.util.List;

@Mapper
public interface PricingStandardMapper {
    int insert(PricingStandardPO pricingStandard);
    int updateById(PricingStandardPO pricingStandard);
    int deleteById(Long id);
    PricingStandardPO selectById(Long id);
    List<PricingStandardPO> selectList();

    int insertPricingByEnergy(PricingByEnergyPO pricingByEnergy);
    int insertPricingByTime(PricingByTimePO pricingByTime);
    int insertPricingByAmount(PricingByAmountPO pricingByAmount);
}
