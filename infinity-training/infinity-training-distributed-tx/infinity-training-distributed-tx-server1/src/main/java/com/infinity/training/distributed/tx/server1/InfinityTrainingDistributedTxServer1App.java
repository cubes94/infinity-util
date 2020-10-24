package com.infinity.training.distributed.tx.server1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/23 18:32
 */
@SpringBootApplication(scanBasePackages = "com.infinity")
@MapperScan("com.infinity.**.dao")
public class InfinityTrainingDistributedTxServer1App {

    public static void main(String[] args) {
        SpringApplication.run(InfinityTrainingDistributedTxServer1App.class, args);
    }
}
