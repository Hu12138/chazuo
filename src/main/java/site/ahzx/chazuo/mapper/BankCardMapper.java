package site.ahzx.chazuo.mapper;

import org.apache.ibatis.annotations.Mapper;
import site.ahzx.chazuo.domain.PO.BankCardPO;
import java.util.List;

@Mapper
public interface BankCardMapper {

    BankCardPO selectById(Long id);

    List<BankCardPO> selectAll();

    int insert(BankCardPO bankCard);

    int update(BankCardPO bankCard);

    int deleteById(Long id);
}