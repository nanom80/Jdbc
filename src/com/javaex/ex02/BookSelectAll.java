package com.javaex.ex02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookSelectAll {

	public static void main(String[] args) {
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BookAuthorVO> baList = new ArrayList<>();
		
		try {
			// 1. JDBC 드라이버 (MySQL) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/web_db";
			conn = DriverManager.getConnection(url,"web","web");
			
			// 3.SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query = """
                    select a.book_id,
				        a.title,
				        a.pubs,
				        date_format(pub_date,'%Y-%m-%d') pub_date,
				        a.author_id,
				        b.author_name,
				        b.author_desc
				    from book a
				        join author b
				            on a.author_id = b.author_id
				    order by a.book_id
				    """;
			query = query.stripIndent().strip();
			
			// 바인딩
			pstmt = conn.prepareStatement(query);
			
			System.out.println(bindQuery(query));
			
			System.out.println();
			
			// 실행
			rs = pstmt.executeQuery();
			
			// 4.결과처리 : 리스트
			while(rs.next()) {
				int bookId = rs.getInt("book_id");
				String title = rs.getString("title");
				String pubs = rs.getString("pubs");
				String pubDate = rs.getString("pub_date");
				int authorId = rs.getInt("author_id");
				String authorName = rs.getString("author_name");
				String authorDesc = rs.getString("author_desc");
				
				System.out.println(bookId+"\t"+title+"\t"+pubs+"\t"+pubDate+"\t"+authorId+"\t"+authorName+"\t"+authorDesc);
				
				//자바의 데이터를 VO로 묶는다
				BookAuthorVO bookAuthorVO = new BookAuthorVO(bookId, title, pubs, pubDate, authorId, authorName, authorDesc);
				
				//VO를 리스트에 추가(add()) 한다
				baList.add(bookAuthorVO);
			}
			
			System.out.println();
			
			for(int i=0; i<baList.size(); i++) {
				System.out.println(baList.get(i).toString());
			}
			
			System.out.println();
			
			for(int i=0; i<baList.size(); i++) {
				System.out.println(baList.get(i).getBookId()+"\t"+
						           baList.get(i).getTitle()+"\t"+
						           baList.get(i).getPubs()+"\t"+
						           baList.get(i).getPubDate()+"\t"+
						           baList.get(i).getAuthorId()+"\t"+
						           baList.get(i).getAuthorName()+"\t"+
						           baList.get(i).getAuthorDesc());
			}
			
			System.out.println();
			
			for(BookAuthorVO baVO : baList) {
				System.out.println(
						baVO.getBookId()+"\t"+
						baVO.getTitle()+"\t"+
						baVO.getPubs()+"\t"+
						baVO.getPubDate()+"\t"+
						baVO.getAuthorId()+"\t"+
						baVO.getAuthorName()+"\t"+
						baVO.getAuthorDesc()
						);
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
