package com.fan.stageone.options;

import com.fan.common.entity.DbConstraintObject;
import com.fan.common.entity.DbIndexObject;
import com.fan.common.entity.DbTableObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface PostgresOptions extends DbOptions{
    List<String> getTableDDL(List<DbTableObject> objectList);

    Map<String, List<String>> getConstraintDDL(List<DbConstraintObject> objectList);

    List<String> getIndexDDL(List<DbConstraintObject> constraintObjectList ,List<DbIndexObject> indexObjectList);

    void createTableByDDL(Connection connection, Map<String, String> map);

    void createConstraintByDDL(Connection connection, Map<String, List<String>> map);

    void createIndexByDDL(Connection connection, Map<String, List<String>> map);

    void dropConstraintByDDL(Connection connection);

    void dropIndexByDDL(Connection connection, List<String> tableNameList);

    void dropTableByDDL(Connection connection, List<String> tableNameList);
}
