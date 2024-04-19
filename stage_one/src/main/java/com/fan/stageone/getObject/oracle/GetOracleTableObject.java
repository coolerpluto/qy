package com.fan.stageone.getObject.oracle;

import com.fan.stageone.constants.OracleConnectVars;
import com.fan.stageone.entity.oracle.OracleTableColumn;
import com.fan.stageone.entity.oracle.OracleTableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetOracleTableObject {

    private static final Logger logger = LoggerFactory.getLogger(GetOracleTableObject.class);

    private static final String USER_TABLES_SQL = "SELECT TABLE_NAME FROM user_tables";

    private static final String ALL_TABLES_SQL = "SELECT TABLE_NAME FROM all_tables";

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(OracleConnectVars.URL, OracleConnectVars.USERNAME, OracleConnectVars.PASSWORD);
//        getTableNames(connection);
//        getColumnsByTableName(connection);
        getOracleObjectList(connection);
        connection.close();
    }

    //获取当前用户拥有的所有表名
    public static List<String> getTableNameList(Connection connection){
        //初始化数组存放表名
        List<String> tableNameList = new ArrayList<>();
        try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(USER_TABLES_SQL)){
            while (resultSet.next()){
                //循环获取表名，放入表名数组
                String tableName = resultSet.getString("TABLE_NAME");
//                logger.info("获取的表名：{}",tableName);
                tableNameList.add(tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //返回结果
        return tableNameList;
    }

    //根据传入的表名获取表的字段对象数组
    public static List<OracleTableColumn> getColumnsByTableName(Connection connection, String... tableNames){
        //初始化数组存放字段对象
        List<OracleTableColumn> oracleTableColumnList = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            for (String tname : tableNames) {
                //构建根据表名查询：字段名称，字段类型，字段长度，字段所属表
                String queryColumns = "SELECT TABLE_NAME ,COLUMN_NAME ,DATA_TYPE ,DATA_LENGTH FROM USER_TAB_COLUMNS WHERE TABLE_NAME = '" + tname + "'";
                //用preparedstatement，有缓存，性能好
                //执行查询语句
                ResultSet resultSet = statement.executeQuery(queryColumns);
                while (resultSet.next()){
                    String tableName = resultSet.getString("TABLE_NAME");//获取字段所属表名
                    String columnName = resultSet.getString("COLUMN_NAME");//获取字段名称
                    String columnType = resultSet.getString("DATA_TYPE");//获取字段类型
                    int columnSize = resultSet.getInt("DATA_LENGTH");//获取字段长度
                    //把查出来的信息创建到一个字段对象里
                    OracleTableColumn oracleTableColumn = new OracleTableColumn(columnName, columnType, columnSize, tableName);
//                    logger.info("获取字段对象：{}",oracleTableColumn);
                    //把字段对象放入数组里
                    oracleTableColumnList.add(oracleTableColumn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //返回结果
        return oracleTableColumnList;
    }

    //获取表对象
    public static List<OracleTableObject> getOracleObjectList(Connection connection){
        //获取当前用户拥有的表
        List<String> tableNames = getTableNameList(connection);
        //初始化数组存放表对象
        ArrayList<OracleTableObject> oracleTableObjects = new ArrayList<>();
        //循环遍历表名，通过表名查询字段对象
        for (String tableName : tableNames) {
            //新建一个表对象
            OracleTableObject oracleTableObject = new OracleTableObject();
            //表名放入到表对象里
            oracleTableObject.setTableName(tableName);
            //返回表对应的字段对象数组
            List<OracleTableColumn> columnsByTableName = getColumnsByTableName(connection, tableName);
            //将字段对象数组放入到表对象里
            oracleTableObject.setOracleTableColumnList(columnsByTableName);
            logger.info("获取到的表对象:{}",oracleTableObject);
            //将表对象放入到数组里
            oracleTableObjects.add(oracleTableObject);
        }
        //返回结果
        return oracleTableObjects;
    }
}
