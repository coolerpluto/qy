package com.fan.stageone.options.impl;

import com.fan.common.convert.ColumnConvertDict;
import com.fan.common.entity.*;
import com.fan.common.sql.PostgresSql;
import com.fan.stageone.options.DbOptions;
import com.fan.stageone.options.PostgresOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostgresOptionsImpl implements PostgresOptions {

    private static final Logger logger = LoggerFactory.getLogger(PostgresOptionsImpl.class);

    @Override
    public Map<String, String> getTableDDL(List<DbTableObject> objectList) {
        Map<String, String > map = new HashMap<>();
        for (DbTableObject tableObject : objectList) {
            String tableName = tableObject.getTableName();
            StringBuilder builder = new StringBuilder("CREATE TABLE "+tableName+"(");
            List<DbColumnObject> columnObjectList = tableObject.getDbColumnObjectList();
            for (DbColumnObject columnObject : columnObjectList) {
                builder.append(columnObject.getColumnName())
                        .append(" ")
                        .append(ColumnConvertDict.columnTypeConvert(columnObject.getColumnType()))
                        .append("(")
                        .append(columnObject.getColumnSize())
                        .append("), ");
            }
            String ddl = builder.delete(builder.length() - 2, builder.length()).append(")").toString();
            logger.info("获取表{}的ddl语句{}", tableName, ddl);
            map.put(tableName, ddl);
        }
        return map;
    }

    @Override
    public Map<String, List<String>> getConstraintDDL(List<DbConstraintObject> objectList) {
        Map<String, DbConstraintObject> constraintObjectMap = new HashMap<>();
        for (DbConstraintObject dbConstraintObject : objectList) {
            if (constraintObjectMap.containsKey((String)dbConstraintObject.getConstraintName())){

            }
        }
        return null;
    }

    @Override
    public Map<String, List<String>> getIndexDDL(List<DbIndexObject> objectList) {
        return null;
    }

    @Override
    public void createTableByDDL(Connection connection, Map<String, String> map) throws SQLException {
        connection.setAutoCommit(false);
        try(Statement statement = connection.createStatement()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                statement.addBatch(entry.getValue());
            }
            statement.executeBatch();
            connection.commit();
            logger.info("表创建成功");
        } catch (SQLException e) {
            logger.error("表创建失败，回滚事务");
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException sqlException) {
                logger.error("回滚失败");
                sqlException.printStackTrace();
            }
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void createConstraintByDDL(Connection connection, Map<String, List<String>>) {

    }

    @Override
    public void createIndexByDDL(Map<String, List<String>> map) {

    }

    @Override
    public void deleteConstraintByDDL(List<String> tableNameList) {

    }

    @Override
    public void deleteIndexByDDL(List<String> tableNameList) {

    }

    @Override
    public void deleteTableByDDL(List<String> tableNameList) {

    }

    @Override
    public List<String> getDbTableNameList(Connection connection) {
        List<String> tableNameList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(PostgresSql.QUERY_ALL_TABLE);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()){
                String tablename = resultSet.getString("tablename");
                tableNameList.add(tablename);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableNameList;
    }

    @Override
    public Map<String, List<DbColumnObject>> getDbColumnObjectList(Connection connection) {
        Map<String, List<DbColumnObject>> map = new HashMap<>();
        List<DbColumnObject> list = new ArrayList<>();
        return null;
    }

    @Override
    public List<DbTableObject> getDbTableObjectList(Connection connection) {
        return null;
    }

    @Override
    public List<DbIndexObject> getDbIndexObjectList(Connection connection) {
        return null;
    }

    @Override
    public List<DbConstraintObject> getDbConstraintObjectList(Connection connection) {
        return null;
    }

    @Override
    public List<DbViewObject> getDbViewObjectList(Connection connection) {
        return null;
    }
}
