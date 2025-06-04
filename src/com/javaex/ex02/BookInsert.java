package com.javaex.ex02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookInsert {
	
	public static void main(String[] args) {
		
		//0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			// 1.JDBC 드라이버 (MySQL) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2.Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/web_db";
			conn = DriverManager.getConnection(url,"web","web");
			
			// 3.SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " insert into book\n ";
			query += " values(15, ?, ?, ?, ?)\n ";
			
			// 바인딩
			pstmt = conn.prepareStatement(query);
			
			String title = "무한도전";
			String pubs = "MBC";
			String pubDate = "2012-02-04";
			int authorId = 11;
			
			System.out.println(bindQuery(query, title, pubs, pubDate, authorId));
			
			pstmt.setString(1,title);
			pstmt.setString(2,pubs);
			pstmt.setString(3,pubDate);
			pstmt.setInt(4,authorId);
			
			// 실행
			int count = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println(count + " 건이 입력되었습니다.");
			
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			// 5.자원정리
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error: " + e);
			}
			
		}
	}
	
	public static String bindQuery(String query, Object... params) {
        for (Object param : params) {
            String value;
            if (param instanceof String) {
                value = "'" + param.toString().replace("'", "''") + "'";
            } else {
                value = String.valueOf(param);
            }
            query = query.replaceFirst("\\?", value);
        }
        return query;
    }

}
