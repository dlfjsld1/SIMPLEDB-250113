package com.ll.simpleDb;

import lombok.Setter;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleDb {
    // 데이터베이스 연결 정보를 저장할 변수들
    private String dbUrl;  // JDBC URL (데이터베이스 주소)
    private String dbUser; // 데이터베이스 사용자 이름
    private String dbPassword; // 데이터베이스 비밀번호
    private Connection connection; // 데이터베이스 연결 객체
    @Setter
    private boolean devMode = false; // 개발 모드 활성화 여부 (로그 출력 등에 사용)

    // 생성자: SimpleDb 객체를 만들 때 호출되는 메서드
    // 데이터베이스 연결 정보를 받아서 초기화하고, 실제 데이터베이스 연결을 시도
    public SimpleDb(String host, String user, String password, String dbName) {
        // JDBC URL 생성 (예: jdbc:mysql://localhost:3306/mydatabase)
        this.dbUrl = "jdbc:mysql://" + host + ":3306/" + dbName;
        this.dbUser = user; // 사용자 이름 설정
        this.dbPassword = password; // 비밀번호 설정

        // 데이터베이스 연결 시도
        try {
            // DriverManager를 사용하여 데이터베이스 연결을 얻어옴
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            // 개발 모드가 활성화되어 있다면 연결 성공 메시지 출력
            if (devMode) {
                System.out.println("데이터베이스에 성공적으로 연결되었습니다.");
            }
        } catch (SQLException e) {
            // 데이터베이스 연결 실패 시 예외 발생 (프로그램 종료)
            throw new RuntimeException("데이터베이스 연결 실패: " + e.getMessage());
        }
    }

    // SQL 실행 메서드 (SELECT 결과가 boolean으로 반환되는 쿼리)
    public boolean selectBoolean(String sql) {
        System.out.println("sql: " + sql);
        return _run(sql, Boolean.class);
    }

    public String selectString(String sql) {
        return _run(sql, String.class);
    }

    public Long selectLong(String sql) {
        return _run(sql, Long.class);
    }

    public LocalDateTime selectDateTime(String sql) {
        return _run(sql, LocalDateTime.class);
    }

    public Map<String, Object> selectRow(String sql) {
        return _run(sql, Map.class);
    }


    public List<Map<String, Object>> selectRows(String sql) {
        return _run(sql, List.class);
    }

    // Sql 객체 생성
    public Sql genSql() {
        return new Sql(this);
    }

    public void run(String sql, Object... params) {
        _run(sql, Integer.class, params);
    }
    // SQL 실행 메서드 (INSERT, UPDATE, DELETE 등 결과를 반환하지 않는 쿼리)
    // type - 0: boolean 1: String
    public <T> T _run(String sql, Class<T> cls, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            if (sql.startsWith("SELECT")) {
                ResultSet rs = stmt.executeQuery();

                if (cls == Boolean.class) {
                    rs.next();
                    return cls.cast(rs.getBoolean(1));
                } else if (cls == String.class) {
                    rs.next();
                    return cls.cast(rs.getString(1));
                } else if (cls == Long.class) {
                    rs.next();
                    return cls.cast(rs.getLong(1));
                } else if (cls == LocalDateTime.class) {
                    rs.next();
                    return cls.cast(rs.getTimestamp(1).toLocalDateTime());
                } else if (cls == Map.class) {
                    rs.next();
                    ResultSetMetaData metaData = rs.getMetaData();

                    int columnCount = metaData.getColumnCount();
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String cname = metaData.getColumnName(i);
                        row.put(cname, rs.getObject(i));
                    }
                    //아래에서 cast는 캐스팅을 하는데 캐스팅이란 타입을 변환하는 것을 말한다.
                    return cls.cast(row);
                } else if(cls == List.class) {
                    List<Map<String, Object>> rows = new ArrayList<>();
                    while(rs.next()) {
                        ResultSetMetaData metaData = rs.getMetaData();
                        int columnCount = metaData.getColumnCount();
                        Map<String, Object> row = new HashMap<>();
                        for (int i = 1; i <= columnCount; i++) {
                            String cname = metaData.getColumnName(i);
                            row.put(cname, rs.getObject(i));
                        }
                        rows.add(row);
                    }
                    return cls.cast(rows);
                }
            }
            // PreparedStatement를 사용하여 SQL 쿼리 실행
            setParams(stmt,params);//파라미터 설정
            return cls.cast(stmt.executeUpdate()); // 실제 반영된 로우 수 반환

        } catch (SQLException e) {
            throw new RuntimeException("SQL 실행 실패: " + e.getMessage());
        }
    }

    // PreparedStatement에 파라미터 바인딩 메서드
    private void setParams(PreparedStatement stmt, Object... params) throws SQLException {
        // 가변 인자(params)로 받은 값들을 PreparedStatement의 '?' 위치에 순서대로 설정
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]); // '?' 위치는 1부터 시작
        }
    }


}