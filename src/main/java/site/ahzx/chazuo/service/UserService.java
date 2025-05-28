package site.ahzx.chazuo.service;

import site.ahzx.chazuo.domain.PO.UserPO;

public interface UserService {
    public Integer insertUser(UserPO user);
    public Integer countOpenId(String openId);
}
