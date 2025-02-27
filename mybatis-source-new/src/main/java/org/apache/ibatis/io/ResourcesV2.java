package org.apache.ibatis.io;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * @author xiehongwei
 * @date 2021/12/9 2:50 PM
 */
public class ResourcesV2 {
    private static ClassLoaderWrapperV2 classLoaderWrapper = new ClassLoaderWrapperV2();

    @SneakyThrows
    public static Properties getResourceAsProperties(String resource) {
        Properties props = new Properties();
        InputStream in = getResourceAsStream(null,resource);
        props.load(in);
        in.close();
        return props;
    }

    public static InputStream getResourceAsStream(ClassLoader classLoader,String resource){
        return classLoaderWrapper.getResourceAsStream(classLoader, resource);
    }

    public static Reader getResourceAsReader(String resource, Charset charset) {
        if (charset == null) {
            charset = Charset.defaultCharset();
        }
        return new InputStreamReader(getResourceAsStream(null, resource),charset);
    }

}
