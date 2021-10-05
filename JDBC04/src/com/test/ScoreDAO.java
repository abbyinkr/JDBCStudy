/*==============
 ScoreDAO.java
==============*/

package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.util.DBConn;

public class ScoreDAO
{
	private Connection conn;

	// 데이터베이스 연결 담당 메소드
	public Connection connection() throws ClassNotFoundException, SQLException
	{
		conn = DBConn.getConnection();
		return conn;
	}

	// 데이터 입력 담당 메소드
	public int add(ScoreDTO dto) throws SQLException
	{
		/*
		 int result = 0;
		 
		 // 작업 객체 생성 Statement stmt = conn.createStatement();
		 
		 // 쿼리문 준비 String sql = String.
		 format("INSERT INTO TBL_SCORE(SID, NAME, KOR, ENG, MAT) VALUES(SCORESEQ.NEXTVAL, '%s', %d, %d, %d)"
		 , dto.getName(), dto.getKor(), dto.getEng(), dto.getMat());
		 
		 // 쿼리문 수행 result = stmt.executeUpdate(sql);
		 
		 // 리소스 반납 stmt.close();
		 
		 // 최종 값 반환 return result;
		 */

		int result = 0;

		Statement stmt = conn.createStatement();
		
		String sql = String.format("INSERT INTO TBL_SCORE(SID, NAME, KOR, ENG, MAT)"
				   + " VALUES(SCORESEQ.NEXTVAL, '%s', %d, %d, %d)"
				    , dto.getName(), dto.getKor(), dto.getEng(), dto.getMat());

		result = stmt.executeUpdate(sql);
		stmt.close();
		
		return result;

	}

	// 전체 리스트 출력 담당 메소드
	public ArrayList<ScoreDTO> lists() throws SQLException
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = "SELECT SID, NAME, KOR, ENG, MAT"
				+ ", (KOR+ENG+MAT) AS TOT, (KOR+ENG+MAT)/3 AS AVG "
				+ ", RANK() OVER (ORDER BY (KOR+ENG+MAT) DESC) AS RANK"
				+ " FROM TBL_SCORE"
				+ " ORDER BY SID ASC";
		
		// 쿼리문 수행
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			// ScoreDTO 인스턴스 생성
			ScoreDTO dto = new ScoreDTO();
			
			dto.setSid(rs.getString("SID"));  
			dto.setName(rs.getString("NAME"));  
			dto.setKor(rs.getInt("KOR"));  
			dto.setEng(rs.getInt("ENG"));  
			dto.setMat(rs.getInt("MAT"));  
			dto.setTot(rs.getInt("TOT"));  
			dto.setAvg(rs.getDouble("AVG")); // 평균 → getDouble   
			dto.setRank(rs.getInt("RANK"));
			
			// arraylist result에 저장
			result.add(dto);
		}
		
		// 리소스 반납
		rs.close();
		stmt.close();

		return result;

	}

	// 이름 검색 담당 메소드

	public ArrayList<ScoreDTO> lists(String name) throws SQLException
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		
		String sql = String.format("SELECT * FROM( SELECT SID, NAME, KOR, ENG, MAT, (KOR+ENG+MAT) AS TOT"
					+ ", (KOR+ENG+MAT)/3 AS AVG"
					+ ", RANK() OVER (ORDER BY (KOR+ENG+MAT) DESC) AS RANK"
					+ " FROM TBL_SCORE)"
					+ " WHERE NAME = '%s'", name);
		
		// 쿼리문 수행
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			// ScoreDTO 인스턴스 생성
			ScoreDTO dto = new ScoreDTO();
			
			dto.setSid(rs.getString("SID"));  
			dto.setName(rs.getString("NAME"));  
			dto.setKor(rs.getInt("KOR"));  
			dto.setEng(rs.getInt("ENG"));  
			dto.setMat(rs.getInt("MAT"));  
			dto.setTot(rs.getInt("TOT"));  
			dto.setAvg(rs.getDouble("AVG")); // 평균 → getDouble   
			dto.setRank(rs.getInt("RANK"));
			
			// arraylist result에 저장
			result.add(dto);
		}
		rs.close();
		stmt.close();

		return result;

	}

	// 번호 검색 담당 메소드
	public ArrayList<ScoreDTO> lists(int sid) throws SQLException
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = String.format("SELECT * FROM (SELECT SID, NAME, KOR, ENG, MAT"
				+ ", (KOR+ENG+MAT) AS TOT, (KOR+ENG+MAT)/3 AS AVG"
				+ ", RANK() OVER (ORDER BY (KOR+ENG+MAT) DESC) AS RANK"
				+ " FROM TBL_SCORE)"
				+ " WHERE SID = %d", sid);
		
		// 쿼리문 수행
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			// ScoreDTO 인스턴스 생성
			ScoreDTO dto = new ScoreDTO();
			
			dto.setSid(rs.getString("SID"));  
			dto.setName(rs.getString("NAME"));  
			dto.setKor(rs.getInt("KOR"));  
			dto.setEng(rs.getInt("ENG"));  
			dto.setMat(rs.getInt("MAT"));  
			dto.setTot(rs.getInt("TOT"));  
			dto.setAvg(rs.getDouble("AVG")); // 평균 → getDouble   
			dto.setRank(rs.getInt("RANK"));
			
			
			// arraylist result에 저장
			result.add(dto);
		}
		rs.close();
		stmt.close();

		return result;

	}

	// 인원 수 확인 담당 메소드
	public int count() throws SQLException
	{
		int result = 0;
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 구성
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_SCORE";
		
		// 쿼리문 수행
		ResultSet rs = stmt.executeQuery(sql);

		if (rs.next())
			result = rs.getInt("COUNT");
		
		rs.close();
		stmt.close();
		
		return result;

	}

	// 데이터 수정 담당 메소드
	public int modify(ScoreDTO dto) throws SQLException
	{
		int result = 0;

		// 작업객체 생성
		Statement stmt = conn.createStatement();
		String sql = String.format("UPDATE TBL_SCORE"
				+ " SET NAME = '%s', KOR = %d, ENG = %d, MAT = %d"
				+ " WHERE SID = %s"
				, dto.getName(), dto.getKor(), dto.getEng(), dto.getMat(), dto.getSid());
		
		result =stmt.executeUpdate(sql);
		
		stmt.close();
		
		return result;

	}

	// 데이터 삭제 담당 메소드
	public int remove(int sid) throws SQLException
	{
		int result = 0;
		
		Statement stmt = conn.createStatement();

		String sql = String.format("DELETE FROM TBL_SCORE WHERE SID = %d", sid);
		
		result = stmt.executeUpdate(sql);
		
		stmt.close();

		return result;

	}
	
	// 데이터베이스 연결 종료 담당 메소드
	public void close() throws SQLException
	{
		DBConn.close();
	}

}
