package org.apache.ibatis.binding;

import lombok.SneakyThrows;
import org.apache.ibatis.BaseDataTestV2;
import org.apache.ibatis.session.SqlSessionFactoryV2;
import org.junit.BeforeClass;

import javax.sql.DataSource;

/**
 * @author xiehongwei
 * @date 2021/12/9 12:58 PM
 */
public class BindingTestV2 {
    private static SqlSessionFactoryV2 sqlSessionFactoryV2;

    @SneakyThrows
    @BeforeClass
    public static void setup() {
        DataSource dataSource = BaseDataTestV2.createBlogDataSource();

    }
}
