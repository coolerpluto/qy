package com.fan.common.sql;

public interface OracleSQL {
    String USER_TABLES_SQL = "SELECT TABLE_NAME FROM USER_TABLES";

    String QUERY_COLUMNS_BY_TABLE_NAME_SQL = "SELECT TABLE_NAME ,COLUMN_NAME ,DATA_TYPE ,DATA_LENGTH FROM USER_TAB_COLUMNS WHERE TABLE_NAME = ?";

    //定义查询约束名称、约束类型、约束字段名称、约束所在表名的sql语句
    String QUERY_CONSTRAINT_SQL = "SELECT uc.CONSTRAINT_NAME ,uc.CONSTRAINT_TYPE, ucc.COLUMN_NAME,ucc.TABLE_NAME, rucc.COLUMN_NAME R_COLUMN_NAME,rucc.TABLE_NAME R_TABLE_NAME\n" +
            "            FROM user_tables ut\n" +
            "            JOIN USER_CONSTRAINTS uc ON ut.TABLE_NAME = uc.TABLE_NAME\n" +
            "            JOIN USER_CONS_COLUMNS ucc ON uc.CONSTRAINT_NAME = ucc.CONSTRAINT_NAME\n" +
            "            LEFT JOIN USER_CONS_COLUMNS rucc ON uc.R_CONSTRAINT_NAME = rucc.CONSTRAINT_NAME and ucc.position = rucc.POSITION";

    //获取索引名称、索引所加字段名称、索引类型、索引所在表名
    String QUERY_INDEX_SQL = "SELECT ui.INDEX_NAME  , uic.COLUMN_NAME , ui.TABLE_NAME\n" +
            "FROM user_tables ut\n" +
            "JOIN user_indexes ui ON ut.TABLE_NAME = ui.TABLE_NAME \n" +
            "JOIN USER_IND_COLUMNS uic ON ui.INDEX_NAME = uic.INDEX_NAME";

    //获取视图描述语句
    String QUERY_VIEW_SQL = "SELECT VIEW_NAME ,TEXT  FROM user_views";

}
