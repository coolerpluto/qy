package com.fan.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class DbConstraintObject implements Serializable {
    private String  constraintName;
    private String constraintType;
    private String columnName;
    private String tableName;
    private String r_columnName;
    private String r_tableName;

    public DbConstraintObject(String constraintName, String constraintType, String columnName, String tableName) {
        this.constraintName = constraintName;
        this.constraintType = constraintType;
        this.columnName = columnName;
        this.tableName = tableName;
    }
}
