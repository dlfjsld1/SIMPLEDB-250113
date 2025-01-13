package com.ll.simpleDb;

public class Sql {

    private StringBuilder sqlBuilder;

    public Sql() {
        this.sqlBuilder = new StringBuilder();
    }

    public Sql append(String sqlLine) {
        sqlBuilder.append(sqlLine);
        return this;
    }

    public Sql append(String sqlLine, Object... args) {
        sqlBuilder.append(sqlLine);
        return this;
    }

    public long insert() {
        return 1;
    }

    public int update() {
        return 3;
    }
}