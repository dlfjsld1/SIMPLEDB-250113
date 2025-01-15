package com.ll.simpleDb;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Sql {

    private final SimpleDb simpleDb;
    private final List<Object> params;
    private final StringBuilder sqlBuilder;

    public Sql(SimpleDb simpleDb) {
        this.sqlBuilder  = new StringBuilder();
        this.simpleDb = simpleDb;
        this.params = new ArrayList<>();
    }

    public Sql append(String sqlLine) {
        this.sqlBuilder.append(sqlLine);
        this.sqlBuilder.append(" ");
        return this;
    }

    public Sql append(String sqlLine, Object... args) {
        this.params.addAll(Arrays.stream(args).toList());
        this.sqlBuilder.append(sqlLine);
        this.sqlBuilder.append(" ");
        return this;
    }

    public long insert() {
        return simpleDb.insert(sqlBuilder.toString(), params);
    }

    public int update() {
        return simpleDb.update(sqlBuilder.toString(), params);
    }

    public int delete() {
        return simpleDb.delete(sqlBuilder.toString(), params);
    }

    public <T> List<T> selectRows(Class<T> cls) {
        return simpleDb.selectRows(sqlBuilder.toString(), params, cls);
    }

    public List<Map<String, Object>> selectRows() {
        return simpleDb.selectRows(sqlBuilder.toString(), params);
    }

    public Map<String, Object> selectRow() {
            return simpleDb.selectRow(sqlBuilder.toString(), params);
//        Map<String, Object> row1 = new HashMap<>();
//        row1.put("id", 1L);
//        row1.put("title", "제목1");
//        row1.put("body", "내용1");
//        row1.put("createdDate", LocalDateTime.now());
//        row1.put("modifiedDate", LocalDateTime.now());
//        row1.put("isBlind", false);

//        return row1;
    }

    public <T> T selectRow(Class<T> cls) {
        return simpleDb.selectRow(sqlBuilder.toString(), params, cls);
    }

    public LocalDateTime selectDatetime() {
        return simpleDb.selectDateTime(sqlBuilder.toString(), params);
    }

    public Long selectLong() {
        return simpleDb.selectLong(sqlBuilder.toString(), params);
    }

    public String selectString() {
        return simpleDb.selectString(sqlBuilder.toString(), params);

    }

    public Boolean selectBoolean() {
        return simpleDb.selectBoolean(sqlBuilder.toString(), params);
    }

    public Sql appendIn(String sql, Object... args) {
        String inClause = Arrays.stream(args)
                .map(a -> "?")
                .collect(Collectors.joining(", "));

        String replacedSql = sql.replaceAll("\\?", inClause);
        this.params.addAll(Arrays.stream(args).toList());
        this.sqlBuilder.append(replacedSql);

        return this;
    }

    public List<Long> selectLongs() {
        return simpleDb.selectLongs(sqlBuilder.toString(), params);
    }
}