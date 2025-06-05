package com.javaex.ex05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
	
	//필드
	//0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/web_db";
	private String id = "web";
	private String pw = "web";
	
	//생성자
	public BookDAO() {};
	
	//메소드 gs
	
	
	//메소드 일반
	
	//DB연결
	private void connect() {
		System.out.println("DB연결");
		try {
			//1.JDBC 드라이버 (MySQL) 로딩
			Class.forName(driver);
			
			//2.Connection 얻어오기
			this.conn = DriverManager.getConnection(url,id,pw);
			
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	//자원정리
	private void close() {
		//5.자원정리
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
		System.out.println("자원정리");
	}
	
	//입력
	public int bookInsert(String title, String pubs, String pubDate, int authorId) {
		
		int count = -1;
		
		this.connect();
		
		try {
			//3.SQL문 준비 / 바인딩 / 실행
			//SQL문 준비
			String query = "";
			query = """
					insert into book
					values(null,?,?,?,?);
					""";
			query = query.stripIndent().strip();
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, pubs);
			pstmt.setString(3, pubDate);
			pstmt.setInt(4, authorId);
			
			System.out.println(bindQuery(query,title,pubs,pubDate,authorId));
			
			//실행
			count = pstmt.executeUpdate();
			
			//4.결과처리
			System.out.println(count + " 건이 입력되었습니다.");
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		this.close();
		
		return count;
	}
	
	//수정
	public int bookUpdate(int bookId, String title, String pubs, String pubDate, int authorId) {
		
		int count = -1;
		
		this.connect();
		
		try {
			//3.SQL문 준비 / 바인딩 / 실행
			//SQL문 준비
			String query = "";
			query = """
					update book
					set title = ?,
					    pubs = ?,
					    pub_date = ?,
					    author_id = ?
					where book_id = ?
					""";
			query = query.stripIndent().strip();
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, pubs);
			pstmt.setString(3, pubDate);
			pstmt.setInt(4, authorId);
			pstmt.setInt(5, bookId);
			
			System.out.println(bindQuery(query,title,pubs,pubDate,authorId));
			
			//실행
			count = pstmt.executeUpdate();
			
			//4.결과처리
			System.out.println(count + " 건이 수정되었습니다.");
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		this.close();
		
		return count;
	}
	
	//삭제
	public int bookDelete(int bookId) {
		
		int count = -1;
		
		this.connect();
		
		try {
			//3.SQL문 준비 / 바인딩 / 실행
			//SQL문 준비
			String query = "";
			query = """
					delete from book
					where book_id = ?
					""";
			query = query.stripIndent().strip();
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bookId);
			
			System.out.println(bindQuery(query, bookId));
			
			//실행
			count = pstmt.executeUpdate();
			
			//4.결과처리
			System.out.println(count + " 건이 삭제되었습니다.");
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		this.close();
		
		return count;
	}
	
	//단건조회
	public List<BookVO> bookSelect(int bookId) {
		
		List<BookVO> bList = new ArrayList<>();
		
		this.connect();
		
		try {
			//3.SQL문 준비 / 바인딩 / 실행
			//SQL문 준비
			String query = "";
			query = """
					select book_id,
					    title,
					    pubs,
					    pub_date,
					    author_id
					from book
					where book_id = ?
					""";
			query = query.stripIndent().strip();
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bookId);
			
			System.out.println(bindQuery(query));
			
			//실행
			rs = pstmt.executeQuery();
			
			//4.결과처리: 리스트
			while(rs.next()) {
				int bId = rs.getInt("book_id");
				String title = rs.getString("title");
				String pubs = rs.getString("pubs");
				String pubDate = rs.getString("pub_date");
				int authorId = rs.getInt("author_id");
				
				//자바의 데이터를 VO로 묶는다
				BookVO bookVO = new BookVO(bId, title, pubs, pubDate, authorId);
				
				//VO를 리스트에 추가(add())한다
				bList.add(bookVO);
			}
			
			for(int i=0; i<bList.size(); i++) {
				System.out.println(
						bList.get(i).getBookId()+"\t"+
						bList.get(i).getTitle()+"\t"+
						bList.get(i).getPubs()+"\t"+
						bList.get(i).getPubDate()+"\t"+
						bList.get(i).getAuthorId()
						);
			}
			System.out.println(bList.size() + " 건이 조회되었습니다.");
			System.out.println();
			
			System.out.println("향상된 for문");
			for(BookVO bVO : bList) {
				System.out.println(
						bVO.getBookId()+"\t"+
						bVO.getTitle()+"\t"+
						bVO.getPubs()+"\t"+
						bVO.getPubDate()+"\t"+
						bVO.getAuthorId()
						);
			}
			System.out.println(bList.size() + " 건이 조회되었습니다.");
			System.out.println();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		this.close();
		
		return bList;
	}
	
	//전체조회
	public List<BookVO> bookSelectAll() {
		
		List<BookVO> bList = new ArrayList<>();
		
		this.connect();
		
		try {
			//3.SQL문 준비 / 바인딩 / 실행
			//SQL문 준비
			String query = "";
			query = """
					select book_id,
					    title,
					    pubs,
					    pub_date,
					    author_id
					from book
					""";
			query = query.stripIndent().strip();
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			
			System.out.println(bindQuery(query));
			
			System.out.println();
			
			//실행
			rs = pstmt.executeQuery();
			
			//4.결과처리: 리스트
			while(rs.next()) {
				int bookId = rs.getInt("book_id");
				String title = rs.getString("title");
				String pubs = rs.getString("pubs");
				String pubDate = rs.getString("pub_date");
				int authorId = rs.getInt("author_id");
				
				//데이터를 VO로 묶는다
				BookVO bookVO = new BookVO(bookId,title,pubs,pubDate,authorId);
				
				//VO를 리스트에 추가(add())한다
				bList.add(bookVO);
			}
			
			for(int i=0; i<bList.size(); i++) {
				System.out.println(
						bList.get(i).getBookId()+"\t"+
						bList.get(i).getTitle()+"\t"+
						bList.get(i).getPubs()+"\t"+
						bList.get(i).getPubDate()+"\t"+
						bList.get(i).getAuthorId()
						);
			}
			
			System.out.println(bList.size() + " 건이 조회되었습니다.");
			System.out.println();
			
			System.out.println("향상된 for문");
			
			for(BookVO bVO : bList) {
				System.out.println(
						bVO.getBookId()+"\t"+
						bVO.getTitle()+"\t"+
						bVO.getPubs()+"\t"+
						bVO.getPubDate()+"\t"+
						bVO.getAuthorId()
						);
			}
			
			System.out.println(bList.size() + " 건이 조회되었습니다.");
			System.out.println();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		this.close();
		
		return bList;
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





