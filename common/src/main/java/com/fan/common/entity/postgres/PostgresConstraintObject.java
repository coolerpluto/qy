package com.fan.common.entity.postgres;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class PostgresConstraintObject implements Serializable {
    private String  constraintName;
    private String constraintType;
    private String columnName;
    private String tableName;
}
