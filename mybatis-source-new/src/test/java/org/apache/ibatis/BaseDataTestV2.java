package org.apache.ibatis;

import lombok.SneakyThrows;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceV2;
import org.apache.ibatis.io.ResourcesV2;
import org.apache.ibatis.jdbc.ScriptRunnerV2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

import static org.apache.ibatis.BaseDataTest.*;

/**
 * @author xiehongwei
 * @date 2021/12/9 2:44 PM
 */
public class BaseDataTestV2 {

    public static DataSource createBlogDataSource() {
        DataSource ds = createUnpooledDataSource(BLOG_PROPERTIES);
        runScript(ds,JPETSTORE_DDL);
        runScript(ds,JPETSTORE_DATA);
        return ds;
    }

    public static UnpooledDataSourceV2 createUnpooledDataSource(String resource) {
        Properties props = ResourcesV2.getResourceAsProperties(resource);
        UnpooledDataSourceV2 ds = new UnpooledDataSourceV2();
        ds.setDriver(props.getProperty("driver"));
        ds.setUrl(props.getProperty("url"));
        ds.setUsername(props.getProperty("username"));
        ds.setPassword(props.getProperty("password"));
        return ds;
    }

    @SneakyThrows
    public static void runScript(DataSource ds, String resource) {
        Connection conn = ds.getConnection();
        ScriptRunnerV2 runner = new ScriptRunnerV2(conn);
        runner.setAutoCommit(true);
        ////xie
    }
}
