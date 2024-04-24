package com.fan.stageone.execution;

import com.fan.common.constants.DbType;
import com.fan.common.entity.DbTableObject;
import com.fan.common.utils.DbUtil;
import com.fan.stageone.options.impl.OracleOptionsImpl;
import com.fan.stageone.options.impl.PostgresOptionsImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class PostgresExecution {
    public static void main(String[] args) throws SQLException {
        Connection connection = DbUtil.getDatabaseConnection(null, DbType.ORACLE);
        Connection databaseConnection = DbUtil.getDatabaseConnection(null, DbType.POSTGRES);
        OracleOptionsImpl oracleOptions = new OracleOptionsImpl();
        List<DbTableObject> dbTableObjectList = oracleOptions.getDbTableObjectList(connection);
        PostgresOptionsImpl postgresOptions = new PostgresOptionsImpl();
        Map<String, String> tableDDL = postgresOptions.getTableDDL(dbTableObjectList);
        postgresOptions.createTableByDDL(databaseConnection, tableDDL);
    }
}
