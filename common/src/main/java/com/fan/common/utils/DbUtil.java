package com.fan.common.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {

    private static final Logger logger = LoggerFactory.getLogger(DbUtil.class);

    private static Properties properties;

    private static final String dbPropertiesPath = "db.properties";

    private static String url;

    private static String username;

    private static String password;

    private static Properties loadProperties(String path){
        properties = new Properties();
        try(InputStream fis = DbUtil.class.getClassLoader().getResourceAsStream(path)) {
            properties.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    private static String getProperty(String key){
        return properties.getProperty(key);
    }

    private static Connection getConnection(String url, String username, String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public static Connection getDatabaseConnection(String dbPropertiesPath, String databaseType) throws SQLException {
        if (dbPropertiesPath == null){
            dbPropertiesPath = DbUtil.dbPropertiesPath;
        }
        loadProperties(dbPropertiesPath);
        if ("oracle".equals(databaseType)){
            url = getProperty("oracle.url");
            username = getProperty("oracle.username");
            password = getProperty("oracle.password");
        }else if ("postgres".equals(databaseType)){
            url = getProperty("postgres.url");
            username = getProperty("postgres.username");
            password = getProperty("postgres.password");
        }else if ("mysql".equals(databaseType)){
            url = getProperty("mysql.url");
            username = getProperty("mysql.username");
            password = getProperty("mysql.password");
        }else {
            logger.info("没有{}该数据库的连接信息", databaseType);
            System.exit(1);
        }
        return getConnection(url, username, password);
    }
}
