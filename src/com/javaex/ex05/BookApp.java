package com.javaex.ex05;

import java.util.List;

public class BookApp {
	
	public static void main(String[] args) {
		
		BookDAO bookDAO = new BookDAO();
		
		int iCnt = bookDAO.bookInsert("a","a","2025-06-05",1);
		
		System.out.println("App: "+ iCnt +" 건이 입력되었습니다");
		System.out.println();
		
		int uCnt = bookDAO.bookUpdate(7,"26년","재미주의2","2012-02-04",5);
		System.out.println("App: "+ uCnt +" 건이 수정되었습니다");
		System.out.println();
		
		int dCnt = bookDAO.bookDelete(33);
		System.out.println("App: "+ dCnt +" 건이 삭제되었습니다");
		System.out.println();
		
		List<BookVO> bList = bookDAO.bookSelect(36);
		System.out.println("App: "+ bList.size() +" 건이 조회되었습니다");
		System.out.println();
		
		List<BookVO> bAllList = bookDAO.bookSelectAll();
		System.out.println("App: "+ bAllList.size() +" 건이 조회되었습니다");
		System.out.println();
		
	}

}
