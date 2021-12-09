package org.apache.ibatis.datasource.unpooled;

import lombok.Data;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * @author xiehongwei
 * @date 2021/12/9 2:49 PM
 */
@Data
public class UnpooledDataSourceV2 implements DataSource {
    private ClassLoader driverClassLoader;
    private Properties props;
    private static Map<String, Driver> registeredDrivers = new ConcurrentHashMap<>();

    private String driver;
    private String url;
    private String username;
    private String password;
    private boolean autoCommit=false;
    private Integer defaultTranIsoLevel;

    static {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            registeredDrivers.put(driver.getClass().getName(), driver);
        }
    }




    @Override
    public Connection getConnection() throws SQLException {
        return doGetConnecton(username, password);
    }

    private Connection doGetConnecton(String username, String password) {
        Properties properties = new Properties();
        if (this.props != null) {
            properties.putAll(this.props);
        }
        if (username != null) {
            properties.put("user", username);
        }
        if (password != null) {
            properties.put("password", password);
        }
        return doGetConnecton(properties);

    }

    @SneakyThrows
    private Connection doGetConnecton(Properties properties) {
        initDriver();
        Connection conn = DriverManager.getConnection(url, properties);
        conn.setAutoCommit(autoCommit);
        if (defaultTranIsoLevel != null) {
            conn.setTransactionIsolation(defaultTranIsoLevel);
        }
        return conn;
    }

    @SneakyThrows
    private void initDriver() {
        if (!registeredDrivers.containsKey(driver)) {
            Class<?> driverType;
            if (driverClassLoader != null) {
                driverType = Class.forName(driver, true, driverClassLoader);
            }else {
                driverType = Class.forName(driver);
            }
            Driver d = (Driver) driverType.newInstance();
            DriverManager.registerDriver(d);
            registeredDrivers.put(driver, d);
        }
    }

    @Override
    public Connection getConnection(String s, String s1) throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter printWriter) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int i) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        return false;
    }
}
