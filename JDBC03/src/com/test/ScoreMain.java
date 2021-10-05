/*=======================
   ScoreMain.java
========================*/

/*
 ○ 성적 처리 프로그램 구현 → 데이터베이스 연동 → ScoreDAO, ScoreDTO 클래스 활용
 
    여러 명의 이름, 국어점수, 영어점수, 수학점수를 입력받아
    총점, 평균을 연산하여 출력하는 프로그램을 구현한다.

실행 예)

1번 학생 성적 입력(이름 국어 영어 수학) : 선혜연 90 80 70
2번 학생 성적 입력(이름 국어 영어 수학) : 이상화 100 90 80
3번 학생 성적 입력(이름 국어 영어 수학) : 이유림 80 85 80
4번 학생 성적 입력(이름 국어 영어 수학) : .

----------------------------------------------------------
번호   이름     국어     영어    수학     총점     평균
----------------------------------------------------------
 1    선혜연    90        80       70     xxx      xx.x
 2    이상화   100        90       80     xxx      xx.x
 3    이유림    80        85       80     xxx      xx.x
----------------------------------------------------------
*/


package com.test;

import java.sql.SQLException;
import java.util.Scanner;

import com.util.DBConn;


public class ScoreMain
{
	public static void main(String[] args) throws SQLException, ClassNotFoundException
	{
		// 스캐너 생성
		Scanner sc = new Scanner(System.in);
		
		// ScoreDAO 인스턴스 생성
		ScoreDAO dao = new ScoreDAO();
		
		// 행의 갯수 
		int count = dao.count();
		// 테스트
		//System.out.println(count);
		// 1
		
		do
		{
			System.out.printf("%d번 학생 성적 입력(이름 국어 영어 수학) :", (++count) );
			String name = sc.next();
			if (name.equals("."))
				// 멈춘다 그리고 빠져나간다.
				break;
			
			int kor = sc.nextInt();
			int eng = sc.nextInt();
			int mat = sc.nextInt();
			
			// 값을 저장할 수 있도록 ScoreDTO 객체 구성(getter, setter 이용)
			ScoreDTO dto = new ScoreDTO();
			dto.setName(name);
			dto.setKor(kor);
			dto.setEng(eng);
			dto.setMat(mat);
			
			// dao의 add 메소드를 통해 DB에 저장
			int result = dao.add(dto);
			
		} while (true);
		
		// 여기까지 했으면 DB에 insert 완료
		
		// 전체 리스트 출력
		System.out.println();
		System.out.println("---------------------------------------------------------------- ");
		System.out.println("번호     이름     국어     영어       수학      총점     평균");
		System.out.println("---------------------------------------------------------------- ");
		
		for (ScoreDTO obj : dao.lists())
		{	
			int tot = obj.getKor()+obj.getEng()+obj.getMat();
			
			double avg = tot/3.0;
			
			System.out.printf(" %3d    %3s    %3d       %3d       %3d       %d      %.1f\n",
					obj.getSid(), obj.getName(), obj.getKor(), obj.getEng(), obj.getMat(), tot, avg);
		}
		System.out.println("---------------------------------------------------------------- ");
		
	
		DBConn.close();
		System.out.println("데이터베이스 연결 닫힘~!!!");
		System.out.println("프로그램 종료됨");

	}

}

// 실행 결과
/*
 * 6번 학생 성적 입력(이름 국어 영어 수학) :한혜림 100 20 30
7번 학생 성적 입력(이름 국어 영어 수학) :.

---------------------------------------------------------------- 
번호   이름     국어     영어    수학     총점     평균
---------------------------------------------------------------- 
   1    김아별     90         80        70     240      80.0
   2    선혜연     90         80        70     240      80.0
   3    이상화    100         90        80     270      90.0
   4    이유림     80         85        80     245      81.7
   5    이하림    100         30        70     200      66.7
   6    한혜림    100         20        30     150      50.0
---------------------------------------------------------------- 
데이터베이스 연결 닫힘~!!!
프로그램 종료됨

*/
