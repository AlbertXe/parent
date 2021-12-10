package org.apache.ibatis.transaction;

import org.apache.ibatis.session.TransactionIsolationLevelV2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author xiehongwei
 * @date 2021/12/10 11:16 AM
 */
public interface TransactionFactoryV2 {
    void setProps(Properties props);

    TransactionV2 newTransaction(Connection connection);

    TransactionV2 newTransaction(DataSource ds, TransactionIsolationLevelV2 level, boolean autoCommit);
}
