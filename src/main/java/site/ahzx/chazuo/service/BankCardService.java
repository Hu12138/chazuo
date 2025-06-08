package site.ahzx.chazuo.service;

import site.ahzx.chazuo.domain.PO.BankCardPO;
import site.ahzx.chazuo.domain.VO.BankCardVO;
import java.util.List;

public interface BankCardService {
    /**
     * 添加银行卡
     */
    BankCardVO addBankCard(BankCardPO bankCard);

    /**
     * 更新银行卡信息
     */
    BankCardVO updateBankCard(BankCardPO bankCard);

    /**
     * 删除银行卡
     */
    boolean deleteBankCard(Long id, Long userId);

    /**
     * 获取用户银行卡列表
     */
    List<BankCardVO> getBankCardsByUserId(Long userId);

    /**
     * 获取银行卡详情
     */
    BankCardVO getBankCardById(Long id, Long userId);

    /**
     * 设置默认银行卡
     */
    boolean setDefaultCard(Long id, Long userId);

    /**
     * 获取用户默认银行卡
     */
    BankCardVO getDefaultCard(Long userId);
}
