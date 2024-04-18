package com.fan.stageone.databaseObject.oracle;

import com.fan.stageone.constants.OracleConnectVars;
import com.fan.stageone.entity.oracle.constraint.OracleConstraintObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetOracleConstraintObject {
    private static final Logger logger = LoggerFactory.getLogger(GetOracleConstraintObject.class);

    //定义查询约束名称、约束类型、约束字段名称、约束所在表名的sql语句
    private static final String queryConstraintList = "SELECT uc.CONSTRAINT_NAME ,uc.CONSTRAINT_TYPE, ucc.COLUMN_NAME,uc.TABLE_NAME\n" +
            "FROM user_tables ut\n" +
            "JOIN USER_CONSTRAINTS uc ON ut.TABLE_NAME = uc.TABLE_NAME \n" +
            "JOIN USER_CONS_COLUMNS ucc ON uc.CONSTRAINT_NAME = ucc.CONSTRAINT_NAME ";

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(OracleConnectVars.URL, OracleConnectVars.USERNAME, OracleConnectVars.PASSWORD);
        getOracleConstraintObjectList(connection);
    }

    //获取约束对象
    public static List<OracleConstraintObject> getOracleConstraintObjectList(Connection connection){
        //初始化对象数组
        List<OracleConstraintObject> oracleConstraintObjectList = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(queryConstraintList)){
            while (resultSet.next()){
                String constraintName = resultSet.getString("CONSTRAINT_NAME");
                String constraintType = resultSet.getString("CONSTRAINT_TYPE");
                String columnName = resultSet.getString("COLUMN_NAME");
                String tableName = resultSet.getString("TABLE_NAME");
                //构造约束对象
                OracleConstraintObject oracleConstraintObject = new OracleConstraintObject(constraintName, constraintType, columnName, tableName);
                logger.info("获取的约束对象：{}", oracleConstraintObject);
                //放入约束对象数组
                oracleConstraintObjectList.add(oracleConstraintObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oracleConstraintObjectList;
    }
}