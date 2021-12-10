package org.apache.ibatis.session;

import lombok.Data;
import org.apache.ibatis.mapping.EnvironmentV2;

/**
 * @author xiehongwei
 * @date 2021/12/10 11:48 AM
 */
@Data
public class ConfigurationV2 {
    EnvironmentV2 env;

    boolean safeRowBoundsEnabled = false;
    boolean safeResultHandlerEnabled = true;
    boolean mapUnderscoreToCamelCase = false;
    boolean aggreesiveLazyLoading = true;
    boolean multipleResuleSetsEnabled = false;
    boolean useGenerateKeys = false;
    boolean useColumnLabel = false;
    boolean cacheEnabled = true;
    boolean callSettersOnNull = false;

    String logPrefix;

}
