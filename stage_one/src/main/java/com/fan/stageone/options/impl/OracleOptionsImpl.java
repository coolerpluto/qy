package com.fan.stageone.options.impl;

import com.fan.common.entity.*;
import com.fan.stageone.options.OracleOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fan.common.sql.OracleSQL.*;

public class OracleOptionsImpl implements OracleOptions {

    private static final Logger logger = LoggerFactory.getLogger(OracleOptionsImpl.class);

    /**
     * 从oracle获取所有表名
     * @param connection
     * @return
     */
    @Override
    public List<String> getDbTableNameList(Connection connection){
        //初始化数组存放表名
        List<String> tableNameList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(USER_TABLES_SQL);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()){
                //循环获取表名，放入表名数组
                String tableName = resultSet.getString("TABLE_NAME");
                logger.info("获取oracle表名：{}",tableName);
                tableNameList.add(tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //返回结果
        return tableNameList;
    }

    /**
     * 从oracle获取字段对象
     * @param connection
     * @return
     * @throws SQLException
     */
    @Override
    public Map<String, List<DbColumnObject>> getDbColumnObjectList(Connection connection){
        List<String> tableNameList = getDbTableNameList(connection);
        Map<String, List<DbColumnObject>> map = new HashMap<>();
        ResultSet resultSet = null;
        for (String tName : tableNameList) {
            List<DbColumnObject> dbColumnObjectList = new ArrayList<>();
            try(PreparedStatement statement = connection.prepareStatement(QUERY_COLUMNS_BY_TABLE_NAME_SQL)){
                statement.setString(1,tName);
                resultSet = statement.executeQuery();
                while (resultSet.next()){
                    String tableName = resultSet.getString("TABLE_NAME");//获取字段所属表名
                    String columnName = resultSet.getString("COLUMN_NAME");//获取字段名称
                    String columnType = resultSet.getString("DATA_TYPE");//获取字段类型
                    int columnSize = resultSet.getInt("DATA_LENGTH");//获取字段长度
                    //把查出来的信息创建到一个字段对象里
                    DbColumnObject dbColumnObject = new DbColumnObject(columnName, columnType, columnSize, tableName);
                    logger.info("获取oracle字段对象：{}", dbColumnObject);
                    //把字段对象放入数组里
                    dbColumnObjectList.add(dbColumnObject);
                }
                map.put(tName, dbColumnObjectList);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        //返回结果
        return map;
    }

    /**
     * 从oracle获取表对象
     * @param connection
     * @return
     * @throws SQLException
     */
    @Override
    public List<DbTableObject> getDbTableObjectList(Connection connection){
        Map<String, List<DbColumnObject>> map = getDbColumnObjectListByTableName(connection, null);
        ArrayList<DbTableObject> list = new ArrayList<>();
        for (Map.Entry<String, List<DbColumnObject>> entry : map.entrySet()){
            DbTableObject dbTableObject = new DbTableObject(entry.getKey(), entry.getValue());
            logger.info("获取oracle表对象:{}", dbTableObject);
            list.add(dbTableObject);
        }
        return list;
    }

    /**
     * 从oracle获取索引对象
     * @param connection
     * @return
     */
    @Override
    public List<DbIndexObject> getDbIndexObjectList(Connection connection){
        //初始化对象数组
        List<DbIndexObject> dbIndexObjectList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(QUERY_INDEX_SQL);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()){
                String indexName = resultSet.getString("INDEX_NAME");
                String columnName = resultSet.getString("COLUMN_NAME");
                String constraintType = resultSet.getString("CONSTRAINT_TYPE");
                String tableName = resultSet.getString("TABLE_NAME");
                //构造索引对象
                DbIndexObject dbIndexObject = new DbIndexObject(indexName, columnName, constraintType, tableName);
                logger.info("获取oracle索引对象：{}", dbIndexObject);
                //放入索引对象数组
                dbIndexObjectList.add(dbIndexObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbIndexObjectList;
    }

    /**
     * 从oracle获取约束对象
     * @param connection
     * @return
     */
    @Override
    public List<DbConstraintObject> getDbConstraintObjectList(Connection connection){
        //初始化对象数组
        List<DbConstraintObject> dbConstraintObjectList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(QUERY_CONSTRAINT_SQL);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()){
                String constraintName = resultSet.getString("CONSTRAINT_NAME");
                String constraintType = resultSet.getString("CONSTRAINT_TYPE");
                String columnName = resultSet.getString("COLUMN_NAME");
                String tableName = resultSet.getString("TABLE_NAME");
                //构造约束对象
                DbConstraintObject dbConstraintObject = new DbConstraintObject(constraintName, constraintType, columnName, tableName);
                logger.info("获取oracle约束对象：{}", dbConstraintObject);
                //放入约束对象数组
                dbConstraintObjectList.add(dbConstraintObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbConstraintObjectList;
    }

    /**
     * 从oracle获取视图对象
     * @param connection
     * @return
     */
    @Override
    public List<DbViewObject> getDbViewObjectList(Connection connection){
        ArrayList<DbViewObject> dbViewObjectList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(QUERY_VIEW_SQL)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String viewName = resultSet.getString("VIEW_NAME");
                String text = resultSet.getString("TEXT");
                DbViewObject dbViewObject = new DbViewObject(viewName,text);
                logger.info("获取oracle视图对象:{}", dbViewObject);
                dbViewObjectList.add(dbViewObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbViewObjectList;
    }
}