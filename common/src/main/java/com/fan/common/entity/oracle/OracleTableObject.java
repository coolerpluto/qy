package com.fan.common.entity.oracle;

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
public class OracleTableObject implements Serializable {
    private String tableName;
    private List<DbColumnObject> oracleColumnObjectList = new ArrayList<DbColumnObject>();
}
