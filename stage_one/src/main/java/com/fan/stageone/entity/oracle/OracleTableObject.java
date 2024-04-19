package com.fan.stageone.entity.oracle;

import java.util.ArrayList;
import java.util.List;

public class OracleTableObject {
    private String tableName;

    private List<OracleTableColumn> oracleTableColumnList = new ArrayList<OracleTableColumn>();

    public OracleTableObject() {
    }

    public OracleTableObject(String tableName, List<OracleTableColumn> oracleTableColumnList) {
        this.tableName = tableName;
        this.oracleTableColumnList = oracleTableColumnList;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<OracleTableColumn> getOracleTableColumnList() {
        return oracleTableColumnList;
    }

    public void setOracleTableColumnList(List<OracleTableColumn> oracleTableColumnList) {
        this.oracleTableColumnList = oracleTableColumnList;
    }

    @Override
    public String toString() {
        return "OracleTableObject{" +
                "tableName='" + tableName + '\'' +
                ", oracleTableColumnList=" + oracleTableColumnList +
                '}';
    }
}
