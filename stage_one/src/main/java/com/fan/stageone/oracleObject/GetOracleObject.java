package com.fan.stageone.oracleObject;

import com.fan.stageone.constants.OracleConnectVars;
import com.fan.stageone.entity.oracle.OracleTableColumn;
import com.fan.stageone.entity.oracle.OracleTableObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetOracleObject {
    public static void main(String[] args) {
        List<OracleTableObject> oracleTableObject = getOracleTableObject(OracleConnectVars.DRIVER, OracleConnectVars.URL, OracleConnectVars.USERNAME, OracleConnectVars.PASSWORD);
        for (OracleTableObject tableObject : oracleTableObject) {
            System.out.println(tableObject);
        }
        List<String> tablesDDL = createTablesDDL(oracleTableObject);
        for (String s : tablesDDL) {
            System.out.println(s);
        }
    }

    public static List<OracleTableObject> getOracleTableObject(String driver, String url, String username, String password){
        ArrayList<OracleTableObject> oracleTableObjects = new ArrayList<OracleTableObject>();
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, username.toUpperCase(), null, new String[]{"TABLE"});
            while (tables.next()){
                OracleTableObject tableObject = new OracleTableObject();
                String tableName = tables.getString("TABLE_NAME");
                String tableOwner = tables.getString("TABLE_SCHEM");
                String tableSpacename = tables.getString("TABLE_CAT");
                tableObject.setTableName(tableName);
                tableObject.setTableOwner(tableOwner);
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
                        .append(oracleTableColumn.getColumnType())
                        .append(", ");
            }
            String substring = stringBuilder.substring(0, stringBuilder.lastIndexOf(","));
            String ddl = substring+(");");
            ddlStrings.add(ddl);
        }
        return ddlStrings;
    }
}
