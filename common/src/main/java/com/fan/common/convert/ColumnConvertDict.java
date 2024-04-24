package com.fan.common.convert;

public class ColumnConvertDict {
    public static String columnTypeConvert(String oracleColumnType){
        switch (oracleColumnType.toUpperCase()){
            case "VARCHAR2":
                return "VARCHAR";
            case "NUMBER":
                return "NUMERIC";
            case "DATE":
                return "TIMESTAMP";
            default:
                return oracleColumnType.toUpperCase();
        }
    }
}
