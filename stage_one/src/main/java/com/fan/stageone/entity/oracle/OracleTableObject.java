package com.fan.stageone.entity.oracle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OracleTableObject {
    private String tableName;
    private List<OracleTableColumn> oracleTableColumnList = new ArrayList<OracleTableColumn>();
}
