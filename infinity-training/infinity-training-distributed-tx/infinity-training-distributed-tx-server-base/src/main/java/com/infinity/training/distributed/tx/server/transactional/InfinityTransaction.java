package com.infinity.training.distributed.tx.server.transactional;

import com.infinity.training.distributed.tx.server.util.Task;
import lombok.Data;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/23 17:04
 */
@Data
public class InfinityTransaction {

    private String groupId;

    private String transactionId;

    private TransactionType transactionType; // commit-待提交，rollback-待回滚

    private Task task;

    public InfinityTransaction(String groupId, String transactionId) {
        this.groupId = groupId;
        this.transactionId = transactionId;
        this.task = new Task();
    }
}
