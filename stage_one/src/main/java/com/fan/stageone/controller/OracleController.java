package com.fan.stageone.controller;

import com.fan.common.R;
import com.fan.constants.oracle.OracleConnectVars;
import com.fan.stageone.entity.oracle.OracleTableObject;
import com.fan.stageone.getObject.oracle.GetOracleTableObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("oracle")
public class OracleController {

    @Autowired
    private GetOracleTableObject getOracleTableObject;

    @GetMapping("table")
    public R<List<OracleTableObject>> getOracleTableObject() throws SQLException {
        Connection connection = DriverManager.getConnection(OracleConnectVars.URL, OracleConnectVars.USERNAME, OracleConnectVars.PASSWORD);
        List<OracleTableObject> oracleObjectList = getOracleTableObject.getOracleObjectList(connection);
        connection.close();
        return R.ok().message("获取表成功").data(oracleObjectList);
    }
}
