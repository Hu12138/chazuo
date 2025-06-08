package site.ahzx.chazuo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.ahzx.chazuo.aop.UserContext;
import site.ahzx.chazuo.domain.BO.BankCardBO;
import site.ahzx.chazuo.domain.PO.BankCardPO;
import site.ahzx.chazuo.domain.VO.BankCardVO;
import site.ahzx.chazuo.domain.PO.UserPO;
import site.ahzx.chazuo.service.BankCardService;
import site.ahzx.chazuo.service.UserService;
import site.ahzx.chazuo.util.R;

import java.util.List;

@RestController
@RequestMapping("/bankCard")
@Slf4j
public class BankCardController {

    @Autowired
    private BankCardService bankCardService;
    @Autowired
    private UserContext userContext;

    @Autowired
    private UserService userService;
    @PostMapping("/add")
    public R addBankCard(@RequestBody BankCardBO bankCardBO) {
        try {
            String phone = userContext.getCurrentUser();
            UserPO user = userService.getUserByPhone(phone);
            if (user == null) {
                return R.fail("用户不存在");
            }
            
            BankCardPO cardPO = new BankCardPO();
            cardPO.setUserId(Long.valueOf(user.getId()));
            cardPO.setCardNumber(bankCardBO.getCardNumber());
            cardPO.setBankName(bankCardBO.getBankName());
            cardPO.setCardType(bankCardBO.getCardType());
            cardPO.setIsDefault(bankCardBO.isDefaultCard());
            bankCardService.addBankCard(cardPO);
            return R.ok("银行卡添加成功");
        } catch (Exception e) {
            log.error("添加银行卡失败", e);
            return R.fail("添加银行卡失败: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public R listBankCards() {
        try {
            String phone = userContext.getCurrentUser();
            UserPO user = userService.getUserByPhone(phone);
            if (user == null) {
                return R.fail("用户不存在");
            }
            List<BankCardVO> cards = bankCardService.getBankCardsByUserId(Long.valueOf(user.getId()));
            return R.ok("查询成功", cards);
        } catch (Exception e) {
            log.error("查询银行卡列表失败", e);
            return R.fail("查询银行卡列表失败: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    public R updateBankCard(@RequestBody BankCardBO bankCardBO) {
        try {
            String phone = userContext.getCurrentUser();
            UserPO user = userService.getUserByPhone(phone);
            if (user == null) {
                return R.fail("用户不存在");
            }
            
            BankCardPO cardPO = new BankCardPO();
            cardPO.setId(bankCardBO.getId());
            cardPO.setUserId(Long.valueOf(user.getId()));
            cardPO.setCardNumber(bankCardBO.getCardNumber());
            cardPO.setBankName(bankCardBO.getBankName());
            cardPO.setCardType(bankCardBO.getCardType());
            cardPO.setIsDefault(bankCardBO.isDefaultCard());
            bankCardService.updateBankCard(cardPO);
            return R.ok("银行卡更新成功");
        } catch (Exception e) {
            log.error("更新银行卡失败", e);
            return R.fail("更新银行卡失败: " + e.getMessage());
        }
    }

    @PostMapping("/delete")
    public R deleteBankCard(@RequestParam Long cardId) {
        try {
            String phone = userContext.getCurrentUser();
            UserPO user = userService.getUserByPhone(phone);
            if (user == null) {
                return R.fail("用户不存在");
            }
            bankCardService.deleteBankCard(cardId, Long.valueOf(user.getId()));
            return R.ok("银行卡删除成功");
        } catch (Exception e) {
            log.error("删除银行卡失败", e);
            return R.fail("删除银行卡失败: " + e.getMessage());
        }
    }

    @GetMapping("/detail")
    public R getBankCardDetail(@RequestParam Long cardId) {
        try {
            String phone = userContext.getCurrentUser();
            UserPO user = userService.getUserByPhone(phone);
            if (user == null) {
                return R.fail("用户不存在");
            }
            BankCardVO card = bankCardService.getBankCardById(cardId, Long.valueOf(user.getId()));
            return R.ok("查询成功", card);
        } catch (Exception e) {
            log.error("查询银行卡详情失败", e);
            return R.fail("查询银行卡详情失败: " + e.getMessage());
        }
    }
}
