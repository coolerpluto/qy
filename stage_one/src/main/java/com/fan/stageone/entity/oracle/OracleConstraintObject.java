package com.fan.stageone.entity.oracle;

public class OracleConstraintObject {
    private String  constraintName;
    private String constraintType;
    private String columnName;
    private String tableName;

    public OracleConstraintObject() {
    }

    public OracleConstraintObject(String constraintName, String constraintType, String columnName, String tableName) {
        this.constraintName = constraintName;
        this.constraintType = constraintType;
        this.columnName = columnName;
        this.tableName = tableName;
    }

    public String getConstraintName() {
        return constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    public String getConstraintType() {
        return constraintType;
    }

    public void setConstraintType(String constraintType) {
        this.constraintType = constraintType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "OracleConstraintObject{" +
                "constraintName='" + constraintName + '\'' +
                ", constraintType='" + constraintType + '\'' +
                ", columnName='" + columnName + '\'' +
                ", tableName='" + tableName + '\'' +
                '}';
    }
}
