package com.infinity.training.mybatis.spring.domain.model;

import lombok.Data;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/20 17:13
 */
@Data
public class User {

    private Long id;

    private String userName;

    private String userMobile;
}
