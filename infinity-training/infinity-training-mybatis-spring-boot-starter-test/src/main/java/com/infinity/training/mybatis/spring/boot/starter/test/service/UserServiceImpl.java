package com.infinity.training.mybatis.spring.boot.starter.test.service;

import com.infinity.training.mybatis.spring.boot.starter.test.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/20 17:15
 */
@Service
public class UserServiceImpl {

    @Autowired
    private UserMapper userMapper;

    public List<String> listUserName() {
        return userMapper.listUserName();
    }
}
