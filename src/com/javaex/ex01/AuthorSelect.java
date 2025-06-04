package com.javaex.ex01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
	
public class AuthorSelect {
	
	public static void main(String[] args) {
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AuthorVO> aList = new ArrayList<>();
	
		try {
		    // 1. JDBC 드라이버 (MySQL) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		    // 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/web_db";
			conn = DriverManager.getConnection(url,"web","web"); // DriverManager 메소드는 static
			
		    // 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비 (쿼리 마침표(;) 적지 않는다, 바인딩할 부분은 물음표(?)로 처리)
			String query = "";
			query += " select author_id,\n ";
			query += "     author_name,\n ";
			query += "     author_desc\n ";
			query += " from author\n ";
			// query += " where author_id = ? ";
			
			// 바인딩
			pstmt = conn.prepareStatement(query);
			
			// int author_id = 11;
			// pstmt.setInt(1, author_id);
			
			// System.out.println(bindQuery(query,author_id));
			System.out.println(bindQuery(query));
			
			// 실행
			rs = pstmt.executeQuery();
		    
		    // 4.결과처리 : 리스트
			while(rs.next()) {
				//resultset의 데이타를 자바의 변수에 담는다
				int authorId = rs.getInt("author_id");
				String authorName = rs.getString("author_name");
				String authorDesc = rs.getString("author_desc");
				
				System.out.println(authorId+"\t"+authorName+"\t"+authorDesc);
				
				//자바의 데이터를 VO로 묶는다
				AuthorVO authorVO = new AuthorVO(authorId, authorName, authorDesc);
				
				//VO를 리스트에 추가(add())한다
				aList.add(authorVO);
			}
			
			System.out.println();
			
			for(int i=0; i<aList.size(); i++) {
				System.out.println(aList.get(i).toString());
			}
			
			System.out.println();
			
			for(int i=0; i<aList.size(); i++) {
				System.out.println(aList.get(i).getAuthorId()+"\t"+
				                   aList.get(i).getAuthorName()+"\t"+
						           aList.get(i).getAuthorDesc());
			}
			
		} catch (ClassNotFoundException e) {
		    System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} finally {
			
		    // 5. 자원정리
		    try {
		        if (rs != null) {
		            rs.close();
		        }                
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



