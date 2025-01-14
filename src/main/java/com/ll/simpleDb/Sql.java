package com.ll.simpleDb;

import java.time.LocalDateTime;
import java.util.*;

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
        return 1;
    }

    public int update() {
        return simpleDb.update(sqlBuilder.toString(), params);
    }

    public int delete() {
        return simpleDb.delete(sqlBuilder.toString(), params);
    }

    public List<Map<String, Object>> selectRows() {

        List<Map<String, Object>> rows = new ArrayList<>();

        Map<String, Object> row1 = new HashMap<>();
        row1.put("id", 1L);
        row1.put("title", "제목1");
        row1.put("body", "내용1");
        row1.put("createdDate", LocalDateTime.now());
        row1.put("modifiedDate", LocalDateTime.now());
        row1.put("isBlind", false);

        Map<String, Object> row2 = new HashMap<>();
        row2.put("id", 2L);
        row2.put("title", "제목2");
        row2.put("body", "내용2");
        row2.put("createdDate", LocalDateTime.now());
        row2.put("modifiedDate", LocalDateTime.now());
        row2.put("isBlind", false);

        Map<String, Object> row3 = new HashMap<>();
        row3.put("id", 3L);
        row3.put("title", "제목3");
        row3.put("body", "내용3");
        row3.put("createdDate", LocalDateTime.now());
        row3.put("modifiedDate", LocalDateTime.now());
        row3.put("isBlind", false);

        rows.add(row1);
        rows.add(row2);
        rows.add(row3);

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
}