package com.infinity.training.mybatis.spring.domain.model;

import lombok.Data;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/20 17:12
 */
@Data
public class Order {

    private Long id;

    private String orderNo;

    private String orderDesc;
}
