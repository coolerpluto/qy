package com.fan.common.sql;

public interface PostgresSql {
    String QUERY_ALL_TABLE = "SELECT * FROM pg_tables WHERE schemaname = 'public'";
    String DELETE_SINGLE_TABLE = "drop table if exists ";
}
