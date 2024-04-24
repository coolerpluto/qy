package com.fan.common.entity.postgres;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class PostgresTableObject implements Serializable {
    private String tableName;
    private List<PostgresColumnObject> columnObjectList = new ArrayList<>();
}
