package site.ahzx.chazuo.service;

import site.ahzx.chazuo.domain.BO.*;
import site.ahzx.chazuo.domain.VO.PricingStandardVO;
import java.util.List;

public interface PricingStandardService {
    void addPricingStandard(PricingStandardBO pricingStandardBO);
    void updatePricingStandard(PricingStandardBO pricingStandardBO);
    void deletePricingStandard(Long id);
    PricingStandardVO getPricingStandardDetail(Long id);
    List<PricingStandardVO> getPricingStandardList();
}
