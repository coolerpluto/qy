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
public class PostgresColumnObject implements Serializable {
    private String columnName;
    private String columnType;
    private int columnSize;
    private String tableName;
}
