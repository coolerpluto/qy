package com.fan.stageone.test;

import com.fan.stageone.constants.OracleConnectVars;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(OracleConnectVars.URL, OracleConnectVars.USERNAME, OracleConnectVars.PASSWORD);
        System.out.println("数据库连接创建："+(connection!=null));
        connection.close();
    }
}
