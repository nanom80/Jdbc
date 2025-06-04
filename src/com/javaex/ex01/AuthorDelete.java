package com.javaex.ex01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.ResultSet; // select에서 사용
import java.sql.SQLException;
	
public class AuthorDelete {
	
	public static void main(String[] args) {
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		//ResultSet rs = null; // select에서 사용
	
		try {
		    // 1. JDBC 드라이버 (MySQL) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		    // 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/web_db";
			conn = DriverManager.getConnection(url,"web","web"); // DriverManager 메소드는 static
			
		    // 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비 (쿼리 마침표(;) 적지 않는다, 바인딩할 부분은 물음표(?)로 처리)
			String query = "";
			query += " delete from author ";
			query += " where author_id = ? ";
			
			System.out.println(query);
			
			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, 10);
			
			// 실행
			int count = pstmt.executeUpdate();
		    
		    // 4.결과처리
			System.out.println(count + " 건이 삭제되었습니다.");
			
		} catch (ClassNotFoundException e) {
		    System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} finally {
			
		    // 5. 자원정리
		    try {
		        //if (rs != null) {
		        //    rs.close();
		        //}                
		        if (pstmt != null) {
		            pstmt.close();
		        }
		        if (conn != null) {
		            conn.close();
		        }
		    } catch (SQLException e) {
		        System.out.println("error:" + e);
		    }
		    
		}
		
	}
	
}

