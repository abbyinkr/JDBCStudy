/*==================
   ScoreMain.java
===================*/

/*
○ 성적 처리 → 데이터베이스 연동(데이터베이스 연결 및 액션 처리)
		     → ScoreDTO 클래스 활용 (속성만 존재하는 클래스, getter/setter 구성)
		     → ScoreDAO 클래스 활용 (데이터베이스 액션 처리)
		     → Process 클래스 활용(단위 기능 구성) - 메인메뉴, 서브메뉴

여러 명의 이름, 국어점수, 영어점수, 수학점수를 입력받아
총점, 평균을 연산하여 출력하는 프로그램을 구현한다.
※ 서브 메뉴 구성 → Process 클래스 활용.

실행 예)

=====[ 성적 처리 ]=====
1. 성적 입력
2. 성적 전체 출력
3. 이름 검색 출력
4. 성적 수정
5. 성적 삭제
=======================
>> 선택(1~5, -1종료) : 1

7번 학생 성적 입력(이름 국어 영어 수학) : 안정미 50 60 70
>> 성적 입력이 완료되었습니다.

8번 학생 성적 입력(이름 국어 영어 수학) : 이새롬 80 70 60
>> 성적 입력이 완료되었습니다.

9번 학생 성적 입력(이름 국어 영어 수학) : .

=====[ 성적 처리 ]=====
1. 성적 입력
2. 성적 전체 출력
3. 이름 검색 출력
4. 성적 수정
5. 성적 삭제
=======================
>> 선택(1~5, -1종료) : 2

전체 인원 8명
번호   이름   국어   영어   수학   총점   평균   석차
 1
 2
 3
 4
 5                       ...
 6
 7
 8
 
 =====[ 성적 처리 ]=======
1. 성적 입력
2. 성적 전체 출력
3. 이름 검색 출력
4. 성적 수정
5. 성적 삭제
=======================
>> 선택(1~5, -1종료) : -1

프로그램이 종료되었습니다.

※ 위 내용(JDBC04 프로젝트에서 수행했던 내용)을
   PreparedStatement 작업 객체를 활용하여 구성할 수 있도록 한다.

 */
package com.test;

import java.util.Scanner;

public class ScoreMain
{
	private static ScoreProcess prc;
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			prc = new ScoreProcess();
			
			do
			{
				System.out.println();
				System.out.println("=====[ 성적 처리 ]=====");
				System.out.println("1. 성적 입력");
				System.out.println("2. 성적 전체 출력");
				System.out.println("3. 이름 검색 출력");
				System.out.println("4. 성적 수정");
				System.out.println("5. 성적 삭제");
				System.out.println("=======================");
				System.out.print(">> 선택(1~5, -1종료) : ");
				
				String menuStr = sc.next();
				
				if(menuStr.equals("-1"))
				{
						System.out.println("데이터베이스 연결 닫힘~!!");
						System.out.println("프로그램 종료~!!!");
						break;
				}
				int menu = Integer.parseInt(menuStr);
				
				switch (menu)
				{
					case 1:
						// 성적 입력 기능
						prc.sungjukInsert();
						break;
					case 2:
						// 전체 학생 성적 출력 기능
						prc.sungjukLists();
						break;
					case 3:
						// 이름 검색 성적 출력 기능
						prc.searchName();
						break;
					case 4:
						// 성적 수정 기능
						prc.sungjukUpdate();
						break;
					case 5:
						// 성적 삭제 기능
						prc.sungjukRemove();
						break;
				}
			} while (true);
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}


	}

}
