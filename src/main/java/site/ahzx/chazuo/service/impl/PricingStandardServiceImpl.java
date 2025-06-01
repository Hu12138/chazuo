package site.ahzx.chazuo.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import site.ahzx.chazuo.domain.BO.*;
import site.ahzx.chazuo.domain.PO.*;
import site.ahzx.chazuo.domain.VO.*;
import site.ahzx.chazuo.mapper.PricingStandardMapper;
import site.ahzx.chazuo.service.PricingStandardService;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PricingStandardServiceImpl implements PricingStandardService {

    private static final Logger log = LoggerFactory.getLogger(PricingStandardServiceImpl.class);
    private final PricingStandardMapper pricingStandardMapper;

    @Override
    public void updatePricingStandard(PricingStandardBO pricingStandardBO) {
        PricingStandardPO po = convertToPO(pricingStandardBO);
        pricingStandardMapper.updateById(po);
    }

    @Override
    public void deletePricingStandard(Long id) {
        pricingStandardMapper.deleteById(id);
    }

    @Override
    public PricingStandardVO getPricingStandardDetail(Long id) {
        PricingStandardPO po = pricingStandardMapper.selectById(id);
        return convertToVO(po);
    }

    @Override
    public List<PricingStandardVO> getPricingStandardList(String openid) {
        List<PricingStandardPO> pos = pricingStandardMapper.selectList(openid);
        return pos.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public void addPricingStandard(PricingStandardBO bo) {
        // 1. 保存收费标准基本信息
        PricingStandardPO standardPO = convertToPO(bo);
        log.debug("standardPO: " + standardPO);
        pricingStandardMapper.insert(standardPO);
        
        // 2. 根据收费类型保存具体定价规则
        switch(bo.getType()) {
            case BY_ENERGY:
                PricingByEnergyPO energyPO = new PricingByEnergyPO();
                energyPO.setStandardId(standardPO.getId());
                energyPO.setHasServiceFee(bo.getHasServiceFee());
                energyPO.setServiceFeePerUnit(bo.getServiceFeePerUnit());
                energyPO.setEnergyFeePerUnit(bo.getEnergyFeePerUnit());
                energyPO.setCreatedBy(bo.getCreatedBy());
                pricingStandardMapper.insertPricingByEnergy(energyPO);
                break;
                
            case BY_TIME:
                PricingByTimePO timePO = new PricingByTimePO();
                timePO.setStandardId(standardPO.getId());
                timePO.setTimeUnit(bo.getTimeUnit());
                timePO.setTimePerYuan(bo.getTimePerYuan());
                timePO.setTimeUnitPerYuan(bo.getTimeUnitPerYuan());
                timePO.setCreatedBy(bo.getCreatedBy());
                pricingStandardMapper.insertPricingByTime(timePO);
                break;
                
            case BY_AMOUNT:
                PricingByAmountPO amountPO = new PricingByAmountPO();
                amountPO.setStandardId(standardPO.getId());
                amountPO.setTimeUnit(bo.getTimeUnit());
                amountPO.setAmountPerUnit(bo.getAmountPerUnit());
                amountPO.setCreatedBy(bo.getCreatedBy());
                pricingStandardMapper.insertPricingByAmount(amountPO);
                break;
        }
    }

    private PricingStandardPO convertToPO(PricingStandardBO bo) {
        PricingStandardPO po = new PricingStandardPO();
        po.setName(bo.getName());
        po.setType(bo.getType());
        po.setIsActive(bo.getIsActive());
        po.setCreatedBy(bo.getCreatedBy());
        return po;
    }

    private PricingStandardVO convertToVO(PricingStandardPO po) {
        PricingStandardVO vo = new PricingStandardVO();
        vo.setId(po.getId());
        vo.setName(po.getName());
        vo.setType(po.getType());
        vo.setIsActive(po.getIsActive());
        return vo;
    }

}
