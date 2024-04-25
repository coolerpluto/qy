package com.fan.common.sql;

public interface PostgresSql {
    String QUERY_ALL_TABLE = "SELECT * FROM pg_tables WHERE schemaname = 'public'";
    String DELETE_SINGLE_TABLE = "drop table if exists ";
    String QUERY_SINGLE_CONSTRAINT = "select \n" +
            "ct.conname constraint_name,\n" +
            "ct.contype constraint_type,\n" +
            "c.relname table_name,\n" +
            "attr.attname column_name\n" +
            "from pg_constraint ct \n" +
            "join pg_class c on ct.conrelid = c.oid\n" +
            "join pg_attribute attr on ct.conrelid = attr.attrelid and attr.attnum = any(ct.conkey)\n" +
            "where c.relname = ? ";
}
