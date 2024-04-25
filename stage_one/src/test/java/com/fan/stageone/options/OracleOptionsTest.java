package com.fan.stageone.options;

import com.fan.common.constants.DbType;
import com.fan.common.entity.*;
import com.fan.common.utils.DbUtil;
import com.fan.stageone.options.impl.OracleOptionsImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class OracleOptionsTest {

    private Connection connection;

    private OracleOptions options = new OracleOptionsImpl();

    @Before
    public void setConnection(){
        try {
            connection = DbUtil.getDatabaseConnection(null, DbType.ORACLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDbTableNameList(){
       options.getDbTableObjectList(connection);
    }

    @Test
    public void getDbColumnObjectList(){
        options.getDbColumnObjectList(connection);
    }

    @Test
    public void getDbTableObjectList(){
        options.getDbTableObjectList(connection);
    }

    @Test
    public void getDbIndexObjectList(){
        options.getDbIndexObjectList(connection);
    }

    @Test
    public void getDbConstraintObjectList(){
        options.getDbConstraintObjectList(connection);
    }

    @Test
    public void getDbViewObjectList(){
        options.getDbViewObjectList(connection);
    }
}