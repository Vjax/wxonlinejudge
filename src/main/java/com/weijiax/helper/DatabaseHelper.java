package com.weijiax.helper;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private static ComboPooledDataSource connectionPool;

    private static String driver = ConfigHelper.getJdbcDriver();
    private static String url = ConfigHelper.getJdbcUrl();
    private static String username = ConfigHelper.getJdbcUsername();
    private static String password = ConfigHelper.getJdbcPassword();

    static {
//        connectionPool = new ComboPooledDataSource();
//        try {
//            connectionPool.setDriverClass(ConfigHelper.getJdbcDriver());
//            connectionPool.setJdbcUrl(ConfigHelper.getJdbcUrl());
//            connectionPool.setUser(ConfigHelper.getJdbcUsername());
//            connectionPool.setPassword(ConfigHelper.getJdbcPassword());
//            connectionPool.setInitialPoolSize(ConfigHelper.getInitialPoolSize());
//            connectionPool.setMaxIdleTime(ConfigHelper.getMaxIdleTime());
//            connectionPool.setMaxPoolSize(ConfigHelper.getMaxPoolSize());
//            connectionPool.setMinPoolSize(ConfigHelper.getMinPoolSize());
//            connectionPool.setMaxStatements(ConfigHelper.getMaxStatement());
//        }catch (PropertyVetoException e){
//            e.printStackTrace();
//        }
          try {
              Class.forName(driver);
          }catch (ClassNotFoundException e){
              e.printStackTrace();
          }
    }

    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url,username,password);
        }catch (Exception e){
            e.printStackTrace();
        }

        return connection;
    }
//    /**
//     * 获取连接
//     */
//    public static Connection getConnection(){
//        try {
//            return connectionPool.getConnection();
//        }catch (SQLException e){
//            LOGGER.error("get database connectiob failure");
//            e.printStackTrace();
//            return null;
//        }
//    }

    /**
     * 关闭连接
     */
    public static void closeConnection(Connection connection){
        try {
            connection.close();
        }catch (SQLException e){
            LOGGER.error("close connection failure");
            e.printStackTrace();
        }
    }



}
