package site.ahzx.chazuo.mapper;

import org.apache.ibatis.annotations.Mapper;
import site.ahzx.chazuo.domain.PO.BankCardPO;
import java.util.List;

@Mapper
public interface BankCardMapper {
    int insert(BankCardPO bankCard);
    int update(BankCardPO bankCard);
    int delete(Long id);
    BankCardPO selectById(Long id);
    List<BankCardPO> selectByUserId(Long userId);
    BankCardPO selectDefaultCard(Long userId);
    int setDefaultCard(Long id, Long userId);
}
