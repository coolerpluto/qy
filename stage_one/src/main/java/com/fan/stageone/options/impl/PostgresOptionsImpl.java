package com.fan.stageone.options.impl;

import com.fan.common.convert.ColumnConvertDict;
import com.fan.common.entity.*;
import com.fan.common.sql.PostgresSql;
import com.fan.stageone.options.DbOptions;
import com.fan.stageone.options.PostgresOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

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
        Map<String, List<DbConstraintObject>> constraintObjectMap = new HashMap<>();
        for (DbConstraintObject object : objectList) {
            List<DbConstraintObject> dbConstraintObjectList;
            if (constraintObjectMap.containsKey(object.getConstraintName())){
                dbConstraintObjectList = constraintObjectMap.get(object.getConstraintName());
                dbConstraintObjectList.add(object);
            }else {
                dbConstraintObjectList = new ArrayList<>();
                dbConstraintObjectList.add(object);
                constraintObjectMap.put(object.getConstraintName(), dbConstraintObjectList);
            }
        }
        System.out.println(constraintObjectMap);
        Map<String, List<String>> map = new HashMap<>();
        for (Map.Entry<String, List<DbConstraintObject>> entry : constraintObjectMap.entrySet()) {
            String tableName = null;
            String constraintName = entry.getKey();
            String constraintType = null;
            StringBuilder affectColumn = new StringBuilder("(");
            List<DbConstraintObject> constraintObjectList = entry.getValue();
            if (constraintObjectList.size() > 1){
                for (DbConstraintObject dbConstraintObject : constraintObjectList) {
                    tableName = dbConstraintObject.getTableName();
                    constraintType = dbConstraintObject.getConstraintType();
                    affectColumn.append(dbConstraintObject.getColumnName())
                            .append(", ");
                }
                affectColumn.delete(affectColumn.length() - 2, affectColumn.length()).append(")");
            }else {
                DbConstraintObject constraintObject = constraintObjectList.get(0);
                tableName = constraintObject.getTableName();
                constraintType = constraintObject.getConstraintType();
                affectColumn.append(constraintObject.getColumnName())
                        .append(")");
            }
            StringBuilder builder = new StringBuilder("ALTER TABLE ");
            builder.append(tableName)
                    .append(" ADD CONSTRAINT ")
                    .append(constraintName);
            if ("P".equals(constraintType.toUpperCase())){
                builder.append(" PRIMARY KEY ")
                        .append(affectColumn);
            }else if ("U".equals(constraintType.toUpperCase())){
                builder.append(" UNIQUE ")
                        .append(affectColumn);
            }else if ("R".equals(constraintType.toUpperCase())){
                builder.append(" FOREIGN KEY ")
                        .append(affectColumn);
            }
            System.out.println(builder.toString());
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
    public void createConstraintByDDL(Connection connection, Map<String, List<String>> map) {

    }

    @Override
    public void createIndexByDDL(Connection connection, Map<String, List<String>> map) {

    }

    @Override
    public void deleteConstraintByDDL(Connection connection, List<String> tableNameList) {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            List<DbConstraintObject> dbConstraintObjectList = getDbConstraintObjectList(connection);
            Collections.sort(dbConstraintObjectList, PgUtil.ConstraintTypeSort());
            for (DbConstraintObject dbConstraintObject : dbConstraintObjectList) {
                String deleteConstraintSql = "ALTER TABLE " + dbConstraintObject.getTableName() + " DROP CONSTRAINT  " + dbConstraintObject.getConstraintName();
                statement.addBatch(deleteConstraintSql);
            }
            statement.executeBatch();
            connection.commit();
            logger.info("已删除所有约束");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteIndexByDDL(Connection connection, List<String> tableNameList) {

    }

    @Override
    public void deleteTableByDDL(Connection connection, List<String> tableNameList) {

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
        List<DbConstraintObject> constraintObjectList = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(PostgresSql.QUERY_SINGLE_CONSTRAINT)) {
            List<String> tableNameList = getDbTableNameList(connection);
            for (String tableName : tableNameList) {
                statement.setString(1, tableName);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    String constraint_name = resultSet.getString("constraint_name");
                    String constraint_type = resultSet.getString("constraint_type");
                    String table_name = resultSet.getString("table_name");
                    String columnName = resultSet.getString("column_name");
                    DbConstraintObject dbConstraintObject = new DbConstraintObject(constraint_name, constraint_type, columnName, table_name);
                    logger.info("获取postgres约束对象{}", dbConstraintObject);
                    constraintObjectList.add(dbConstraintObject);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return constraintObjectList;
    }

    @Override
    public List<DbViewObject> getDbViewObjectList(Connection connection) {
        return null;
    }
}

class PgUtil{

    public static Comparator<DbConstraintObject> ConstraintTypeSort(){
        Comparator<DbConstraintObject> comparator = new Comparator<DbConstraintObject>() {
            @Override
            public int compare(DbConstraintObject o1, DbConstraintObject o2) {
                String sortDefine = "FPU";
                return sortDefine.indexOf(o1.getConstraintType().toUpperCase().charAt(0)) - sortDefine.indexOf(o2.getConstraintType().toUpperCase().charAt(0));
            }
        };
        return comparator;
    }
}
