package com.fan.stageone.options;

import com.fan.common.entity.oracle.*;
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

public class OracleOptions {

    private static final Logger logger = LoggerFactory.getLogger(OracleOptions.class);

    /**
     * 从oracle获取所有表名
     * @param connection
     * @return
     */
    public static List<String> getTableNameList(Connection connection){
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
     * @param tableNameList
     * @return
     * @throws SQLException
     */
    public static Map<String, List<DbColumnObject>> getColumnsByTableName(Connection connection, List<String> tableNameList) throws SQLException {
        if (tableNameList == null){
            tableNameList = getTableNameList(connection);
        }
        HashMap<String, List<DbColumnObject>> map = new HashMap<>();
        ResultSet resultSet = null;
        for (String tName : tableNameList) {
            List<DbColumnObject> oracleColumnObjectList = new ArrayList<>();
            try(PreparedStatement statement = connection.prepareStatement(QUERY_COLUMNS_BY_TABLE_NAME_SQL)){
                statement.setString(1,tName);
                resultSet = statement.executeQuery();
                while (resultSet.next()){
                    String tableName = resultSet.getString("TABLE_NAME");//获取字段所属表名
                    String columnName = resultSet.getString("COLUMN_NAME");//获取字段名称
                    String columnType = resultSet.getString("DATA_TYPE");//获取字段类型
                    int columnSize = resultSet.getInt("DATA_LENGTH");//获取字段长度
                    //把查出来的信息创建到一个字段对象里
                    DbColumnObject oracleColumnObject = new DbColumnObject(columnName, columnType, columnSize, tableName);
                    logger.info("获取oracle字段对象：{}",oracleColumnObject);
                    //把字段对象放入数组里
                    oracleColumnObjectList.add(oracleColumnObject);
                }
                map.put(tName, oracleColumnObjectList);
            }finally {
                resultSet.close();
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
    public static List<OracleTableObject> getOracleObjectList(Connection connection) throws SQLException {
        Map<String, List<DbColumnObject>> map = getColumnsByTableName(connection, null);
        ArrayList<OracleTableObject> list = new ArrayList<>();
        for (Map.Entry<String, List<DbColumnObject>> entry : map.entrySet()){
            OracleTableObject oracleTableObject = new OracleTableObject(entry.getKey(), entry.getValue());
            logger.info("获取oracle表对象:{}", oracleTableObject);
            list.add(oracleTableObject);
        }
        return list;
    }

    /**
     * 从oracle获取索引对象
     * @param connection
     * @return
     */
    public static List<OracleIndexObject> getOracleIndexObjectList(Connection connection){
        //初始化对象数组
        List<OracleIndexObject> oracleIndexObjectList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(QUERY_INDEX_SQL);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()){
                String indexName = resultSet.getString("INDEX_NAME");
                String columnName = resultSet.getString("COLUMN_NAME");
                String constraintType = resultSet.getString("CONSTRAINT_TYPE");
                String tableName = resultSet.getString("TABLE_NAME");
                //构造索引对象
                OracleIndexObject oracleIndexObject = new OracleIndexObject(indexName, columnName, constraintType, tableName);
                logger.info("获取oracle索引对象：{}",oracleIndexObject);
                //放入索引对象数组
                oracleIndexObjectList.add(oracleIndexObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oracleIndexObjectList;
    }

    /**
     * 从oracle获取约束对象
     * @param connection
     * @return
     */
    public static List<OracleConstraintObject> getOracleConstraintObjectList(Connection connection){
        //初始化对象数组
        List<OracleConstraintObject> oracleConstraintObjectList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(QUERY_CONSTRAINT_SQL);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()){
                String constraintName = resultSet.getString("CONSTRAINT_NAME");
                String constraintType = resultSet.getString("CONSTRAINT_TYPE");
                String columnName = resultSet.getString("COLUMN_NAME");
                String tableName = resultSet.getString("TABLE_NAME");
                //构造约束对象
                OracleConstraintObject oracleConstraintObject = new OracleConstraintObject(constraintName, constraintType, columnName, tableName);
                logger.info("获取oracle约束对象：{}", oracleConstraintObject);
                //放入约束对象数组
                oracleConstraintObjectList.add(oracleConstraintObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oracleConstraintObjectList;
    }

    /**
     * 从oracle获取视图对象
     * @param connection
     * @return
     */
    public static List<OracleViewObject> getOracleViewObjectList(Connection connection){
        ArrayList<OracleViewObject> oracleViewObjectList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(QUERY_VIEW_SQL)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String viewName = resultSet.getString("VIEW_NAME");
                String text = resultSet.getString("TEXT");
                OracleViewObject oracleViewObject = new OracleViewObject(viewName,text);
                logger.info("获取oracle视图对象:{}",oracleViewObject);
                oracleViewObjectList.add(oracleViewObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oracleViewObjectList;
    }
}