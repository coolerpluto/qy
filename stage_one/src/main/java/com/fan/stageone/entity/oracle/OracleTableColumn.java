package com.fan.stageone.entity.oracle;

public class OracleTableColumn {
    private String columnName;

    private String columnType;

    public OracleTableColumn() {
    }

    public OracleTableColumn(String columnName, String columnType) {
        this.columnName = columnName;
        this.columnType = columnType;
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


    @Override
    public String toString() {
        return "TableColumn{" +
                "columnName='" + columnName + '\'' +
                ", columnType='" + columnType + '\'' +
                '}';
    }
}
