package com.infinity.training.mybatis.spring.boot.starter.test.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/20 17:11
 */
public interface OrderMapper {

    @Select("select count(*) from `order`")
    List<String> countOrder();
}
