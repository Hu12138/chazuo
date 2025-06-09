package site.ahzx.chazuo.service;

import site.ahzx.chazuo.domain.PO.BankCardPO;
import java.util.List;

public interface BankCardService {
    BankCardPO getById(Long id);
    List<BankCardPO> getAll();
    void save(BankCardPO bankCard);
    void update(BankCardPO bankCard);
    int delete(Long id);
}