package com.ll.simpleDb;

import java.sql.*;

public class SimpleDb {
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private Connection connection;
    private boolean devMode = false;

    // 생성자: 데이터베이스 연결 정보 초기화
    public SimpleDb(String host, String user, String password, String dbName) {
        this.dbUrl = "jdbc:mysql://" + host + ":3306/" + dbName; // JDBC URL
        this.dbUser = user;                                    // 사용자 이름
        this.dbPassword = password;                            // 비밀번호

        // 연결 초기화
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            if (devMode) {
                System.out.println("데이터베이스에 성공적으로 연결되었습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 연결 실패: " + e.getMessage());
        }
    }

    // 개발 모드 설정
    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

    // SQL 실행 (DDL, DML 등 반환값이 없는 쿼리)
    public void run(String sql) {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
            if (devMode) {
                System.out.println("SQL 실행 완료: " + sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException("SQL 실행 실패: " + e.getMessage());
        }
    }

    // SQL 실행 (PreparedStatement와 파라미터)
    public void run(String sql, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setParams(stmt, params); // 파라미터 설정
            stmt.executeUpdate();
            if (devMode) {
                System.out.println("SQL 실행 완료: " + sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException("SQL 실행 실패: " + e.getMessage());
        }
    }

    // SELECT 쿼리 실행 (결과 반환)
    public ResultSet select(String sql, Object... params) {
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            setParams(stmt, params); // 파라미터 설정
            return stmt.executeQuery(); // 결과 집합 반환
        } catch (SQLException e) {
            throw new RuntimeException("SELECT 실행 실패: " + e.getMessage());
        }
    }

    // PreparedStatement에 파라미터 바인딩
    private void setParams(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]); // '?' 위치에 값 설정
        }
    }

    // 데이터베이스 연결 종료
    public void close() {
        if (connection != null) {
            try {
                connection.close();
                if (devMode) {
                    System.out.println("데이터베이스 연결 종료.");
                }
            } catch (SQLException e) {
                throw new RuntimeException("데이터베이스 연결 종료 실패: " + e.getMessage());
            }
        }
    }

    public Sql genSql() {
        return new Sql();
    }
}