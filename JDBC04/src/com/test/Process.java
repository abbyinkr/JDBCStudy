
/*==================
   Process.java
==================*/

package com.test;

import java.util.ArrayList;
import java.util.Scanner;

public class Process
{
	// 주요 속성 구성 → 데이터베이스 액션 처리 전담 객체 → ScoreDAO
	// 클래스 객체 상속 개념 이해하고 가기
	private ScoreDAO dao;
	
	// 생성자 
	public Process()
	{
		dao = new ScoreDAO();
	}
	
	// 성적 입력 기능
	public void sungjukInsert()
	{
		
		try
		{
			// 데이터베이스 연결
			dao.connection();
			
			// 레코드 수 확인
			int count = dao.count();
			
			Scanner sc = new Scanner(System.in);
			
			do
			{

				System.out.println();
				System.out.printf("%d번 학생 성적 입력(이름 국어 영어 수학) :", (++count) );
				String name = sc.next();
				
				// 반복의 조건을 무너뜨리는 코드 구성
				if (name.equals("."))
					break;
				
				int kor = sc.nextInt();
				int eng = sc.nextInt();
				int mat = sc.nextInt();
				
				// ScoreDTO 객체 구성
				ScoreDTO dto = new ScoreDTO();
				
				dto.setName(name);
				dto.setKor(kor);
				dto.setEng(eng);
				dto.setMat(mat);
				
				// dao 의 add() 메소드 호출
				int result = dao.add(dto);
				
				if (result > 0)
					System.out.println(">> 성적 입력이 완료되었습니다.");
					
			} while (true);
			
			// 데이터베이스 연결 종료
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
	}//end sungjukInsert()

	// 성적 전체 출력  
	public void sungjukSelectAll()
	{
		// 사전 작성
		/*
		try
		{
			// 데이터베이스 연결
			dao.connection();
			
			// 레코드 수 확인
			int count = dao.count();
			
			ArrayList<ScoreDTO> list;
			list = dao.lists();
			
			System.out.printf("전체 인원 %d명\n", count);
			System.out.println("번호   이름   국어   영어   수학   총점   평균   석차");
			
			for (ScoreDTO obj : list)
			{
				System.out.printf("%3s %4s %5d %6d %6d %6d %6.1f %5d\n"
						          , obj.getSid(), obj.getName(), obj.getKor(), obj.getEng()
						          , obj.getMat(), obj.getTot(), obj.getAvg(), obj.getRank());
			}
			
			
			//데이터베이스 연결 종료
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		} 
		*/
		
		// 선생님 풀이
		
		try
		{
			// dao의 connection() 메소드 호출 → 데이터베이스 연결
			dao.connection();
			
			// dao 의 count() 메소드 호출 → 인원 수 확인
			int count = dao.count();
			
			System.out.println();
			System.out.printf("전체 인원 : %d명\n", count);
			System.out.println("번호   이름   국어   영어   수학   총점   평균   석차");
			
			// 반복문
			for (ScoreDTO dto : dao.lists())
			{
				System.out.printf("%3s %4s %5d %6d %6d %6d %7.1f %5d\n"
								, dto.getSid(), dto.getName(), dto.getKor(), dto.getEng(), dto.getMat()
								, dto.getTot(), dto.getAvg(), dto.getRank());
			}
			// dao 의 close메소드 호출 → 데이터베이스 연결 종료
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		

	}//end sungjukSelectAll()
	
	// 이름 검색 출력
	public void sungjukSearchName()
	{
		/*
		try
		{
			// 데이터베이스 연결
			dao.connection();
			
			// 스캐너 생성
			Scanner sc = new Scanner(System.in);
			
			System.out.print("검색할 이름을 입력하세요 : ");
			String name = sc.next();
			
			
			System.out.println("번호   이름   국어   영어   수학   총점   평균   석차");
			
			// 반복문 
			for (ScoreDTO dto : dao.lists(name))
			{
				System.out.printf("%3s %4s %5d %6d %6d %6d %7.1f %5d\n"
						, dto.getSid(), dto.getName(), dto.getKor(), dto.getEng(), dto.getMat()
						, dto.getTot(), dto.getAvg(), dto.getRank());		
			}
			
			// 데이터베이스 연결 종료
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		} 
		*/
		
		// 선생님 풀이
			
		try
		{
			// 검색할 이름 입력받기
			Scanner sc = new Scanner(System.in);
			
			System.out.print("검색할 이름 입력 : ");
			String name = sc.next();
			
			// -- 필요할 경우 이 과정에서 프로그래밍적으로 검증(검사) 수행
			
			// 데이터베이스 연결
			dao.connection();
			
			// dao 의 lists() 메소드 호출 → 매개변수로 검색할 이름 넘겨주기
			ArrayList<ScoreDTO> arrayList = dao.lists(name);
			
			if (arrayList.size() > 0)
			{
				// 수신된 내용 출력
				System.out.println("번호   이름   국어   영어   수학   총점   평균   석차");
				
				// 반복문 
				for (ScoreDTO dto : arrayList)
				{
					System.out.printf("%3s %4s %5d %6d %6d %6d %7.1f %5d\n"
							, dto.getSid(), dto.getName()
							, dto.getKor(), dto.getEng(), dto.getMat()
							, dto.getTot(), dto.getAvg(), dto.getRank());		
				}
				
			} else
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
		/*
		try
		{
			// 데이터베이스 연결
			dao.connection();
			
			// 번호 입력받기
			Scanner sc = new Scanner(System.in);
			
			System.out.print("검색할 번호 입력 : ");
			int sid = sc.nextInt();
			
			// 대상 찾기
			ArrayList<ScoreDTO> arrayList = dao.lists(sid);
			

			// 수정데이터
			if (arrayList.size() > 0)
			{
				// 수정할 데이터 보여주기
				System.out.println();
				System.out.println("번호   이름   국어   영어   수학   총점   평균   석차");
				
				// 반복문 
				//arrayList.get(0); -- 이걸로도 가져올 수 있나 궁금..
				for (ScoreDTO dto : arrayList)
				{
					System.out.printf("%3s %4s %5d %6d %6d %6d %7.1f %5d\n"
							, dto.getSid(), dto.getName(), dto.getKor(), dto.getEng(), dto.getMat()
							, dto.getTot(), dto.getAvg(), dto.getRank());		
				}
				
				// 수정할 데이터 입력받기 → dto 에 저장
				System.out.print("수정할 내용 입력(이름, 국어, 영어, 수학): ");
				String name = sc.next();
				int kor = sc.nextInt();
				int eng = sc.nextInt();
				int mat = sc.nextInt();
				int tot = kor+eng+mat;
				double avg = tot/3.0;
				
				// dto 인스턴스 생성
				ScoreDTO dto = new ScoreDTO();
				
				// 입력받은 이름, 국어, 영어, 수학점수 저장
				dto.setName(name);
				dto.setKor(kor);
				dto.setEng(eng);
				dto.setTot(tot);
				dto.setAvg(avg);
				
				// 수정 완료
				dao.modify(dto);		
				
			} else
				System.out.println("수정 대상이 존재하지 않습니다.");
			
			// 데이터베이스 연결 종료
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		*/
		
		// 선생님 풀이
		
		try
		{
			// 수정할 번호 입력받기
			Scanner sc = new Scanner(System.in);
			System.out.print("수정할 번호를 입력하세요 : ");
			int sid = sc.nextInt();
			
			//-- 입력받은 번호로 체크해야할 로직 적용 삽입 가능
			
			// 데이터베이스 연결
			dao.connection();
			
			
			// 데이터 수정을 위해 대상 검색 → 수정할 대상 수신
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
				}
				
				System.out.println();
				System.out.print("수정 데이터 입력(이름 국어 영어 수학) : ");
				String name = sc.next();
				int kor = sc.nextInt();
				int eng = sc.nextInt();
				int mat = sc.nextInt();
				
				// dto 구성
				ScoreDTO dto = new ScoreDTO();
				
				dto.setName(name);
				dto.setKor(kor);
				dto.setEng(eng);
				dto.setMat(mat);
				dto.setSid(String.valueOf(sid));
				// 형변환 함수 valueOf()

		
				int result = dao.modify(dto);
				if (result > 0)
				{
					System.out.println(">> 수정이 완료되었습니다.");
				}
				
			} else
			{
				System.out.println(">> 수정 대상이 존재하지 않습니다.");
			}
			
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
		/*
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
				}
				// 삭제
				int result = dao.remove(sid);
				
				// 삭제 완료
				if(result > 0)
					System.out.println(">> 삭제가 완료되었습니다.");
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
		*/
		
		// 선생님 풀이
		
		try
		{
			// 삭제할 번호 입력받기
			Scanner sc = new Scanner(System.in);
			System.out.print("삭제할 번호를 입력하세요 : ");
			int sid = sc.nextInt();
			
			// 데이터베이스 연결
			dao.connection();
			
			// 데이터 삭제를 위해 대상 검색 → dao의 list() 메소드 호출 → 삭제 대상 수신
			ArrayList<ScoreDTO> arrayList = dao.lists(sid);
			
			if (arrayList.size() > 0)
			{
				// 수신된 내용 처리
				System.out.println();
				System.out.println("번호   이름   국어   영어   수학   총점   평균   석차");
				
				for (ScoreDTO dto : arrayList)
				{
					System.out.printf("%3s %4s %5d %6d %6d %6d %7.1f %5d\n"
							, dto.getSid(), dto.getName(), dto.getKor(), dto.getEng(), dto.getMat()
							, dto.getTot(), dto.getAvg(), dto.getRank());		
				}
				System.out.println();
				
				System.out.println(">> 정말 삭제하시겠습니까?(Y/N) : ");
				String response = sc.next();
				
				if (response.equals("Y") || response.equals("y"))
				{
					int result = dao.remove(sid);
					if (result > 0)
					{
						System.out.println(">> 삭제가 완료되었습니다. ");
					}
					
				}
				else
				{
					System.out.println(">> 취소되었습니다.");
				}

			} else
			{
				System.out.println("삭제할 대상이 존재하지 않습니다.");
			}
			
			// 데이터베이스 연결 종료
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
	}// end sungjukDelete()

	
	
}

