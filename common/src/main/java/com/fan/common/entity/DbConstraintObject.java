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
}
