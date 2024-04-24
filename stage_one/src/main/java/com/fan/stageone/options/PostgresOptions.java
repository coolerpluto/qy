package com.fan.stageone.options;

import com.fan.common.entity.DbConstraintObject;
import com.fan.common.entity.DbIndexObject;
import com.fan.common.entity.DbTableObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface PostgresOptions extends DbOptions{
    Map<String, String> getTableDDL(List<DbTableObject> objectList);

    Map<String, List<String>> getConstraintDDL(List<DbConstraintObject> objectList);

    Map<String, List<String>> getIndexDDL(List<DbIndexObject> objectList);

    void createTableByDDL(Connection connection, Map<String, String> map) throws SQLException;

    void createConstraintByDDL(Connection connection, Map<String, List<String>>);

    void createIndexByDDL(Map<String, List<String>> map);

    void deleteConstraintByDDL(List<String> tableNameList);

    void deleteIndexByDDL(List<String> tableNameList);

    void deleteTableByDDL(List<String> tableNameList);
}
