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
public class PostgresViewObject implements Serializable {
    private String viewName;
    private String viewDesc;
}
