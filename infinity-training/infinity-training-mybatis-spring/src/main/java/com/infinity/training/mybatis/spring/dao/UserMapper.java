package com.infinity.training.mybatis.spring.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/20 17:11
 */
public interface UserMapper {

    @Select("select user_name from user")
    List<String> listUserName();
}
