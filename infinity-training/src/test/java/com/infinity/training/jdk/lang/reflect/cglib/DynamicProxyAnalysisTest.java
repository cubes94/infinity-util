package com.infinity.training.jdk.lang.reflect.cglib;

import com.infinity.training.jdk.AnalysisTest;
import com.infinity.training.jdk.lang.reflect.DynamicProxyAnalysis;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 动态代理测试
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/09/23 15:54
 */
@Slf4j
public class DynamicProxyAnalysisTest extends DynamicProxyAnalysis implements AnalysisTest {

    @Test
    public void testJdkReflect() {
        super.jdkReflect();
    }

    @Test
    public void testCglib() {
        super.cglib();
    }
}
