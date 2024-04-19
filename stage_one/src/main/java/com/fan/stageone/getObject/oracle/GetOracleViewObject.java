package com.fan.stageone.getObject.oracle;

import com.fan.stageone.constants.OracleConnectVars;
import com.fan.stageone.entity.oracle.view.OracleViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetOracleViewObject {

    private static final Logger logger = LoggerFactory.getLogger(GetOracleViewObject.class);

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(OracleConnectVars.URL, OracleConnectVars.USERNAME, OracleConnectVars.PASSWORD);
        getOracleViewObjectList(connection);
        connection.close();
    }

    public static List<OracleViewObject> getOracleViewObjectList(Connection connection){
        ArrayList<OracleViewObject> oracleViewObjectList = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            String queryView = "SELECT VIEW_NAME ,TEXT  FROM user_views";
            ResultSet resultSet = statement.executeQuery(queryView);
            while (resultSet.next()){
                String viewName = resultSet.getString("VIEW_NAME");
                String text = resultSet.getString("TEXT");
                OracleViewObject oracleViewObject = new OracleViewObject(viewName,text);
                logger.info("获取视图对象:{}",oracleViewObject);
                oracleViewObjectList.add(oracleViewObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oracleViewObjectList;
    }

}
