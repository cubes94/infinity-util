package com.infinity.training.basis.algorithm.id;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * ID生成器测试
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/11 19:55
 */
@Slf4j
public class IDGeneratorTest {

    @Test
    public void testSnowFlake() {
        final SnowFlake snowFlake = new SnowFlake(3, 3);
        for (int i = 0; i < (1 << 12); i++) {
            log.info("id: {}", snowFlake.nextId());
        }
    }
}
