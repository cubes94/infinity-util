package com.infinity.training.basis.jdk.security;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/13 00:08
 */
public class SecurityManagerTestB {

    public static void test() {
        // AccessController.doPrivileged中断了栈检查过程，使得后续原本没有权限的代码也可以正常执行。
        AccessController.doPrivileged((PrivilegedAction<String>) () -> {
            new SecurityManagerTestA().test();
            return "security check success";
        });
    }
}
