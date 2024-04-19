package com.fan.stageone.thread;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TableFetcher {

    // 数据库连接信息
    private static final String URL = "jdbc:oracle:thin:@//192.168.10.190:1521/orcl";
    private static final String USERNAME = "sys as sysdba";
    private static final String PASSWORD = "sys"; // 替换为实际的密码

    // 获取所有表名的 SQL 查询语句
    private static final String QUERY_TABLES_SQL = "SELECT TABLE_NAME FROM all_tables WHERE OWNER = 'SYS'";

    public static void main(String[] args) {
        // 创建数据库连接
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // 获取所有表名
            List<String> tableNames = getTableNames(connection);

            // 设置线程池大小
            int numThreads = 5;
            ExecutorService executor = Executors.newFixedThreadPool(numThreads);

            // 提交任务并发地获取表信息
            for (String tableName : tableNames) {
                executor.submit(() -> {
                    // 获取表信息的逻辑
                    // 例如，输出表名
                    System.out.println("Fetching information for table: " + tableName);
                });
            }

            // 关闭线程池
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 获取所有表名
    private static List<String> getTableNames(Connection connection) throws SQLException {
        List<String> tableNames = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_TABLES_SQL)) {
            while (resultSet.next()) {
                tableNames.add(resultSet.getString("TABLE_NAME"));
            }
        }
        return tableNames;
    }
}

