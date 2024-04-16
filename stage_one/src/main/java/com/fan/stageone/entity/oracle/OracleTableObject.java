package com.fan.stageone.entity.oracle;

import java.util.ArrayList;
import java.util.List;

public class OracleTableObject {
    private String tableName;

    private String tableOwner;

    private String tableSpacename;

    private List<OracleTableColumn> oracleTableColumnList = new ArrayList<OracleTableColumn>();

    public OracleTableObject() {
    }

    public OracleTableObject(String tableName, String tableOwner, String tableSpacename, List<OracleTableColumn> oracleTableColumnList) {
        this.tableName = tableName;
        this.tableOwner = tableOwner;
        this.tableSpacename = tableSpacename;
        this.oracleTableColumnList = oracleTableColumnList;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableOwner() {
        return tableOwner;
    }

    public void setTableOwner(String tableOwner) {
        this.tableOwner = tableOwner;
    }

    public String getTableSpacename() {
        return tableSpacename;
    }

    public void setTableSpacename(String tableSpacename) {
        this.tableSpacename = tableSpacename;
    }

    public List<OracleTableColumn> getOracleTableColumnList() {
        return oracleTableColumnList;
    }

    public void setOracleTableColumnList(List<OracleTableColumn> oracleTableColumnList) {
        this.oracleTableColumnList = oracleTableColumnList;
    }

    @Override
    public String toString() {
        return "TableObject{" +
                "tableName='" + tableName + '\'' +
                ", tableOwner='" + tableOwner + '\'' +
                ", tableSpacename='" + tableSpacename + '\'' +
                ", tableColumnList=" + oracleTableColumnList +
                '}';
    }
}
