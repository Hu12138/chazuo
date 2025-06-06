package site.ahzx.chazuo.service;

import site.ahzx.chazuo.domain.BO.LoginBO;
import site.ahzx.chazuo.domain.PO.UserPO;

public interface UserService {
    public Integer insertUser(UserPO user);
    public Integer countOpenId(String openId);
    public Long getUserIdByOpenid(String openid);

    // 1. 根据手机号密码查找用户
    public UserPO getUserByPhoneAndPassword(LoginBO loginBO);

    // 2.根据用户找到角色
    public String getRoleByUserId(Long userId);
}
