package com.fan.stageone.entity.oracle.index;

public class OracleIndexObject {
    private String indexName;
    private String columnName;
    private String indexType;
    private String tableName;

    public OracleIndexObject() {
    }

    public OracleIndexObject(String indexName, String columnName, String indexType, String tableName) {
        this.indexName = indexName;
        this.columnName = columnName;
        this.indexType = indexType;
        this.tableName = tableName;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "OracleIndexObject{" +
                "indexName='" + indexName + '\'' +
                ", columnName='" + columnName + '\'' +
                ", indexType='" + indexType + '\'' +
                ", tableName='" + tableName + '\'' +
                '}';
    }
}
