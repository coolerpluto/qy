package com.fan.stageone.getObject.oracle;

import com.fan.stageone.constants.OracleConnectVars;
import com.fan.stageone.entity.oracle.OracleIndexObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetOracleIndexObject {
    private static final Logger logger = LoggerFactory.getLogger(GetOracleIndexObject.class);

    //获取索引名称、索引所加字段名称、索引类型、索引所在表名
    private static final String QUERY_SQL = "SELECT ui.INDEX_NAME  , uic.COLUMN_NAME ,uc.CONSTRAINT_TYPE, ui.TABLE_NAME\n" +
            "FROM user_tables ut\n" +
            "JOIN user_indexes ui ON ut.TABLE_NAME = ui.TABLE_NAME \n" +
            "JOIN USER_IND_COLUMNS uic ON ui.INDEX_NAME = uic.INDEX_NAME \n" +
            "JOIN USER_CONSTRAINTS uc ON ui.INDEX_NAME = uc.CONSTRAINT_NAME ";

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(OracleConnectVars.URL, OracleConnectVars.USERNAME, OracleConnectVars.PASSWORD);
        getOracleIndexObjectList(connection);
        connection.close();
    }

    //获取索引对象数组
    public static List<OracleIndexObject> getOracleIndexObjectList(Connection connection){
        //初始化对象数组
        List<OracleIndexObject> oracleIndexObjectList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(QUERY_SQL);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()){
                String indexName = resultSet.getString("INDEX_NAME");
                String columnName = resultSet.getString("COLUMN_NAME");
                String constraintType = resultSet.getString("CONSTRAINT_TYPE");
                String tableName = resultSet.getString("TABLE_NAME");
                //构造索引对象
                OracleIndexObject oracleIndexObject = new OracleIndexObject(indexName, columnName, constraintType, tableName);
                logger.info("获取索引对象：{}",oracleIndexObject);
                //放入索引对象数组
                oracleIndexObjectList.add(oracleIndexObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oracleIndexObjectList;
    }
}
