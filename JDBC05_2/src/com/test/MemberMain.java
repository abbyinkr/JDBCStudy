package com.test;

import java.sql.SQLException;
import java.util.Scanner;

public class MemberMain
{
	public static void main(String[] args) throws SQLException
	{
		// add() 메소드에 대한 단위 테스트 개념
		/* 
		// 테스트 데이터 입력
		MemberDAO dao = new MemberDAO();
		
		dao.connection();
		
		MemberDTO dto = new MemberDTO();
		
		dto.setEmpName("김가영");
		dto.setSsn("940225-2234567");
		dto.setIbsaDate("2020-01-16");
		dto.setCityLoc("서울");
		dto.setTel("010-9999-8888");
		dto.setBuseoName("개발부");
		dto.setJikwiName("대리");
		dto.setBasicPay(2000000);
		dto.setSudang(1000000);
		
		
		int result = dao.add(dto);
		System.out.println(result);
		*/
		
		Scanner sc = new Scanner(System.in);
		MemberProcess prc = new MemberProcess();
		
		do
		{
			System.out.println();
			System.out.println("=======[ 직원 관리 ]=======");
			System.out.println("1. 직원 정보 입력");
			System.out.println("2. 직원 전체 출력");
			System.out.println("   - 사번 정렬");
			System.out.println("   - 이름 정렬");
			System.out.println("   - 부서 정렬");
			System.out.println("   - 직위 정렬");
			System.out.println("   - 급여 내림차순 정렬");
			System.out.println("3. 직원 검색 출력");
			System.out.println("   - 사번 검색");
			System.out.println("   - 이름 검색");
			System.out.println("   - 부서 검색");
			System.out.println("   - 직위 검색");
			System.out.println("4. 직원 정보 수정");
			System.out.println("5. 직원 정보 삭제");
			System.out.println("===========================");
			System.out.print(">> 메뉴 선택(1~5, -1종료) :");
			
			String menuStr = sc.next();
			
			try
			{
				int menu = Integer.parseInt(menuStr);
				
				if (menu==-1)
					break;
				
				switch (menu)
				{
					case 1:
						// 직원 정보 입력 메소드 호출
						prc.memberInsert();
						break;
					case 2:
						// 직원 전체 출력 메소드 호출
						prc.memberLists();
						break;
					case 3:
						// 직원 검색 출력 메소드 호출
						prc.memberSearch();
						break;
					case 4:
						// 직원 정보 수정 메소드 호출
						prc.memberUpdate();
						break;
					case 5:
						// 직원 정보 삭제 메소드 호출
						prc.memberDelete();
						break;
						

				}
				
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
			
		} while (true);
		
		System.out.println();
		System.out.println("데이터베이스 연결 닫힘~!!!");
		System.out.println("프로그램 종료됨~!!!");
		
	}
}



/*
EMP_ID   NOT NULL NUMBER(5)    
EMP_NAME          VARCHAR2(20) 
SSN               CHAR(14)     
IBSADATE          DATE         
CITY_ID           NUMBER(5)    
TEL               VARCHAR2(15) 
BUSEO_ID          NUMBER(5)    
JIKWI_ID          NUMBER(5)    
BASICPAY          NUMBER(10)   
SUDANG            NUMBER(10)   

 */