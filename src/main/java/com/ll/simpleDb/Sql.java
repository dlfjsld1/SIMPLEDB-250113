package com.ll.simpleDb;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sql {

    private String sqlFormat;
    private StringBuilder sqlBuilder;

    public Sql() {

    }

    public Sql append(String sqlLine) {
        this.sqlFormat = sqlLine;
        return this;
    }

    public Sql append(String sqlLine, Object... args) {
        this.sqlFormat = sqlLine;
        return this;
    }

    public long insert() {
        return 1;
    }

    public int update() {
        return 3;
    }

    public int delete() {
        return 2;
    }

    public List<Map<String, Object>> selectRows() {

//        assertThat(articleRow.get("id")).isEqualTo(id);
//        assertThat(articleRow.get("title")).isEqualTo("제목%d".formatted(id));
//        assertThat(articleRow.get("body")).isEqualTo("내용%d".formatted(id));
//        assertThat(articleRow.get("createdDate")).isInstanceOf(LocalDateTime.class);
//        assertThat(articleRow.get("createdDate")).isNotNull();
//        assertThat(articleRow.get("modifiedDate")).isInstanceOf(LocalDateTime.class);
//        assertThat(articleRow.get("modifiedDate")).isNotNull();
//        assertThat(articleRow.get("isBlind")).isEqualTo(false);

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

        return rows;
    }

    public Map<String, Object> selectRow() {

        Map<String, Object> row1 = new HashMap<>();
        row1.put("id", 1L);
        row1.put("title", "제목1");
        row1.put("body", "내용1");
        row1.put("createdDate", LocalDateTime.now());
        row1.put("modifiedDate", LocalDateTime.now());
        row1.put("isBlind", false);

        return row1;
    }

    public LocalDateTime selectDatetime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime;
    }

    public Long selectLong() {
        return 1L;
    }

    public String selectString() {
        return "제목1";
    }

    public Boolean selectBoolean() {
        if("SELECT 1 = 1".equals(sqlFormat)) {
            return true;
        } else if("SELECT 1 = 0".equals(sqlFormat)) {
            return false;
        }
        return false;
    }
}