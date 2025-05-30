package site.ahzx.chazuo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.ahzx.chazuo.domain.BO.*;
import site.ahzx.chazuo.domain.PO.*;
import site.ahzx.chazuo.domain.VO.*;
import site.ahzx.chazuo.domain.enums.PricingTypeEnum;
import site.ahzx.chazuo.mapper.PricingStandardMapper;
import site.ahzx.chazuo.service.PricingStandardService;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PricingStandardServiceImpl implements PricingStandardService {

    private final PricingStandardMapper pricingStandardMapper;

    @Override
    public void addPricingStandard(PricingStandardBO pricingStandardBO) {
        PricingStandardPO po = convertToPO(pricingStandardBO);
        pricingStandardMapper.insert(po);
    }

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
    public List<PricingStandardVO> getPricingStandardList() {
        List<PricingStandardPO> pos = pricingStandardMapper.selectList();
        return pos.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public void addPricingByEnergy(PricingByEnergyBO pricingByEnergyBO) {
        PricingByEnergyPO po = convertToPO(pricingByEnergyBO);
        pricingStandardMapper.insertPricingByEnergy(po);
    }

    @Override
    public void addPricingByTime(PricingByTimeBO pricingByTimeBO) {
        PricingByTimePO po = convertToPO(pricingByTimeBO);
        pricingStandardMapper.insertPricingByTime(po);
    }

    @Override
    public void addPricingByAmount(PricingByAmountBO pricingByAmountBO) {
        PricingByAmountPO po = convertToPO(pricingByAmountBO);
        pricingStandardMapper.insertPricingByAmount(po);
    }

    private PricingStandardPO convertToPO(PricingStandardBO bo) {
        PricingStandardPO po = new PricingStandardPO();
        po.setName(bo.getName());
        po.setType(bo.getType());
        po.setIsActive(bo.getIsActive());
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

    private PricingByEnergyPO convertToPO(PricingByEnergyBO bo) {
        PricingByEnergyPO po = new PricingByEnergyPO();
        po.setStandardId(bo.getStandardId());
        po.setHasServiceFee(bo.getHasServiceFee());
        po.setServiceFeePerUnit(bo.getServiceFeePerUnit());
        po.setEnergyFeePerUnit(bo.getEnergyFeePerUnit());
        return po;
    }

    private PricingByTimePO convertToPO(PricingByTimeBO bo) {
        PricingByTimePO po = new PricingByTimePO();
        po.setStandardId(bo.getStandardId());
        po.setTimeUnit(bo.getTimeUnit());
        po.setTimePerYuan(bo.getTimePerYuan());
        po.setTimeUnitPerYuan(bo.getTimeUnitPerYuan());
        return po;
    }

    private PricingByAmountPO convertToPO(PricingByAmountBO bo) {
        PricingByAmountPO po = new PricingByAmountPO();
        po.setStandardId(bo.getStandardId());
        po.setTimeUnit(bo.getTimeUnit());
        po.setAmountPerUnit(bo.getAmountPerUnit());
        return po;
    }
}
