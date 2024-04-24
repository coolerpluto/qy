package com.fan.stageone.execution;

import com.fan.common.constants.DbType;
import com.fan.common.utils.DbUtil;
import com.fan.stageone.options.OracleOptions;

import java.sql.Connection;
import java.sql.SQLException;

public class OracleExecution {
    public static void main(String[] args) throws SQLException {
        Connection oracle = DbUtil.getDatabaseConnection(null, DbType.ORACLE);
        OracleOptions.getOracleIndexObjectList(oracle);
        OracleOptions.getOracleConstraintObjectList(oracle);
    }
}
