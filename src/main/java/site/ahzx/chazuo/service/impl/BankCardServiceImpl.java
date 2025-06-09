package site.ahzx.chazuo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.ahzx.chazuo.domain.PO.BankCardPO;
import site.ahzx.chazuo.mapper.BankCardMapper;
import site.ahzx.chazuo.service.BankCardService;

import java.util.List;

@Service
public class BankCardServiceImpl implements BankCardService {

    @Autowired
    private BankCardMapper bankCardMapper;

    @Override
    public BankCardPO getById(Long id) {
        return bankCardMapper.selectById(id);
    }

    @Override
    public List<BankCardPO> getAll() {
        return bankCardMapper.selectAll();
    }

    @Override
    public void save(BankCardPO bankCard) {
        bankCardMapper.insert(bankCard);
    }

    @Override
    public void update(BankCardPO bankCard) {
        bankCardMapper.update(bankCard);
    }

    @Override
    public int delete(Long id) {
       return bankCardMapper.deleteById(id);
    }
}