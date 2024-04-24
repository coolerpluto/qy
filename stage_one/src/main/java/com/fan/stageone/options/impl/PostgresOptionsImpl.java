package com.fan.stageone.options.impl;

import com.fan.common.convert.ColumnConvertDict;
import com.fan.common.entity.*;
import com.fan.common.sql.PostgresSql;
import com.fan.stageone.options.DbOptions;
import com.fan.stageone.options.PostgresOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostgresOptionsImpl implements PostgresOptions {

    private static final Logger logger = LoggerFactory.getLogger(PostgresOptionsImpl.class);

    @Override
    public Map<String, String> getTableDDL(List<DbTableObject> objectList) {
        return null;
    }

    @Override
    public Map<String, List<String>> getConstraintDDL(List<DbConstraintObject> objectList) {
        return null;
    }

    @Override
    public Map<String, List<String>> getIndexDDL(List<DbIndexObject> objectList) {
        return null;
    }

    @Override
    public void creteTableByDDL(Connection connection, Map<String, String> map) {

    }

    @Override
    public void createConstraintByDDL(Map<String, List<String>> map) {

    }

    @Override
    public void createIndexByDDL(Map<String, List<String>> map) {

    }

    @Override
    public void deleteConstraintByDDL(List<String> tableNameList) {

    }

    @Override
    public void deleteIndexByDDL(List<String> tableNameList) {

    }

    @Override
    public void deleteTableByDDL(List<String> tableNameList) {

    }

    @Override
    public List<String> getDbTableNameList(Connection connection) {
        List<String> tableNameList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(PostgresSql.QUERY_ALL_TABLE);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()){
                String tablename = resultSet.getString("tablename");
                tableNameList.add(tablename);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableNameList;
    }

    @Override
    public Map<String, List<DbColumnObject>> getDbColumnObjectList(Connection connection) {
        return null;
    }

    @Override
    public List<DbTableObject> getDbTableObjectList(Connection connection) {
        return null;
    }

    @Override
    public List<DbIndexObject> getDbIndexObjectList(Connection connection) {
        return null;
    }

    @Override
    public List<DbConstraintObject> getDbConstraintObjectList(Connection connection) {
        return null;
    }

    @Override
    public List<DbViewObject> getDbViewObjectList(Connection connection) {
        return null;
    }
}
