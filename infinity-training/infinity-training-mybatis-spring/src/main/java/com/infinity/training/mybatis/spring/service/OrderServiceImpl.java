package com.infinity.training.mybatis.spring.service;

import com.infinity.training.mybatis.spring.dao.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/20 17:13
 */
@Service
public class OrderServiceImpl {

    @Autowired
    private OrderMapper orderMapper;

    public List<String> countOrder() {
        return orderMapper.countOrder();
    }
}
