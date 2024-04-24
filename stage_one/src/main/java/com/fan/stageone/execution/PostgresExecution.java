package com.fan.stageone.execution;

import com.fan.common.constants.DbType;
import com.fan.common.sql.PostgresSql;
import com.fan.common.utils.DbUtil;
import com.fan.stageone.options.OracleOptions;
import com.fan.stageone.options.PostgresOptions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class PostgresExecution {
    public static void main(String[] args) throws SQLException {
        Connection databaseConnection = DbUtil.getDatabaseConnection(null, DbType.POSTGRES);
        PostgresOptions.deleteAllTables(databaseConnection, PostgresOptions.getTableObjectList(databaseConnection));
    }
    /**
     * 从oracle获取表DDL语句，在postgres创建相应表
     */
    public static void executeCreateTables(){
    }
}
