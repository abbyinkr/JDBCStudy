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

public class ScoreDAO_backup
{ 
	// 연결 객체 생성(private → 정보 은닉)
	private Connection conn; 
	
	// 데이터베이스 연결 담당 메소드
	public Connection connection() throws ClassNotFoundException, SQLException
	{
		// DB연결
		conn = DBConn.getConnection();
		return conn;
		
	}
	
	// 데이터베이스 종료 담당 메소드
	public void close() throws SQLException
	{
		DBConn.close();
	}
	
	// 데이터베이스 입력 담당 메소드
	public int add(ScoreDTO dto) throws SQLException
	{
		int result = 0;
		
		// 작업객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비, 입력이니까 동적
		String sql = String.format("INSERT INTO TBL_SCORE(SID, NAME, KOR, ENG, MAT)"
				+ " VALUES(SCORESEQ.NEXTVAL, '%s', %d, %d, %d)"
				, dto.getName(), dto.getKor(), dto.getEng(), dto.getMat());
		
		// 쿼리문 수행
		result = stmt.executeUpdate(sql);
		
		if(result>0)
			System.out.println("데이터 입력이 완료되었습니다.");
		
		return result;
		
	}
	
	// 전체리스트 출력 담당 메소드
	public ArrayList<ScoreDTO> lists() throws SQLException
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT SID, NAME, KOR, ENG, MAT, (KOR+ENG+MAT) AS TOT"
				+ ", (KOR+ENG+MAT)/3 AS AVG"
				+ ", RANK() OVER (ORDER BY (KOR+ENG+MAT) DESC) AS RANK"
				+ " FROM TBL_SCORE"
				+ " ORDER BY SID ASC";
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
			dto.setAvg(rs.getDouble("AVG")); // 평균은 실수
			dto.setRank(rs.getInt("RANK"));
			
			result.add(dto);
		}
		rs.close();
		stmt.close();
		
		return result;
		
	}
	
	// 이름 검색 담당 메소드 → 출력 , lists로 메소드 오버로딩
	public ArrayList<ScoreDTO> lists(String name) throws SQLException
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		
		Statement stmt = conn.createStatement();
		
		String sql = String.format("SELECT * FROM( SELECT SID, NAME, KOR, ENG, MAT"
				+ ", (KOR+ENG+MAT) AS TOT, (KOR+ENG+MAT)/3 AS AVG"
				+ ", RANK() OVER (ORDER BY (KOR+ENG+MAT) DESC) AS RANK"
				+ " FROM TBL_SCORE)"
				+ " WHERE NAME = '%s'"
				, name);
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
			dto.setAvg(rs.getDouble("AVG")); // 평균은 실수
			dto.setRank(rs.getInt("RANK"));
			
			result.add(dto);
		}
		
		
		return result;
		
	}
	
	//번호 검색 담당 메소드 → 출력, lists로 메소드 오버로딩
	public ArrayList<ScoreDTO> lists(int sid) throws SQLException
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		
		Statement stmt = conn.createStatement();
		
		String sql = String.format("SELECT * FROM( SELECT SID, NAME, KOR, ENG, MAT"
				+ ", (KOR+ENG+MAT) AS TOT, (KOR+ENG+MAT)/3 AS AVG"
				+ ", RANK() OVER (ORDER BY (KOR+ENG+MAT) DESC) AS RANK"
				+ " FROM TBL_SCORE)"
				+ " WHERE SID = %d"
				, sid);
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
			dto.setAvg(rs.getDouble("AVG")); // 평균은 실수
			dto.setRank(rs.getInt("RANK"));
			
			result.add(dto);
		}
		
		
		
		return result;
	}
	
	// 인원수 확인 담당 메소드
	public int count() throws SQLException
	{
		int result=0;
		
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_SCORE";
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next())
			result = rs.getInt("COUNT");
		rs.close();
		stmt.close();
		
		return result;
	}
	
	//데이터 수정 담당 메소드
	public int modify(ScoreDTO dto) throws SQLException
	{
		int result=0;
		Statement stmt = conn.createStatement();
		
		String sql = String.format("UPDATE TBL_SCORE SET NAME = '%s'"
					  + ", KOR = %d, ENG = %d, MAT = %d"
					  + " WHERE SID = %s" 
					  , dto.getName(), dto.getKor(), dto.getEng(), dto.getMat(), dto.getSid());
		
		result = stmt.executeUpdate(sql);
		if (result>0)
			System.out.println("데이터 수정이 완료되었습니다.");
		stmt.close();
		
		return result;
	}
	
	//데이터 삭제 담당 메소드
	public int remove(int sid) throws SQLException
	{
		int result=0;
		Statement stmt = conn.createStatement();
		String sql = String.format("DELETE FROM TBL_SCORE WHERE SID = %d", sid);
		result = stmt.executeUpdate(sql);
		if (result>0)
			System.out.println("데이터 삭제가 완료되었습니다.");
		stmt.close();
		
		return result;
		
	}
	
	
	
}
