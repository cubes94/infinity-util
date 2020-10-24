package com.infinity.training.distributed.tx.server.transactional;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/23 17:04
 */
public enum TransactionType {

    COMMIT("commit"),

    ROLLBACK("rollback");

    private final String name;

    TransactionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
