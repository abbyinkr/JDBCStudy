package com.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ScoreProcess
{
	private ScoreDAO dao;
	
	public ScoreProcess() throws ClassNotFoundException, SQLException
	{
		dao = new ScoreDAO();
	}
	
	// 성적 입력기능
	public void sungjukInsert()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			dao.connection();
			int count = dao.count();
			System.out.printf("%d번 학생 성적 입력(이름 국어 영어 수학) : " , (++count));
			String name = sc.next();
			int kor = sc.nextInt();
			int eng = sc.nextInt();
			int mat = sc.nextInt();
			
			ScoreDTO dto = new ScoreDTO();
			
			dto.setName(name);
			dto.setKor(kor);
			dto.setEng(eng);
			dto.setMat(mat);
			
			dao.add(dto);
			
		} catch (Exception e)
		{	
			System.out.println(e.toString());
			e.printStackTrace();
		}
		finally 
		{
			try
			{
				dao.close();
				
			} catch (Exception e2)
			{
				System.out.println(e2.toString());
			
			}
		}
		
	}

	
	// 성적 전체 출력 기능
	public void sungjukLists()
	{
		try
		{
			dao.connection();
			int count = dao.count();
			System.out.println();
			System.out.printf("전체 인원: %d명\n", count);
			System.out.println("번호   이름   국어   영어   수학   총점   평균   석차");
		
			
			ArrayList<ScoreDTO> arrayLists = dao.lists();
			
			for (ScoreDTO dto : arrayLists)
			{
				System.out.printf("%3d %4s %6d %6d %6d %6d %6.1f %5d\n", dto.getSid(), dto.getName()
						          , dto.getKor(), dto.getEng(), dto.getMat()
						          , dto.getTot(), dto.getAvg(), dto.getRank() );
			
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				dao.close();
				
			} catch (Exception e2)
			{
				System.out.println(e2.toString());
			}
			
		}
		
	}

	
	
	// 이름 검색 출력 기능
	public void searchName()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			dao.connection();
			System.out.print("검색할 이름 입력 : ");
			String name = sc.next();
			
			int count = dao.count(name);
			
			if (count>0)
			{

				System.out.println();
				System.out.printf("검색 인원: %d명\n", count);
				System.out.println("번호   이름   국어   영어   수학   총점   평균   석차");
				
				ArrayList<ScoreDTO> arrayList = dao.lists(name);
				
				for (ScoreDTO dto : arrayList)
				{
					System.out.printf("%3d %4s %6d %6d %6d %6d %6.1f %5d\n", dto.getSid(), dto.getName()
				          , dto.getKor(), dto.getEng(), dto.getMat()
				          , dto.getTot(), dto.getAvg(), dto.getRank() );
				
				}
			}
			else
				System.out.println(">> 검색 결과가 없습니다.");
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				dao.close();
				
			} catch (Exception e2)
			{
				System.out.println(e2.toString());
			}
			
		}
		
	}
	
	
	// 성적 수정 기능
	public void sungjukUpdate()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			dao.connection();
			System.out.print("수정할 학생 번호 입력 : " );
			int sid = sc.nextInt();
			// 기존 정보 보여주기
			ArrayList<ScoreDTO> arrayList = dao.lists(sid);
			
			if (arrayList.size()>0)
			{

				System.out.println("조회된 학생 성적 정보: ");
				System.out.println("번호   이름   국어   영어   수학   총점   평균   석차");
				for (ScoreDTO dto : arrayList)
				{
					System.out.printf("%3d %4s %6d %6d %6d %6d %6.1f %5d\n", dto.getSid(), dto.getName()
				          , dto.getKor(), dto.getEng(), dto.getMat()
				          , dto.getTot(), dto.getAvg(), dto.getRank() );
				}
				
				System.out.print("정말 수정하시겠습니까?(Y/N) : ");
				String answer = sc.next();
				
				if (answer.equals("Y") || answer.equals("y"))
				{

					System.out.print("수정할 이름 국어, 영어, 수학 점수 입력 : ");
					String name = sc.next();
					int kor = sc.nextInt();
					int eng = sc.nextInt();
					int mat = sc.nextInt();
					
					ScoreDTO dto = new ScoreDTO();
					dto.setSid(sid);
					dto.setName(name);
					dto.setKor(kor);
					dto.setEng(eng);
					dto.setMat(mat);
					
					dao.modify(dto);
				}
				else
					System.out.println(">> 취소되었습니다.");
				
			}
			else
				System.out.println(">> 수정할 데이터가 없습니다.");
			
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally 
		{
			try
			{
				dao.close();
				
			} catch (Exception e2)
			{
				System.out.println(e2.toString());
			}
		}
		
		
	}
	
	// 성적 삭제 기능
	public void sungjukRemove()
	{
		try
		{
			dao.connection();
			Scanner sc = new Scanner(System.in);
			
			System.out.print("삭제할 학생 번호 입력 : ");
			int sid = sc.nextInt();
			
			// 기존 정보 보여주기
			ArrayList<ScoreDTO> arrayList = dao.lists(sid);
			
			if (arrayList.size()>0)
			{

				System.out.println("조회된 학생 성적 정보: ");
				System.out.println("번호   이름   국어   영어   수학   총점   평균   석차");
				for (ScoreDTO dto : arrayList)
				{
					System.out.printf("%3d %4s %6d %6d %6d %6d %6.1f %5d\n", dto.getSid(), dto.getName()
				          , dto.getKor(), dto.getEng(), dto.getMat()
				          , dto.getTot(), dto.getAvg(), dto.getRank() );
				}
				
				System.out.print("정말 삭제하시겠습니까?(Y/N) : ");
				String answer = sc.next();
				
				if (answer.equals("Y") || answer.equals("y"))
				{
					dao.remove(sid);
				} else
				{
					System.out.println(">> 삭제가 취소되었습니다.");
				}
				
			}
			else 
				System.out.println(">> 삭제할 데이터가 없습니다.");
			
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
	}
	
	
}

