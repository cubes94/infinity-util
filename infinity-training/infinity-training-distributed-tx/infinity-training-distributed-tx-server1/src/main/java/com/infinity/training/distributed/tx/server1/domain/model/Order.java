package com.infinity.training.distributed.tx.server1.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/23 18:18
 */
@Data
@Accessors(chain = true)
public class Order {

    private Long id;

    private String orderNo;

    private String orderDesc;
}