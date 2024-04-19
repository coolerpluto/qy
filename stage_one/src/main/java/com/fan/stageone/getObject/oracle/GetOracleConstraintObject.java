package com.fan.stageone.getObject.oracle;

import com.fan.stageone.entity.oracle.OracleConstraintObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class GetOracleConstraintObject {
    private static final Logger logger = LoggerFactory.getLogger(GetOracleConstraintObject.class);

    //定义查询约束名称、约束类型、约束字段名称、约束所在表名的sql语句
    private static final String QUERY_SQL = "SELECT uc.CONSTRAINT_NAME ,uc.CONSTRAINT_TYPE, ucc.COLUMN_NAME,uc.TABLE_NAME\n" +
            "FROM user_tables ut\n" +
            "JOIN USER_CONSTRAINTS uc ON ut.TABLE_NAME = uc.TABLE_NAME \n" +
            "JOIN USER_CONS_COLUMNS ucc ON uc.CONSTRAINT_NAME = ucc.CONSTRAINT_NAME ";

    //获取约束对象
    public List<OracleConstraintObject> getOracleConstraintObjectList(Connection connection){
        //初始化对象数组
        List<OracleConstraintObject> oracleConstraintObjectList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(QUERY_SQL);
             ResultSet resultSet = statement.executeQuery()){
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
