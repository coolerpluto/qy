package com.fan.stageone.options;

import com.fan.common.constants.DbType;
import com.fan.common.entity.DbConstraintObject;
import com.fan.common.entity.DbIndexObject;
import com.fan.common.entity.DbTableObject;
import com.fan.common.utils.DbUtil;
import com.fan.stageone.options.impl.OracleOptionsImpl;
import com.fan.stageone.options.impl.PostgresOptionsImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class PostgresOptionsTest {

    private Connection postConnection;

    private Connection oracleConnection;

    private PostgresOptions postgresOptions = new PostgresOptionsImpl();

    private OracleOptions oracleOptions = new OracleOptionsImpl();

    @Before
    public void initConnection() throws Exception {
        postConnection = DbUtil.getDatabaseConnection(null, DbType.POSTGRES);
        oracleConnection = DbUtil.getDatabaseConnection(null, DbType.ORACLE);
    }

    @After
    public void closeConnection() throws Exception {
        postConnection.close();
        oracleConnection.close();
    }

    @Test
    public void getTableDDL() {
        List<DbTableObject> dbTableObjectList = oracleOptions.getDbTableObjectList(oracleConnection);
        postgresOptions.getTableDDL(dbTableObjectList);
    }

    @Test
    public void getConstraintDDL() {
        List<DbConstraintObject> dbConstraintObjectList = oracleOptions.getDbConstraintObjectList(oracleConnection);
        postgresOptions.getConstraintDDL(dbConstraintObjectList);
    }

    @Test
    public void getIndexDDL() {
        List<DbIndexObject> dbIndexObjectList = oracleOptions.getDbIndexObjectList(oracleConnection);
        List<DbConstraintObject> dbConstraintObjectList = oracleOptions.getDbConstraintObjectList(oracleConnection);
        postgresOptions.getIndexDDL(dbConstraintObjectList, dbIndexObjectList);
    }

    @Test
    public void createTableByDDL() {
        List<DbTableObject> dbTableObjectList = oracleOptions.getDbTableObjectList(oracleConnection);
        List<String > ddl = postgresOptions.getTableDDL(dbTableObjectList);
        postgresOptions.createTableByDDL(postConnection, ddl);
    }

    @Test
    public void createConstraintByDDL() {
        List<DbConstraintObject> dbConstraintObjectList = oracleOptions.getDbConstraintObjectList(oracleConnection);
        Map<String, List<String>> map = postgresOptions.getConstraintDDL(dbConstraintObjectList);
        postgresOptions.createConstraintByDDL(postConnection, map);
    }

    @Test
    public void createIndexByDDL() {
    }

    @Test
    public void dropConstraintByDDL() {
        postgresOptions.dropConstraintByDDL(postConnection);
    }

    @Test
    public void dropIndexByDDL() {
    }

    @Test
    public void dropTableByDDL() {
        postgresOptions.dropTableByDDL(postConnection, null);
    }
}