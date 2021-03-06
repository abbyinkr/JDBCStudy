
/*==================
   Process.java
==================*/

package com.test;

import java.util.ArrayList;
import java.util.Scanner;

public class Process_backup
{
	// 주요 속성 구성 → 데이터베이스 액션 처리 전담 객체 → ScoreDAO
	// DTO → DAO → Process 로 이어지는 개념 확인!
	private ScoreDAO dao;
	
	// dao의 메소드를 편하게 쓰기위해 생성자로 dao 인스턴스 생성
	public Process_backup()
	{
		dao = new ScoreDAO();
	}
	

	// 성적 전체 출력  
	public void sungjukSelectAll()
	{
		try
		{
			//ArrayList<ScoreDTO> arrayList =new ArrayList<ScoreDTO>();
			
			// 데이터베이스 연결
			dao.connection();
			
			System.out.println();
			System.out.printf("전체 인원 : %d명\n", dao.count());
			System.out.println("번호   이름   국어   영어   수학   총점   평균   석차");
			
			for (ScoreDTO dto : dao.lists())
			{
				System.out.printf("%5s %5s %5d %5d %5d %5d %5.1f %5d\n"
						, dto.getSid(), dto.getName(), dto.getKor(), dto.getEng(), dto.getMat()
						, dto.getTot(), dto.getAvg(), dto.getRank());
			}
			System.out.println();
			
			dao.close();
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		

	}//end sungjukSelectAll()
	
	
	// 성적 입력 기능
	public void sungjukInsert()
	{
		
		try
		{
			// 데이터베이스 연결
			dao.connection();
			
			
			//레코드 수 확인
			int count = dao.count();
			
			Scanner sc = new Scanner(System.in);
			
			
			do
			{
				
				
				System.out.println();
				System.out.printf("%번 학생 성적 입력(이름 국어 영어 수학) : ",(++count) );
				String name = sc.next();
				if(name == ".")
					break;
				int kor = sc.nextInt();
				int eng = sc.nextInt();
				int mat = sc.nextInt();
				
				//ScoreDTO 객체 생성 및 속성(변수) 구성
				ScoreDTO dto = new ScoreDTO();
				
				dto.setName(name);
				dto.setKor(kor);
				dto.setEng(eng);
				dto.setMat(mat);
				
				// dao의 add() 메소드 호출
				int result = dao.add(dto);
				
				if (result>0)
				{
					System.out.println(">> 성적 입력이 완료되었습니다.");
				}
				
				
			} while (true);
			
			dao.close();
					
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
	}//end sungjukInsert()

	
	// 이름 검색 출력
	public void sungjukSearchName()
	{
		
		try
		{
			// 스캐너 생성
			Scanner sc = new Scanner(System.in);
			
			System.out.print("검색할 이름 입력 : ");
			String name = sc.next();
			
			// 데이터베이스 연결
			dao.connection();
			
			// dao의 lists() 메소드 호출 → 매개변수로 검색할 이름 넘겨주기
			ArrayList<ScoreDTO> arrayList = dao.lists(name);
			
			if (arrayList.size()>0)
			{

				System.out.println("번호   이름   국어   영어   수학   총점   평균   석차");
				
				// 반복문 
				for (ScoreDTO dto : arrayList)
				{
					System.out.printf("%3s %4s %5d %6d %6d %6d %7.1f %5d\n"
							, dto.getSid(), dto.getName(), dto.getKor(), dto.getEng(), dto.getMat()
							, dto.getTot(), dto.getAvg(), dto.getRank());		
				}
			}
			else
			{
				System.out.println(">> 검색 결과가 존재하지 않습니다.");
			}
			
			// 데이터베이스 연결 종료
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		} 
		
	}// end sungjukSearchName()
	
	
	// 성적 수정        
	public void sungjukUpdate()
	{
		
		try
		{
			
			// 번호 입력받기
			Scanner sc = new Scanner(System.in);
			
			System.out.print("수정할 번호 입력 : ");
			int sid = sc.nextInt();
			
			// 데이터베이스 연결
			dao.connection();
			
			// 대상 찾기
			ArrayList<ScoreDTO> arrayList = dao.lists(sid);

			

			// 수정데이터
			if (arrayList.size() > 0)
			{
				// 수정할 데이터 보여주기
				System.out.println();
				System.out.println("번호   이름   국어   영어   수학   총점   평균   석차");
				
				// 반복문 
				//arrayList.get(0); -- 이걸로도 가져올 수 있음
				for (ScoreDTO dto : arrayList)
				{
					System.out.printf("%3s %4s %5d %6d %6d %6d %7.1f %5d\n"
							, dto.getSid(), dto.getName(), dto.getKor(), dto.getEng(), dto.getMat()
							, dto.getTot(), dto.getAvg(), dto.getRank());		
				}
				
				// 수정할 데이터 입력받기 → dto 에 저장
				System.out.println();
				System.out.print("수정 데이터입력 입력(이름, 국어, 영어, 수학): ");
				String name = sc.next();
				int kor = sc.nextInt();
				int eng = sc.nextInt();
				int mat = sc.nextInt();
				//int tot = kor+eng+mat;
				//double avg = tot/3.0;
				
				// dto 인스턴스 생성
				ScoreDTO dto = new ScoreDTO();
				
				// 입력받은 이름, 국어, 영어, 수학점수 저장
				// PK : sid 저장
				dto.setName(name);
				dto.setKor(kor);
				dto.setEng(eng);
				dto.setSid(String.valueOf(sid));
				// 형변환함수 valueOf()
				
				// 수정 완료
				int result = dao.modify(dto);
				if (result > 0)
				{
					System.out.println(">> 수정이 완료되었습니다.");
				}
				
			} else
				System.out.println(">>수정 대상이 존재하지 않습니다.");
			
			// 데이터베이스 연결 종료
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		
	}//end sungjukUpdate()
	
	// 성적 삭제      
	public void sungjukDelete()
	{
		
		try
		{
			// 삭제할 sid 번호 입력받기
			Scanner sc = new Scanner(System.in);
			System.out.print("삭제할 번호를 입력하세요 : ");
			int sid = sc.nextInt();
			
			// 데이터베이스 연결
			dao.connection();
			
			// 삭제할 데이터 출력
			ArrayList<ScoreDTO> arrayList = dao.lists(sid);
			
			if (arrayList.size() > 0)
			{
				// 수신된 내용 출력
				System.out.println();
				System.out.println("번호   이름   국어   영어   수학   총점   평균   석차");
				
				for (ScoreDTO dto : arrayList)
				{
					System.out.printf("%3s %4s %5d %6d %6d %6d %7.1f %5d\n"
							, dto.getSid(), dto.getName(), dto.getKor(), dto.getEng(), dto.getMat()
							, dto.getTot(), dto.getAvg(), dto.getRank());	
					
					System.out.println();
					
					System.out.println(">> 정말 삭제하시겠습니까?(Y/N) : ");
					String response = sc.next();
					
					if (response.equals("Y") || response.equals("y") )
					{
						int result = dao.remove(sid);
						
						// 삭제 완료
						if(result > 0)
							System.out.println(">> 삭제가 완료되었습니다.");
					}
					else
						System.out.println(">> 취소되었습니다.");
				}
				// 삭제
				
			}
			else
			{
				System.out.println(">> 삭제할 대상이 존재하지 않습니다.");
			}
			
		// 데이터베이스 종료
		dao.close();

		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		
		
	}// end sungjukDelete()

	
	
}

