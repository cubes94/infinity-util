package com.infinity.training.basis.jdk.security;

import lombok.extern.slf4j.Slf4j;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/13 00:08
 */
@Slf4j
public class SecurityManagerTestA {

    public void test() {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkWrite("test");
        }
        log.info("security check success");
    }
}
