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
    public Integer insertUser(UserPO user) {

        return userMapper.insertUser(user);
    }

    @Override
    public Integer countOpenId(String openId) {
        return userMapper.countOpenId(openId);
    }
}
