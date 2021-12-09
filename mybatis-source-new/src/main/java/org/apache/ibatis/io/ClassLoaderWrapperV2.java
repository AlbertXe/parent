package org.apache.ibatis.io;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author xiehongwei
 * @date 2021/12/9 2:53 PM
 */
public class ClassLoaderWrapperV2 {
    ClassLoader defaultLoad;
    ClassLoader sysLoad;

    public ClassLoaderWrapperV2() {
        sysLoad = ClassLoader.getSystemClassLoader();
    }

    public InputStream getResourceAsStream(ClassLoader classLoader, String resource) {
        return getResourceAsStream(getClassLoaders(classLoader), resource);
    }

    public InputStream getResourceAsStream(ClassLoader[] classLoaders, String resource) {
        return Arrays.stream(classLoaders).filter(Objects::nonNull).map(t -> t.getResourceAsStream(resource))
                .findAny().orElse(null);
    }

    /**
     * 5个加载器
     * @param classLoader
     * @return
     */
    ClassLoader[] getClassLoaders(ClassLoader classLoader) {
        return new ClassLoader[]{classLoader,
                defaultLoad,
                Thread.currentThread().getContextClassLoader(),
                getClass().getClassLoader(),
                sysLoad};
    }
}
