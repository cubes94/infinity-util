package com.infinity.training.distributed.tx.server1.service;

import com.infinity.training.basis.algorithm.id.SnowFlake;
import com.infinity.training.distributed.tx.server.annotation.InfinityTransactional;
import com.infinity.training.distributed.tx.server1.dao.OrderMapper;
import com.infinity.training.distributed.tx.server1.domain.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/23 18:26
 */
@Slf4j
@Service
public class OrderServiceImpl {

    @Value("${distributed.tx.test.fail}")
    private boolean testFail;

    @Autowired
    private OrderMapper orderMapper;

    @InfinityTransactional(isEnd = true)
    @Transactional
    public int addOrder(String orderDesc) {
        SnowFlake snowFlake = new SnowFlake(0, 1);
        long id = snowFlake.nextId();
        Order order = new Order().setId(id).setOrderNo("O" + id).setOrderDesc(orderDesc);
        int count = orderMapper.addOrder(order);
        log.info("server1: {}", count);
        if (testFail) {
            throw new RuntimeException("on purpose");
        }
        return count;
    }
}
