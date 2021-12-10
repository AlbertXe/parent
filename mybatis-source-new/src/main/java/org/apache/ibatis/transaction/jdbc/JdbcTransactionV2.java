package org.apache.ibatis.transaction.jdbc;

import lombok.SneakyThrows;
import org.apache.ibatis.session.TransactionIsolationLevelV2;
import org.apache.ibatis.transaction.TransactionV2;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author xiehongwei
 * @date 2021/12/10 11:19 AM
 */
public class JdbcTransactionV2 implements TransactionV2 {
    Connection conn;
    DataSource ds;
    TransactionIsolationLevelV2 level;
    boolean autoCommit;

    public JdbcTransactionV2(Connection conn) {
        this.conn = conn;
    }

    public JdbcTransactionV2(DataSource ds, TransactionIsolationLevelV2 level, boolean autoCommit) {
        this.ds = ds;
        this.level = level;
        this.autoCommit = autoCommit;
    }


    @SneakyThrows
    @Override
    public Connection getConn() {
        if (conn == null) {
            conn = ds.getConnection();
            if (level != null) {
                conn.setTransactionIsolation(level.getLevel());
            }
            if (conn.getAutoCommit() != autoCommit) {
                conn.setAutoCommit(autoCommit);
            }
        }
        return conn;
    }

    @SneakyThrows
    @Override
    public void commit() {
        if (conn != null && !conn.getAutoCommit()) {
            conn.commit();
        }
    }

    @SneakyThrows
    @Override
    public void rollback() {
        if (conn != null && !conn.getAutoCommit()) {
            conn.rollback();
        }
    }

    @SneakyThrows
    @Override
    public void close() {
        if (conn != null) {
            conn.setAutoCommit(true);
            conn.close();
        }
    }
}
