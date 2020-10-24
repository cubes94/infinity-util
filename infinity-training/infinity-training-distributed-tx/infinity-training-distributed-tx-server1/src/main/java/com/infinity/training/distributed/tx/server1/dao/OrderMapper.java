package com.infinity.training.distributed.tx.server1.dao;

import com.infinity.training.distributed.tx.server1.domain.model.Order;
import org.apache.ibatis.annotations.Insert;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/23 18:17
 */
public interface OrderMapper {

    @Insert("insert into `order`(id, order_no, order_desc) values (#{id}, #{orderNo}, #{orderDesc})")
    int addOrder(Order order);
}
