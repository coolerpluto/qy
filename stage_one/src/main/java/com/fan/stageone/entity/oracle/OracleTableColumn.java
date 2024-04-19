package com.fan.stageone.entity.oracle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OracleTableColumn {
    private String columnName;
    private String columnType;
    private int columnSize;
    private String tableName;
}
