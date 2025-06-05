package com.javaex.ex03;

public class AuthorApp {
	
	public static void main(String[] args) {
		
		AuthorDAO authorDao = new AuthorDAO();
		
		int count = authorDao.authorInsert("황일영","하이미디어");
		System.out.println("App: "+count+" 건이 입력되었습니다");
		System.out.println();
		
		count = authorDao.authorUpdate(16,"황일영","성내동");
		System.out.println("App: "+count+" 건이 수정되었습니다");
		System.out.println();
		
		count = authorDao.authorDelete(13);
		System.out.println("App: "+count+" 건이 삭제되었습니다");
		System.out.println();
		
		count = authorDao.authorList();
		System.out.println("App: "+count+" 건이 조회되었습니다");
		System.out.println();
		
	}
	
}
