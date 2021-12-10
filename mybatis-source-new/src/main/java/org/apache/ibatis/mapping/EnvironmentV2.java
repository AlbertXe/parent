package org.apache.ibatis.mapping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.ibatis.transaction.TransactionFactoryV2;

import javax.sql.DataSource;

/**
 * @author xiehongwei
 * @date 2021/12/10 11:45 AM
 */
@Data
@Builder
@AllArgsConstructor
public class EnvironmentV2 {
    private String id;
    private TransactionFactoryV2 factoryV2;
    private DataSource ds;
}
