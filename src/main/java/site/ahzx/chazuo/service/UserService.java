package site.ahzx.chazuo.service;

import site.ahzx.chazuo.domain.PO.UserPO;

public interface UserService {
    public void insertUser(UserPO user);
    public Integer countOpenId(String openId);
    public Long getUserIdByOpenid(String openid);

    // 1. 根据手机号密码查找用户
    public UserPO getUserByPhone(String phone);

    // 2.根据用户找到角色
    public String getRoleByUserId(Long userId);
    
    // 3.检查用户是否存在
    boolean exists(Long userId);
}
