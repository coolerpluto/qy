package com.fan.stageone.options.impl;

import com.fan.common.convert.ColumnConvertDict;
import com.fan.common.entity.*;
import com.fan.common.sql.PostgresSql;
import com.fan.stageone.options.PostgresOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

public class PostgresOptionsImpl implements PostgresOptions {

    private static final Logger logger = LoggerFactory.getLogger(PostgresOptionsImpl.class);

    @Override
    public List<String> getTableDDL(List<DbTableObject> objectList) {
        List<String> ddlList = new ArrayList<>();//用 linkedlist更好些
        for (DbTableObject tableObject : objectList) {
            String tableName = tableObject.getTableName();
            StringBuilder builder = new StringBuilder("CREATE TABLE \""+tableName+"\"(");
            List<DbColumnObject> columnObjectList = tableObject.getDbColumnObjectList();
            for (DbColumnObject columnObject : columnObjectList) {
                builder.append("\"")
                        .append(columnObject.getColumnName())
                        .append("\"")
                        .append(" ")
                        .append(ColumnConvertDict.columnTypeConvert(columnObject.getColumnType()))
                        .append("(")
                        .append(columnObject.getColumnSize())//还有精度、大小
                        .append("), ");
            }
            String ddl = builder.delete(builder.length() - 2, builder.length()).append(")").toString();
            logger.info("获取表{}的ddl语句{}", tableName, ddl);
            ddlList.add(ddl);
        }
        return ddlList;
    }

    @Override
    public Map<String, List<String>> getConstraintDDL(List<DbConstraintObject> objectList) {
        //
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
        Map<String, List<String>> map = new HashMap<String, List<String>>(){{
            put("P", new ArrayList<>());
            put("U", new ArrayList<>());
            put("R", new ArrayList<>());
        }};
        for (Map.Entry<String, List<DbConstraintObject>> entry : constraintObjectMap.entrySet()) {
            String tableName = null;
            String rTableName = null;
            String constraintName = entry.getKey();
            String constraintType = null;
            StringBuilder affectColumn = new StringBuilder("(");
            StringBuilder affectRColumn = new StringBuilder("(");
            List<DbConstraintObject> constraintObjectList = entry.getValue();
            for (DbConstraintObject dbConstraintObject : constraintObjectList) {
                tableName = dbConstraintObject.getTableName();
                constraintType = dbConstraintObject.getConstraintType();
                affectColumn.append("\"")
                        .append(dbConstraintObject.getColumnName())
                        .append("\"")
                        .append(", ");
                if (dbConstraintObject.getR_tableName() != null){
                    rTableName = dbConstraintObject.getR_tableName();
                    affectRColumn.append("\"")
                            .append(dbConstraintObject.getR_columnName())
                            .append("\"")
                            .append(", ");
                }
            }
            if (rTableName != null){
                affectRColumn.delete(affectRColumn.length() - 2, affectRColumn.length()).append(")");
            }
            affectColumn.delete(affectColumn.length() - 2, affectColumn.length()).append(")");
            StringBuilder builder = new StringBuilder("ALTER TABLE \"");
            builder.append(tableName)
                    .append("\"")
                    .append(" ADD CONSTRAINT \"")
                    .append(constraintName)
                    .append("\"");
            if ("P".equals(constraintType.toUpperCase())){
                builder.append(" PRIMARY KEY ")
                        .append(affectColumn);
                map.get("P").add(builder.toString());
            }else if ("U".equals(constraintType.toUpperCase())){
                builder.append(" UNIQUE ")
                        .append(affectColumn);
                map.get("U").add(builder.toString());
            }else if ("R".equals(constraintType.toUpperCase())){
                builder.append(" FOREIGN KEY ")
                        .append(affectColumn);
                if (rTableName != null){
                    builder.append(" REFERENCES ")
                            .append("\"")
                            .append(rTableName)
                            .append("\" ")
                            .append(affectRColumn);
                }
                map.get("R").add(builder.toString());
            }
            String ddl = builder.toString();
            logger.info("获取到约束创建语句：{}", ddl);
        }
        return map;
    }

    @Override
    public List<String> getIndexDDL(List<DbConstraintObject> constraintObjectList, List<DbIndexObject> indexObjectList) {
        List<String> ddlList = new ArrayList<>();
        Map<String, List<DbIndexObject>> indexMap = new HashMap<>();
        for (int i = 0; i < indexObjectList.size(); i++){
            for (int k = 0; k < constraintObjectList.size(); k++){
                DbIndexObject dbIndexObject = indexObjectList.get(i);
                DbConstraintObject dbConstraintObject = constraintObjectList.get(k);
                if (!dbIndexObject.getIndexName().equals(dbConstraintObject.getConstraintName())
                        && "PUR".indexOf(dbConstraintObject.getConstraintType().toUpperCase()) < 0){
                    if (indexMap.containsKey(dbIndexObject.getIndexName())){
                        indexMap.get(dbIndexObject.getIndexName()).add(dbIndexObject);
                    }else{
                        List<DbIndexObject> columnSingle = new ArrayList<>();
                        columnSingle.add(dbIndexObject);
                        indexMap.put(dbIndexObject.getIndexName(), columnSingle);
                    }
                }
            }
        }
        for (Map.Entry<String, List<DbIndexObject>> entry : indexMap.entrySet()) {
            StringBuilder builder = new StringBuilder("CREATE INDEX ");
            builder.append("\"")
                    .append(entry.getKey())
                    .append("\" ON \"")
                    .append(entry.getValue().get(0).getTableName())
                    .append("\"");
            List<DbIndexObject> indexObjects = entry.getValue();
            StringBuilder affectColumnBuilder = new StringBuilder(" (");
            for (DbIndexObject indexObject : indexObjects) {
                affectColumnBuilder.append("\"")
                        .append(indexObject.getColumnName())
                        .append("\"")
                        .append(", ");
            }
            String affectColumn = affectColumnBuilder.delete(affectColumnBuilder.length() - 2, affectColumnBuilder.length()).append(")").toString();
            String ddl = builder.append(affectColumn).toString();
            logger.info("索引创建语句：{}", ddl);
            ddlList.add(ddl);
        }
        return ddlList;
    }

    @Override
    public void createTableByDDL(Connection connection, List<String> ddlList){
        try(Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            ddlList.forEach(ddl->{
                try {
                    statement.addBatch(ddl);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            int[] ints = statement.executeBatch();
            connection.commit();
            logger.info("表创建成功，个数：{}", ints.length);
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
        try(Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            List<String> p_list = map.get("P");
            p_list.forEach(i ->{
                try {
                    statement.addBatch(i);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            int[] p_batch = statement.executeBatch();
            connection.commit();
            logger.info("主键约束创建成功");
            statement.clearBatch();
            List<String> u_list = map.get("U");
            u_list.forEach(u ->{
                try {
                    statement.addBatch(u);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            int[] u_batch = statement.executeBatch();
            connection.commit();
            logger.info("唯一约束创建成功");
            statement.clearBatch();
            List<String> r_list = map.get("R");
            r_list.forEach(r ->{
                try {
                    statement.addBatch(r);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            int[] r_batch = statement.executeBatch();
            logger.info("外键约束创建成功");
            connection.commit();
        } catch (SQLException e) {
            try {
                logger.info("创建约束失败，回滚");
                connection.rollback();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
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
    public void createIndexByDDL(Connection connection, Map<String, List<String>> map) {

    }

    @Override
    public void dropConstraintByDDL(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            List<DbConstraintObject> dbConstraintObjectList = getDbConstraintObjectList(connection);
            Collections.sort(dbConstraintObjectList, PgUtil.ConstraintTypeSort());
            List<String> ddlList = new ArrayList<>();
            for (DbConstraintObject dbConstraintObject : dbConstraintObjectList) {
                String deleteConstraintSql = "ALTER TABLE " + "\"" + dbConstraintObject.getTableName() + "\"" + " DROP CONSTRAINT  " + "\"" + dbConstraintObject.getConstraintName() + "\"";
                if (!ddlList.contains(deleteConstraintSql)){
                    ddlList.add(deleteConstraintSql);
                }
            }
            for (String ddl : ddlList) {
                statement.addBatch(ddl);
            }
            int[] ints = statement.executeBatch();
            connection.commit();
            logger.info("已删除所有约束，个数：{}", ints.length);
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
    public void dropIndexByDDL(Connection connection, List<String> tableNameList) {

    }

    @Override
    public void dropTableByDDL(Connection connection, List<String> tableNameList) {
        try(Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            if (tableNameList == null){
                tableNameList = getDbTableNameList(connection);
            }
            for (String tableName : tableNameList) {
                String dropTableDDL = "DROP TABLE IF EXISTS \"" +tableName + "\"";
                statement.addBatch(dropTableDDL);
            }
            int[] ints = statement.executeBatch();
            connection.commit();
            logger.info("表删除成功，个数：{}", ints.length);
        } catch (SQLException e) {
            logger.error("表删除失败");
            try {
                connection.rollback();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            e.printStackTrace();
        }
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
