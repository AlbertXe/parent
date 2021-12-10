package org.apache.ibatis.transaction.jdbc;

import org.apache.ibatis.session.TransactionIsolationLevelV2;
import org.apache.ibatis.transaction.TransactionFactoryV2;
import org.apache.ibatis.transaction.TransactionV2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author xiehongwei
 * @date 2021/12/10 11:17 AM
 */
public class JdbcTransactionFactoryV2 implements TransactionFactoryV2 {

    @Override
    public void setProps(Properties props) {

    }

    @Override
    public TransactionV2 newTransaction(Connection connection) {
        return new JdbcTransactionV2(connection);
    }

    @Override
    public TransactionV2 newTransaction(DataSource ds, TransactionIsolationLevelV2 level, boolean autoCommit) {
        return new JdbcTransactionV2(ds,level,autoCommit);
    }
}
