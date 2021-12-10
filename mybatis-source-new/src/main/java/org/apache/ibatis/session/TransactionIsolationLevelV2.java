package org.apache.ibatis.session;

import lombok.Getter;

import java.sql.Connection;

/**
 * @author xiehongwei
 * @date 2021/12/10 11:21 AM
 */
@Getter
public enum TransactionIsolationLevelV2 {
    NONE(Connection.TRANSACTION_NONE),
    READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),
    READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),
    SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);


    private int level;

    TransactionIsolationLevelV2(int level) {
        this.level = level;
    }


}
