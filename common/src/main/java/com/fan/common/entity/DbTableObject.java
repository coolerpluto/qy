package com.fan.common.entity;

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
public class DbTableObject implements Serializable {
    private String tableName;
    private List<DbColumnObject> dbColumnObjectList = new ArrayList<DbColumnObject>();
}
