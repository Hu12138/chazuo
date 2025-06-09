package site.ahzx.chazuo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.ahzx.chazuo.aop.UserContext;
import site.ahzx.chazuo.domain.BO.BankCardBO;
import site.ahzx.chazuo.domain.CodeInfo;
import site.ahzx.chazuo.domain.PO.BankCardPO;
import site.ahzx.chazuo.domain.PO.UserPO;
import site.ahzx.chazuo.service.BankCardService;
import site.ahzx.chazuo.service.UserService;
import site.ahzx.chazuo.util.R;
import site.ahzx.chazuo.util.SMSCodeUtil;

import java.util.List;


@RestController
@RequestMapping("/bankCard")
public class BankCardController {

    @Autowired
    private BankCardService bankCardService;


    @Autowired
    private UserContext userContext;

    @Autowired
    private UserService userService;

    @Autowired
    private SMSCodeUtil smsCodeUtil;

    @GetMapping("/{id}")
    public BankCardPO getById(@PathVariable Long id) {
        return bankCardService.getById(id);
    }

    @GetMapping
    public List<BankCardPO> getAll() {
        return bankCardService.getAll();
    }

    @PostMapping("/add")
    public R create(@RequestBody @Valid BankCardBO bankCardBO) {
        String phone = userContext.getCurrentUser();
        String code = bankCardBO.getCode();
        String cardPhone = bankCardBO.getCardPhone();
        // 验证验证码
        if (!smsCodeUtil.verifyCode(cardPhone, code)) {
            return R.fail("验证码错误或已过期");
        }
        // 检查用户是否已存在
        UserPO user = userService.getUserByPhone(phone);

        if (user == null) {
            return R.fail("用户不存在");
        }
        bankCardBO.setUserId(Long.valueOf(user.getId()));
        BankCardPO po = convertToPO(bankCardBO);

        bankCardService.save(po);

        return R.ok("创建成功");
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody @Valid BankCardBO bo) {

        BankCardPO po = convertToPO(bo);
        po.setId(id);
        bankCardService.update(po);
        return "更新成功";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        bankCardService.delete(id);
        return "删除成功";
    }

    private BankCardPO convertToPO(BankCardBO bo) {
        BankCardPO po = new BankCardPO();
        po.setId(bo.getId());
        po.setUserId(String.valueOf(bo.getUserId()));
        po.setCardNumber(bo.getCardNumber());
        po.setBankName(bo.getBankName());
        po.setCardType(bo.getCardType());
        po.setCardHolder(bo.getCardHolder());
        po.setCardPhone(bo.getCardPhone());
        po.setIdCard(bo.getIdCard());
        po.setProvince(bo.getProvince());
        po.setCity(bo.getCity());
        po.setStatus(1); // 默认状态
        return po;
    }
}