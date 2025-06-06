package site.ahzx.chazuo.service;

import site.ahzx.chazuo.domain.BO.*;
import site.ahzx.chazuo.domain.VO.PricingStandardVO;
import java.util.List;

public interface PricingStandardService {
    void addPricingStandard(PricingStandardBO pricingStandardBO);
    int updatePricingStandard(PricingStandardBO pricingStandardBO);
    int deletePricingStandard(Long id);
    PricingStandardVO getPricingStandardDetail(Long id);
    com.github.pagehelper.PageInfo<PricingStandardVO> getPricingStandardList(String phone, Integer pageNum, Integer pageSize);
}
