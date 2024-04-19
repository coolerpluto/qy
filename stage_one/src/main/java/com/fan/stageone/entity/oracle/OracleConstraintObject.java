package com.fan.stageone.entity.oracle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OracleConstraintObject {
    private String  constraintName;
    private String constraintType;
    private String columnName;
    private String tableName;
}
