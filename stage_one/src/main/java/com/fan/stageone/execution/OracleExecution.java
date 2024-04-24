package com.fan.stageone.execution;

import com.fan.common.constants.DbType;
import com.fan.common.utils.DbUtil;
import com.fan.stageone.options.DbOptions;
import com.fan.stageone.options.impl.OracleOptionsImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class OracleExecution {
    public static void main(String[] args) throws SQLException {
        Connection connection = DbUtil.getDatabaseConnection(null, DbType.ORACLE);
        OracleOptionsImpl oracleOptions = new OracleOptionsImpl();
        oracleOptions.getDbTableObjectList(connection);
        oracleOptions.getDbConstraintObjectList(connection);
    }
}
