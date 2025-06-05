package com.javaex.ex04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {
	//필드
	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/web_db";
	private String id = "web";
	private String pw = "web";
	
	//생성자
	public AuthorDAO() {};
	
	//메소드 gs
	
	//메소드 일반
	
	//DB연결
	private void connect() {
		System.out.println("DB연결");
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

		    // 2. Connection 얻어오기
			this.conn = DriverManager.getConnection(url,id,pw);
			
		} catch (ClassNotFoundException e) {
		    System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		
		}
	}
	
	//자원정리
	private void close() {
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
	
	//입력
	public int authorInsert(String name, String desc) {
		int count = -1;
		
		this.connect();
		
		try {
		    // 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query = """
					insert into author
					values(null, ?, ?)
				    """;
			query = query.stripIndent().strip();
			
			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, desc);
			
			System.out.println(bindQuery(query, name, desc));
		    
			// 실행
			count = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println(count + " 건이 입력되었습니다.");
			System.out.println();

		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		
		this.close();
		
		return count;
	}
	
	//수정
	public int authorUpdate(int authorId, String name, String desc) {
		int count = -1;
		
		this.connect();
		
		try {
		    // 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비 (쿼리 마침표(;) 적지 않는다, 바인딩할 부분은 물음표(?)로 처리)
			String query = "";
			query = """
					update author
					set author_name = ?,
						author_desc = ?
					where author_id = ?
				    """;
			query = query.stripIndent().strip();
			
			// 바인딩
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, name);
			pstmt.setString(2, desc);
			pstmt.setInt(3, authorId);
			
			System.out.println(bindQuery(query, name, desc, authorId));
			
			// 실행
			count = pstmt.executeUpdate();
		    
		    // 4.결과처리
			
			System.out.println(count + " 건이 수정되었습니다.");
			System.out.println();
			
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		
		this.close();
		
		return count;
	}
	
	//삭제
	public int authorDelete(int authorId) {
		int count = -1;
		
		this.connect();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query = """
					delete from author
					where author_id = ?
				    """;
			query = query.stripIndent().strip();
			
			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, authorId);
			
			System.out.println(bindQuery(query, authorId));
		    
			// 실행
			count = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println(count + " 건이 삭제되었습니다.");
			System.out.println();

		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		
		this.close();
		
		return count;
	}
	
	//조회
	//작가리스트 조회
	public List<AuthorVO> authorSelect() {
		
		List<AuthorVO> aList = new ArrayList<>();
		
		this.connect();
		
		try {
			// 3.SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query = """
                    select author_id,
				        author_name,
				        author_desc
				    from author
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
				int authorId = rs.getInt("author_id");
				String authorName = rs.getString("author_name");
				String authorDesc = rs.getString("author_desc");
				
				//자바의 데이터를 VO로 묶는다
				AuthorVO authorVO = new AuthorVO(authorId, authorName, authorDesc);
				
				//VO를 리스트에 추가(add()) 한다
				aList.add(authorVO);
			}
			
			for(int i=0; i<aList.size(); i++) {
				System.out.println(aList.get(i).getAuthorId()+"\t"+
						           aList.get(i).getAuthorName()+"\t"+
						           aList.get(i).getAuthorDesc());
			}
			
			System.out.println(aList.size() + " 건이 조회되었습니다.");
			System.out.println();
			
			System.out.println("향상된for문");
			
			for(AuthorVO baVO : aList) {
				System.out.println(
						baVO.getAuthorId()+"\t"+
						baVO.getAuthorName()+"\t"+
						baVO.getAuthorDesc()
						);
			}
			
			System.out.println(aList.size() + " 건이 조회되었습니다.");
			System.out.println();
			
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		
		this.close();
		
		return aList;
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
