package site.ahzx.chazuo.mapper;

import org.apache.ibatis.annotations.Mapper;
import site.ahzx.chazuo.domain.PO.UserPO;

@Mapper
public interface UserMapper {
    public Integer insertUser(UserPO user);
    public Integer countOpenId(String openId);
    public Long getUserIdByOpenid(String openid);

}
