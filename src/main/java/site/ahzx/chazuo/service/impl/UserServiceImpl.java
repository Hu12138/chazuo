package site.ahzx.chazuo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.ahzx.chazuo.domain.PO.UserPO;
import site.ahzx.chazuo.mapper.UserMapper;
import site.ahzx.chazuo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void insertUser(UserPO user) {

        userMapper.insertUser(user);
    }

    @Override
    public Integer countOpenId(String openId) {
        return userMapper.countOpenId(openId);
    }

    @Override
    public Long getUserIdByOpenid(String openid) {
        return userMapper.getUserIdByOpenid(openid);
    }

    @Override
    public UserPO getUserByPhone(String phone) {

        return userMapper.getUserByPhone(phone);
    }

    @Override
    public String getRoleByUserId(Long userId) {
        return userMapper.getRoleByUserId(userId);
    }


}
