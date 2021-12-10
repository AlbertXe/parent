package org.apache.ibatis.binding;

import lombok.SneakyThrows;
import org.apache.ibatis.BaseDataTestV2;
import org.apache.ibatis.mapping.EnvironmentV2;
import org.apache.ibatis.session.ConfigurationV2;
import org.apache.ibatis.session.SqlSessionFactoryV2;
import org.apache.ibatis.transaction.TransactionFactoryV2;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactoryV2;
import org.junit.BeforeClass;

import javax.sql.DataSource;

import static org.apache.ibatis.BaseDataTest.BLOG_DATA;
import static org.apache.ibatis.BaseDataTest.BLOG_DDL;

/**
 * @author xiehongwei
 * @date 2021/12/9 12:58 PM
 */
public class BindingTestV2 {
    private static SqlSessionFactoryV2 sqlSessionFactoryV2;

    @SneakyThrows
    @BeforeClass
    public static void setup() {
        DataSource ds = BaseDataTestV2.createBlogDataSource();
        BaseDataTestV2.runScript(ds,BLOG_DDL);
        BaseDataTestV2.runScript(ds,BLOG_DATA);
        TransactionFactoryV2 factory = new JdbcTransactionFactoryV2();
        EnvironmentV2 env = new EnvironmentV2("Production", factory, ds);
        ConfigurationV2 configuration = new ConfigurationV2();


    }
}
