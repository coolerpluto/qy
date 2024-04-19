package com.fan.stageone.entity.oracle;

public class OracleTableColumn {
    private String columnName;

    private String columnType;

    private int columnSize;

    private String tableName;

    public OracleTableColumn() {
    }

    public OracleTableColumn(String columnName, String columnType, int columnSize) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.columnSize = columnSize;
    }

    public OracleTableColumn(String columnName, String columnType, int columnSize, String tableName) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.columnSize = columnSize;
        this.tableName = tableName;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "OracleTableColumn{" +
                "columnName='" + columnName + '\'' +
                ", columnType='" + columnType + '\'' +
                ", columnSize=" + columnSize +
                ", tableName='" + tableName + '\'' +
                '}';
    }
}
