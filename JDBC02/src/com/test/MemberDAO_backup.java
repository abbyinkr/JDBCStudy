/*=================
 MemberDAO.java
=================*/

// Database 에 Access 하는 기능
// → DBConn 활용

// 데이터를 입력하는 기능 → insert

// 인원수를 확인하는 기능
// → 대상 테이블(TBL_MEMBER)의 레코드 카운팅 기능 → select

// 전체 리스트 조회하는 기능
// → 대상테이블(TBL_MEMBER)의 데이터를 조회하는 기능 → select

package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.util.DBConn;

public class MemberDAO_backup
{
	// 주요 변수 선언 → DB 연결 객체 생성
	private Connection conn;

	
	// 생성자 정의
	public MemberDAO_backup() throws ClassNotFoundException, SQLException
	{
		// 실제 DB 서버로 연결
		conn = DBConn.getConnection();
	}
	
	// 메소드 정의 → 데이터를 입력하는 기능 → insert
	public int add(MemberDTO dto) throws SQLException
	{
		
		Scanner sc = new Scanner(System.in);
		int n = 1;
		Statement stmt = conn.createStatement();
		int result;
		
		do
		{
			System.out.printf("이름 전화번호 입력(%d) : ", n);
			//String name = sc.next();
			//String tel = sc.next();
			
			dto.setName(sc.next());
			dto.setTel(sc.next());
			
			
			
			String sql = String.format("INSERT INTO TBL_MEMBER(SID, NAME, TEL)"
				    + " VALUES(MEMBERSEQ.NEXTVAL,'%s', '%s')", dto.getName(), dto.getTel());

			result = stmt.executeUpdate(sql);
			System.out.println(">> 회원정보 입력 완료~!!!");
			n++;
			
			
		} while (sc.next() == ".");

		return result;
		

	}
	
	// 메소드 정의 → 전체 인원수 확인 기능 → select (count)
	public int count() throws SQLException
	{
		
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_MEMBER";
		
		ResultSet rs = stmt.executeQuery(sql);
		
		int su = 0;
		
		if (rs.next())
		{
			su = rs.getInt("COUNT");	
		}

		return su;
		
		
	}
	
	// 메소드 정의 → 전체 리스트 조회 기능 → select 
	public ArrayList<MemberDTO> lists() throws SQLException
	{
		
		ArrayList<MemberDTO> memList = new ArrayList<MemberDTO>();
		MemberDTO dto1 = new MemberDTO();
		
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT SID, NAME, TEL FROM TBL_MEMBER ORDER BY SID";
		
		ResultSet rs = stmt.executeQuery(sql);
		
		dto1.setSid(rs.getString("SID"));
		dto1.setName(rs.getString("NAME"));
		dto1.setTel(rs.getString("TEL"));
		
		memList.add(dto1);

		return memList;
		

	}
	
	
	
	
	
}
