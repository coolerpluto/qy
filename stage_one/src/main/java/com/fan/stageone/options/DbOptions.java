package com.fan.stageone.options;

import com.fan.common.entity.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface DbOptions {
    List<String> getDbTableNameList(Connection connection);

    Map<String, List<DbColumnObject>> getDbColumnObjectList(Connection connection);

    List<DbTableObject> getDbTableObjectList(Connection connection);

    List<DbIndexObject> getDbIndexObjectList(Connection connection);

    List<DbConstraintObject> getDbConstraintObjectList(Connection connection);

    List<DbViewObject> getDbViewObjectList(Connection connection);
}
