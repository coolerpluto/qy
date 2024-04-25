package com.fan.stageone.execution;

import com.fan.common.constants.DbType;
import com.fan.common.entity.DbConstraintObject;
import com.fan.common.entity.DbTableObject;
import com.fan.common.utils.DbUtil;
import com.fan.stageone.options.impl.OracleOptionsImpl;
import com.fan.stageone.options.impl.PostgresOptionsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class PostgresExecution {
    private static final Logger logger = LoggerFactory.getLogger(PostgresExecution.class);

    public static void main(String[] args) throws SQLException {
        Connection connection = DbUtil.getDatabaseConnection(null, DbType.POSTGRES);
        PostgresOptionsImpl postgresOptions = new PostgresOptionsImpl();
        List<DbConstraintObject> dbConstraintObjectList = postgresOptions.getDbConstraintObjectList(connection);
        Map<String, List<String>> constraintDDL = postgresOptions.getConstraintDDL(dbConstraintObjectList);
//        postgresOptions.createConstraintByDDL(connection, constraintDDL);
//        postgresOptions.deleteConstraintByDDL(connection, null);
        connection.close();
    }
}
