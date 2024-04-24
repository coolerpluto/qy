package com.fan.common.entity.oracle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OracleIndexObject implements Serializable {
    private String indexName;
    private String columnName;
    private String indexType;
    private String tableName;
}
