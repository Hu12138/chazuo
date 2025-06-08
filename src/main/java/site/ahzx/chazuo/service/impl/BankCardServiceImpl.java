package site.ahzx.chazuo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.ahzx.chazuo.domain.PO.BankCardPO;
import site.ahzx.chazuo.domain.VO.BankCardVO;
import site.ahzx.chazuo.mapper.BankCardMapper;
import site.ahzx.chazuo.service.BankCardService;
import site.ahzx.chazuo.service.UserService;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankCardServiceImpl implements BankCardService {

    private final BankCardMapper bankCardMapper;
    private final UserService userService;

    @Override
    public BankCardVO addBankCard(BankCardPO bankCard) {
        // 验证用户是否存在
        if (!userService.exists(bankCard.getUserId())) {
            throw new IllegalArgumentException("用户不存在");
        }
        
        // 如果是第一张卡，自动设为默认
        if (bankCardMapper.selectByUserId(bankCard.getUserId()).isEmpty()) {
            bankCard.setIsDefault(true);
        }
        
        bankCardMapper.insert(bankCard);
        return convertToVO(bankCard);
    }

    @Override
    public BankCardVO updateBankCard(BankCardPO bankCard) {
        BankCardPO existing = bankCardMapper.selectById(bankCard.getId());
        if (existing == null || !existing.getUserId().equals(bankCard.getUserId())) {
            throw new IllegalArgumentException("银行卡不存在或不属于当前用户");
        }
        
        bankCardMapper.update(bankCard);
        return convertToVO(bankCardMapper.selectById(bankCard.getId()));
    }

    @Override
    public boolean deleteBankCard(Long id, Long userId) {
        BankCardPO card = bankCardMapper.selectById(id);
        if (card == null || !card.getUserId().equals(userId)) {
            return false;
        }
        
        // 如果是默认卡，需要设置其他卡为默认
        if (card.getIsDefault()) {
            List<BankCardPO> cards = bankCardMapper.selectByUserId(userId);
            cards.removeIf(c -> c.getId().equals(id));
            if (!cards.isEmpty()) {
                bankCardMapper.setDefaultCard(cards.get(0).getId(), userId);
            }
        }
        
        return bankCardMapper.delete(id) > 0;
    }

    @Override
    public List<BankCardVO> getBankCardsByUserId(Long userId) {
        return bankCardMapper.selectByUserId(userId).stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public BankCardVO getBankCardById(Long id, Long userId) {
        BankCardPO card = bankCardMapper.selectById(id);
        if (card == null || !card.getUserId().equals(userId)) {
            return null;
        }
        return convertToVO(card);
    }

    @Override
    public boolean setDefaultCard(Long id, Long userId) {
        BankCardPO card = bankCardMapper.selectById(id);
        if (card == null || !card.getUserId().equals(userId)) {
            return false;
        }
        return bankCardMapper.setDefaultCard(id, userId) > 0;
    }

    @Override
    public BankCardVO getDefaultCard(Long userId) {
        BankCardPO card = bankCardMapper.selectDefaultCard(userId);
        return card != null ? convertToVO(card) : null;
    }

    private BankCardVO convertToVO(BankCardPO po) {
        BankCardVO vo = new BankCardVO();
        vo.setId(po.getId());
        vo.setCardNumber(po.getCardNumber());
        vo.setBankName(po.getBankName());
        vo.setCardType(po.getCardType());
        vo.setCardHolder(po.getCardHolder());
        vo.setIsDefault(po.getIsDefault());
        return vo;
    }
}
