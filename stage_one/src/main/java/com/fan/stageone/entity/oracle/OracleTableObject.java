package com.fan.stageone.entity.oracle;

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
    private List<OracleColumnObject> oracleColumnObjectList = new ArrayList<OracleColumnObject>();
}