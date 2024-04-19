package com.fan.stageone.getObject.oracle;

import com.fan.stageone.constants.OracleConnectVars;
import com.fan.stageone.entity.oracle.OracleViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetOracleViewObject {

    private static final Logger logger = LoggerFactory.getLogger(GetOracleViewObject.class);

    private static final String QUERY_SQL = "SELECT VIEW_NAME ,TEXT  FROM user_views";

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(OracleConnectVars.URL, OracleConnectVars.USERNAME, OracleConnectVars.PASSWORD);
        getOracleViewObjectList(connection);
        connection.close();
    }

    public static List<OracleViewObject> getOracleViewObjectList(Connection connection){
        ArrayList<OracleViewObject> oracleViewObjectList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(QUERY_SQL)){
            ResultSet resultSet = statement.executeQuery();
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
