package com.fan.stageone.entity.oracle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OracleIndexObject {
    private String indexName;
    private String columnName;
    private String indexType;
    private String tableName;
}
