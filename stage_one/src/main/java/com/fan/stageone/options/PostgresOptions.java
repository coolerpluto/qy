package com.fan.stageone.options;

import com.fan.common.convert.ColumnConvertDict;
import com.fan.common.entity.oracle.DbColumnObject;
import com.fan.common.entity.oracle.OracleConstraintObject;
import com.fan.common.entity.oracle.OracleIndexObject;
import com.fan.common.entity.oracle.OracleTableObject;
import com.fan.common.entity.postgres.PostgresTableObject;
import com.fan.common.sql.PostgresSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostgresOptions {

    private static final Logger logger = LoggerFactory.getLogger(PostgresOptions.class);

    /**
     * 获取postgres表对象
     * @param connection
     * @return
     */
    public static List<PostgresTableObject> getTableObjectList(Connection connection){
        List<PostgresTableObject> tableObjectList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(PostgresSql.QUERY_ALL_TABLE)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                PostgresTableObject tableObject = new PostgresTableObject(resultSet.getString("tablename"));
                logger.info("获取postgres表对象：{}", tableObject);
                tableObjectList.add(tableObject);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  tableObjectList;
    }

    public static List<>



    /**
     * 从oracle获取表结构创建DDL语句供postgres数据库创建对应表
     * @param connection
     * @return
     */
    public static Map<String, String> getTableDDLByOracleTableObject(Connection connection){
        HashMap<String, String> map = new HashMap<>();
        List<OracleTableObject> oracleObjectList;
        try {
            oracleObjectList = OracleOptions.getOracleObjectList(connection);
            for (OracleTableObject oracleTableObject : oracleObjectList) {
                String tableName = oracleTableObject.getTableName();
                StringBuilder builder = new StringBuilder();
                builder.append("CREATE TABLE ")
                        .append(tableName)
                        .append("(");
                for (DbColumnObject oracleColumnObject : oracleTableObject.getOracleColumnObjectList()) {
                    builder.append(oracleColumnObject.getColumnName())
                            .append(" ")
                            .append(ColumnConvertDict.columnTypeConvert(oracleColumnObject.getColumnType()))
                            .append("(")
                            .append(oracleColumnObject.getColumnSize())
                            .append(")")
                            .append(", ");
                }
                builder.delete(builder.length() - 2, builder.length());
                String ddl = builder.append(")").toString();
                logger.info("获取oracle表:{}的DDL语句:{}", tableName, ddl);
                map.put(tableName, ddl);
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return map;
    }

    public static Map<String, String > getIndexDDLByOracleIndexObject (Connection connection){
        Map<String, String> map = new HashMap<>();
        List<OracleIndexObject> indexObjectList = OracleOptions.getOracleIndexObjectList(connection);
        for (OracleIndexObject indexObject : indexObjectList){
            String tableName = indexObject.getTableName();
            StringBuilder builder = new StringBuilder();
        }
        return map;
    }

    public static Map<String, String> getOracleConstraintDDLConvert(Connection connection){
        Map<String , String> map = new HashMap<>();
        List<OracleConstraintObject> constraintObjectList = OracleOptions.getOracleConstraintObjectList(connection);
        for (OracleConstraintObject oracleConstraintObject : constraintObjectList) {

        }
        return map;
    }

    /**
     * 执行表创建DDL语句
     * @param connection
     * @param ddlMap
     * @throws SQLException
     */
    public static void createTableByDDL(Connection connection, Map<String, String> ddlMap){
        for (Map.Entry<String, String> entry : ddlMap.entrySet()) {
            try (PreparedStatement statement = connection.prepareStatement(entry.getValue())){
                boolean execute = statement.execute();
                logger.info("{}表创建{}", entry.getKey(), execute == true ? "成功": "失败");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createConstraintByDDL(Connection connection, String tableName, String ddl) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(ddl)) {

        }
    }

    /**
     * 删除postgres所有表
     * @param connection
     * @param tableObjectList
     */
    public static void deleteAllTables(Connection connection, List<PostgresTableObject> tableObjectList){
        List<String> tableNameList = new ArrayList<>();
        for (PostgresTableObject object : tableObjectList) {
            tableNameList.add(object.getTableName());
        }
        try {
            connection.setAutoCommit(false);
            try(Statement statement = connection.createStatement()){
                for (String name : tableNameList) {
                    String ddl = PostgresSql.DELETE_SINGLE_TABLE + name;
                    statement.addBatch(ddl);
                }
                statement.executeBatch();
                connection.commit();
            }catch (SQLException e){
                logger.error("删除表失败，回滚");
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error("关闭自动提交失败{}", e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.info("恢复自动提交");
                e.printStackTrace();
            }
        }
    }

}
