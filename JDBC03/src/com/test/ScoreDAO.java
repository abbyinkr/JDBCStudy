package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.util.DBConn;

public class ScoreDAO
{
	// DB 연결 객체 선언
	private Connection conn;
	
	// 생성자 생성 (연결 객체 생성)
	public ScoreDAO() throws ClassNotFoundException, SQLException
	{
		// 연결
		conn = DBConn.getConnection();
	}

	// 입력 메소드 → insert
	public int add(ScoreDTO dto) throws SQLException
	{
		int result = 0;
		
		// 작업 객체 생성
		
		Statement stmt = conn.createStatement();
		
		// 쿼리문 구성
		String sql = String.format("INSERT INTO TBL_SCORE(SID, NAME, KOR, ENG, MAT) VALUES (SCORESEQ.NEXTVAL, '%s', %d, %d, %d)"
				     , dto.getName(), dto.getKor(), dto.getEng(), dto.getMat());
		
		// 쿼리문 실행
		result = stmt.executeUpdate(sql);
		
		// 리소스 반납
		stmt.close();
		
		// 최종값 반환
		return result;
		
	}
	
	// 전체 인원수 확인 메소드 → select count(*) as 별칭
	public int count() throws SQLException
	{
		int result = 0;
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 구성
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_SCORE";
		
		// 쿼리문 실행
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{
			result = rs.getInt(1);
		}
		
		// 리소스 반납
		rs.close();
		stmt.close();
		
		// 최종값 반환
		return result;
		
	}
	
	
	// 전체 리스트 조회 메소드 → select 
	public ArrayList<ScoreDTO> lists() throws SQLException
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = "SELECT SID, NAME, KOR, ENG, MAT FROM TBL_SCORE ORDER BY SID";
		
		// 쿼리문 실행
		ResultSet rs = stmt.executeQuery(sql);
		
		
		while (rs.next())
		{
			// ScoreDTO 인스턴스 생성
			ScoreDTO dto = new ScoreDTO();
			dto.setSid(rs.getInt("SID"));
			dto.setName(rs.getString("NAME"));
			dto.setKor(rs.getInt("KOR"));
			dto.setEng(rs.getInt("ENG"));
			dto.setMat(rs.getInt("MAT"));	
			
			// ArrayList에 저장
			result.add(dto);
		}
		// 리소스 반납
		rs.close();
		stmt.close();
		
		// 최종값 반환
		return result;
		
		
	}

}
