package com.fan.stageone.dataCreation;

import com.fan.stageone.constants.OracleConnectVars;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertData {
    public static void main(String[] args) {
        insertSchoolClass();
    }

    public static void insertTeachers(){
        try (Connection connection = DriverManager.getConnection(OracleConnectVars.URL, OracleConnectVars.USERNAME, OracleConnectVars.PASSWORD);
             Statement statement = connection.createStatement();){

            for (int i = 2; i < 20; i++){
                String insertTeacher = "insert into TEACHER(id,name) values("+i+",'teacher"+i+"')";
                statement.executeQuery(insertTeacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertStudents(){
        try (Connection connection = DriverManager.getConnection(OracleConnectVars.URL, OracleConnectVars.USERNAME, OracleConnectVars.PASSWORD);
             Statement statement = connection.createStatement();){

            for (int i = 0; i < 20; i++){
                String insertStudent = "insert into STUDENT(id,name,teacher_id) values("+i+",'student"+i+"',"+i+")";
                statement.executeQuery(insertStudent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertSchoolClass(){
        try (Connection connection = DriverManager.getConnection(OracleConnectVars.URL, OracleConnectVars.USERNAME, OracleConnectVars.PASSWORD);
             Statement statement = connection.createStatement();){

            for (int i = 0; i < 2; i++){
                String insertSchoolClass = "insert into schoolClass(id,name) values("+i+",'schoolClass"+i+"')";
                statement.executeQuery(insertSchoolClass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
