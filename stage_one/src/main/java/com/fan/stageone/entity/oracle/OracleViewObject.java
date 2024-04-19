package com.fan.stageone.entity.oracle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OracleViewObject {
    private String viewName;
    private String viewDesc;
}
