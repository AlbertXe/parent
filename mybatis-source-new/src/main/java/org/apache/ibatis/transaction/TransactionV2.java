package org.apache.ibatis.transaction;

import java.sql.Connection;

/**
 * @author xiehongwei
 * @date 2021/12/10 11:18 AM
 */
public interface TransactionV2 {
    Connection getConn();

    void commit();

    void rollback();

    void close();
}
