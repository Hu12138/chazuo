package site.ahzx.chazuo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.ahzx.chazuo.aop.UserContext;
import site.ahzx.chazuo.domain.BO.PricingStandardBO;
import site.ahzx.chazuo.domain.VO.*;
import site.ahzx.chazuo.service.PricingStandardService;
import site.ahzx.chazuo.service.UserService;
import site.ahzx.chazuo.util.R;

@RestController
@RequestMapping("/price-std")
@Slf4j
public class PriceStdController {

    @Autowired
    private PricingStandardService pricingStandardService;
    
    @Autowired
    private UserContext userContext;
    
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public R addPricingStandard(@RequestBody PricingStandardBO pricingStandardBO) {
        try {
            log.info("新增收费标准: {}", pricingStandardBO);
            String openid = userContext.getCurrentUser();
            Long userId = userService.getUserIdByOpenid(openid);
            if (userId == null) {
                return R.fail("用户不存在");
            }
            pricingStandardBO.setCreatedBy(userId);
            
            // 验证收费类型和对应字段
            switch(pricingStandardBO.getType()) {
                case BY_ENERGY:
                    if (pricingStandardBO.getEnergyFeePerUnit() == null) {
                        return R.fail("按电量收费必须设置电费单价");
                    }
                    break;
                case BY_TIME:
                    if (pricingStandardBO.getTimePerYuan() == null || 
                        pricingStandardBO.getTimeUnit() == null) {
                        return R.fail("按时长收费必须设置时间和单位");
                    }
                    break;
                case BY_AMOUNT:
                    if (pricingStandardBO.getAmountPerUnit() == null || 
                        pricingStandardBO.getTimeUnit() == null) {
                        return R.fail("按金额收费必须设置金额和单位");
                    }
                    break;
            }

            pricingStandardService.addPricingStandard(pricingStandardBO);
            return R.ok("收费标准添加成功");
        } catch (Exception e) {
            log.error("新增收费标准失败", e);
            return R.fail("收费标准添加失败: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    public R updatePricingStandard(@RequestBody PricingStandardBO pricingStandardBO) {
        try {
            log.info("更新收费标准: {}", pricingStandardBO);
            pricingStandardService.updatePricingStandard(pricingStandardBO);
            return R.ok("收费标准更新成功");
        } catch (Exception e) {
            log.error("更新收费标准失败", e);
            return R.fail("收费标准更新失败: " + e.getMessage());
        }
    }

    @GetMapping("/delete/{id}")
    public R deletePricingStandard(@PathVariable("id") Long id) {
        try {
            log.info("删除收费标准: {}", id);
            pricingStandardService.deletePricingStandard(id);
            return R.ok("收费标准删除成功");
        } catch (Exception e) {
            log.error("删除收费标准失败", e);
            return R.fail("收费标准删除失败: " + e.getMessage());
        }
    }

    @GetMapping("/detail/{id}")
    public R getPricingStandardDetail(@PathVariable("id") Long id) {
        try {
            log.info("查询收费标准详情: {}", id);
            PricingStandardVO vo = pricingStandardService.getPricingStandardDetail(id);
            return R.ok("查询成功", vo);
        } catch (Exception e) {
            log.error("查询收费标准详情失败", e);
            return R.fail("查询失败: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public R getPricingStandardList() {
        try {
            log.info("查询收费标准列表");
            String openid = userContext.getCurrentUser();
            return R.ok("查询成功", pricingStandardService.getPricingStandardList(openid));
        } catch (Exception e) {
            log.error("查询收费标准列表失败", e);
            return R.fail("查询失败: " + e.getMessage());
        }
    }

}
