package com.infinity.training.basis.jdk.security;

import com.infinity.training.basis.jdk.AnalysisTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/13 00:10
 */
@Slf4j
public class SecurityManagerAnalysisTest extends SecurityManagerAnalysis implements AnalysisTest {

    @Test
    public void testSecurityManagerA() {
        // 需要添加JVM参数
//        -Djava.security.manager -Djava.security.policy=/D:/workspace/wuhaichao/infinity-util/infinity-training/infinity-training-basis/target/classes/security/test.policy
        new SecurityManagerTestA().test();
        // 抛出异常： java.security.AccessControlException: access denied ("java.io.FilePermission" "test" "write")
    }

    @Test
    public void testSecurityManagerB() {
        // 需要添加JVM参数
//        -Djava.security.manager -Djava.security.policy=/D:/workspace/wuhaichao/infinity-util/infinity-training/infinity-training-basis/target/classes/security/test.policy
        SecurityManagerTestB.test();
        // 正常运行
    }
}
