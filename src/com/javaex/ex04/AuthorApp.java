package com.javaex.ex04;

import java.util.List;

public class AuthorApp {
	
	public static void main(String[] args) {
		
		AuthorDAO authorDAO = new AuthorDAO();
		
		int c01 = authorDAO.authorInsert("정우성","서울");
		System.out.println("App: "+c01+" 건이 입력되었습니다");
		System.out.println();
		
		int c02 = authorDAO.authorUpdate(33,"강호동","천호동");
		System.out.println("App: "+c02+" 건이 수정되었습니다");
		System.out.println();
		
		int c03 = authorDAO.authorDelete(33);
		System.out.println("App: "+c03+" 건이 삭제되었습니다");
		System.out.println();
		
		List<AuthorVO> aList = authorDAO.authorSelect();
		System.out.println("App: "+aList.size()+" 건이 조회되었습니다");
		System.out.println();
		
	}

}
