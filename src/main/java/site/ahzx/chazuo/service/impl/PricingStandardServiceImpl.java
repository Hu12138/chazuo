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
    public com.github.pagehelper.PageInfo<PricingStandardVO> getPricingStandardList(String openid, Integer pageNum, Integer pageSize) {
        com.github.pagehelper.PageHelper.startPage(pageNum, pageSize);
        List<PricingStandardPO> pos = pricingStandardMapper.selectList(openid);
        List<PricingStandardVO> vos = pos.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        return new com.github.pagehelper.PageInfo<>(vos);
    }

    @Override
    public void addPricingStandard(PricingStandardBO bo) {
        // 转换并保存所有字段到合并后的PO
        PricingStandardPO standardPO = convertToPO(bo);
        log.debug("standardPO: {}", standardPO);
        pricingStandardMapper.insert(standardPO);
    }

    private PricingStandardPO convertToPO(PricingStandardBO bo) {
        PricingStandardPO po = new PricingStandardPO();
        po.setName(bo.getName());
        po.setType(bo.getType());
        po.setIsActive(bo.getIsActive());
        po.setCreatedBy(bo.getCreatedBy());
        
        // 设置收费类型相关字段
        switch(bo.getType()) {
            case BY_ENERGY:
                po.setHasServiceFee(bo.getHasServiceFee());
                po.setServiceFeePerUnit(bo.getServiceFeePerUnit());
                po.setEnergyFeePerUnit(bo.getEnergyFeePerUnit());
                break;
            case BY_TIME:
                po.setTimeUnit(bo.getTimeUnit());
                po.setTimePerYuan(bo.getTimePerYuan());
                po.setTimeUnitPerYuan(bo.getTimeUnitPerYuan());
                break;
            case BY_AMOUNT:
                po.setAmountTimeUnit(bo.getTimeUnit());
                po.setAmountPerUnit(bo.getAmountPerUnit());
                break;
        }
        return po;
    }

    private PricingStandardVO convertToVO(PricingStandardPO po) {
        PricingStandardVO vo = new PricingStandardVO();
        vo.setId(po.getId());
        vo.setName(po.getName());
        vo.setType(po.getType());
        vo.setIsActive(po.getIsActive());
        
        // 设置收费类型相关字段
        vo.setHasServiceFee(po.getHasServiceFee());
        vo.setServiceFeePerUnit(po.getServiceFeePerUnit());
        vo.setEnergyFeePerUnit(po.getEnergyFeePerUnit());
        vo.setTimeUnit(po.getTimeUnit());
        vo.setTimePerYuan(po.getTimePerYuan());
        vo.setTimeUnitPerYuan(po.getTimeUnitPerYuan());
        vo.setAmountTimeUnit(po.getAmountTimeUnit());
        vo.setAmountPerUnit(po.getAmountPerUnit());
        
        return vo;
    }

}
