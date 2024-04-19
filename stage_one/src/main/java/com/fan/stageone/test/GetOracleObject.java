package com.fan.stageone.test;

import com.fan.stageone.constants.OracleConnectVars;
import com.fan.stageone.entity.oracle.OracleTableColumn;
import com.fan.stageone.entity.oracle.OracleTableObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetOracleObject {
    public static void main(String[] args) {
        List<OracleTableObject> oracleTableObjectByStatement = getOracleTableObjectByStatement(OracleConnectVars.DRIVER, OracleConnectVars.URL, OracleConnectVars.USERNAME, OracleConnectVars.PASSWORD);
        for (OracleTableObject oracleTableObject : oracleTableObjectByStatement) {
            System.out.println(oracleTableObject);
        }
//        List<OracleTableObject> oracleTableObject = getOracleTableObject(OracleConnectVars.DRIVER, OracleConnectVars.URL, OracleConnectVars.USERNAME, OracleConnectVars.PASSWORD);
//        for (OracleTableObject tableObject : oracleTableObject) {
//            System.out.println(tableObject);
//        }
        List<String> tablesDDL = createTablesDDL(oracleTableObjectByStatement);
        for (String s : tablesDDL) {
            System.out.println(s);
        }
    }

    //写注释
    public static List<OracleTableObject> getOracleTableObjectByStatement(String driver, String url, String username, String password){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<OracleTableObject> oracleTableObjectList = new ArrayList<OracleTableObject>();
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url,username,password);
            DatabaseMetaData metaData = connection.getMetaData();
            resultSet = metaData.getTables(null, username.toUpperCase(), null, new String[]{"TABLE"});
            while (resultSet.next()){
                OracleTableObject oracleTableObject = new OracleTableObject();
                String tableName = resultSet.getString("TABLE_NAME");
                oracleTableObject.setTableName(tableName);
                ResultSet columns = metaData.getColumns(null, username.toUpperCase(), tableName, null);
                ArrayList<OracleTableColumn> oracleTableColumns = new ArrayList<OracleTableColumn>();
                while (columns.next()){
                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");
                    int columnSize = columns.getInt("COLUMN_SIZE");
                    OracleTableColumn oracleTableColumn = new OracleTableColumn(columnName, columnType, columnSize);
                    oracleTableColumns.add(oracleTableColumn);
                }
                oracleTableObject.setOracleTableColumnList(oracleTableColumns);
                oracleTableObjectList.add(oracleTableObject);
                columns.close();
            }
            return oracleTableObjectList;
        } catch (ClassNotFoundException e) {
            System.out.println("oracle驱动没找到");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.out.println("oracle连接失败");
            e.printStackTrace();
            return null;
        }finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static List<OracleTableObject> getOracleTableObject(String driver, String url, String username, String password){
        ArrayList<OracleTableObject> oracleTableObjects = new ArrayList<OracleTableObject>();
        Connection connection = null;
        DatabaseMetaData metaData = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
            metaData = connection.getMetaData();
            statement = connection.createStatement();
            resultSet = metaData.getTables(null, username.toUpperCase(), null, new String[]{"TABLE"});
            while (resultSet.next()){
                OracleTableObject tableObject = new OracleTableObject();
                String tableName = resultSet.getString("TABLE_NAME");
                String tableOwner = resultSet.getString("TABLE_SCHEM");
                tableObject.setTableName(tableName);
                ResultSet columns = metaData.getColumns(null, null, tableName, null);
                ArrayList<OracleTableColumn> oracleTableColumns = new ArrayList<OracleTableColumn>();

                while (columns.next()){
                    OracleTableColumn oracleTableColumn = new OracleTableColumn();
                    oracleTableColumn.setColumnName(columns.getString("COLUMN_NAME"));
                    oracleTableColumn.setColumnType(columns.getString("TYPE_NAME"));
                    oracleTableColumns.add(oracleTableColumn);
                }
                tableObject.setOracleTableColumnList(oracleTableColumns);
                oracleTableObjects.add(tableObject);
            }
            connection.close();
            return oracleTableObjects;
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver not found.");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> createTablesDDL(List<OracleTableObject> oracleTableObjects){
        List<String> ddlStrings = new ArrayList<String>();
        for (OracleTableObject oracleTableObject : oracleTableObjects) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("create table ")
                    .append(oracleTableObject.getTableName())
                    .append("(");
            for (OracleTableColumn oracleTableColumn : oracleTableObject.getOracleTableColumnList()) {
                stringBuilder.append(oracleTableColumn.getColumnName())
                        .append(" ")
                        .append(oracleTableColumn.getColumnType());
                if (oracleTableColumn.getColumnSize()>0){
                    stringBuilder.append("(")
                            .append(oracleTableColumn.getColumnSize())
                            .append(")");
                }

                        stringBuilder.append(", ");
            }
            String substring = stringBuilder.substring(0, stringBuilder.lastIndexOf(","));
            String ddl = substring+(");");
            ddlStrings.add(ddl);
        }
        return ddlStrings;
    }
}
