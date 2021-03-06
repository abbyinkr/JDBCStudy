package com.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.util.DBConn;

public class ScoreDAO
{
	private Connection conn;
	
	public Connection connection() throws ClassNotFoundException, SQLException
	{
		conn = DBConn.getConnection();
		return conn;
	}
	
	// 데이터베이스 연결 종료 메소드
	public void close() throws SQLException
	{
		DBConn.close();
		
	}
	
	// 전체 인원수 조회 메소드
	public int count()
	{
		int result=0;
		try
		{
			String sql = "SELECT COUNT(*) AS COUNT FROM TBL_SCORE";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next())
				result = rs.getInt("COUNT");
			
			rs.close();
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}

		return result;
		
	}
	
// 이름 검색 인원수 조회 메소드
	public int count(String name)
	{
		int result=0;
		try
		{
			String sql = "SELECT COUNT(*) AS COUNT FROM TBL_SCORE WHERE NAME = ?";
			
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, name);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next())
				result = rs.getInt("COUNT");
			
			rs.close();
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}

		return result;
		
	}
	
	// 성적 데이터 입력 메소드
	public int add(ScoreDTO dto)
	{
		int result=0;

		String sql = "INSERT INTO TBL_SCORE(SID, NAME, KOR, ENG, MAT)"
				+ " VALUES(SCORESEQ.NEXTVAL, ?, ?, ?, ?)";
		
		try
		{
			//PreparedStatement 작업객체 생성과 동시에 쿼리문 전달
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getKor());
			pstmt.setInt(3, dto.getEng());
			pstmt.setInt(4, dto.getMat());
			
			result = pstmt.executeUpdate();
			if (result>0)
				System.out.println(">> 데이터 입력 완료~!!");
			
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		
		return result;
		
	}

	// 성적 데이터 전체 출력
	public ArrayList<ScoreDTO> lists()
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		
		try
		{
			String sql = "SELECT SID, NAME, KOR, ENG, MAT"
					+ ", (KOR+ENG+MAT) AS TOT"
					+ ", (KOR+ENG+MAT)/3 AS AVG"
					+ ", RANK() OVER (ORDER BY (KOR+ENG+MAT) DESC) AS RANK"
					+ " FROM TBL_SCORE ORDER BY SID ASC";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				ScoreDTO dto = new ScoreDTO();
				
				dto.setSid(rs.getInt("SID"));
				dto.setName(rs.getString("NAME"));
				dto.setKor(rs.getInt("KOR"));
				dto.setEng(rs.getInt("ENG"));
				dto.setMat(rs.getInt("MAT"));
				dto.setTot(rs.getInt("TOT"));
				dto.setAvg(rs.getDouble("AVG"));
				dto.setRank(rs.getInt("RANK"));
				
				result.add(dto);
			}
			rs.close();
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
		
	}
	
	// 이름 검색 출력
	public ArrayList<ScoreDTO> lists(String name)
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();

		try
		{
			String sql = "SELECT * FROM( SELECT SID, NAME, KOR, ENG, MAT"
					+ ", (KOR+ENG+MAT) AS TOT, (KOR+ENG+MAT)/3 AS AVG"
					+ ", RANK() OVER (ORDER BY (KOR+ENG+MAT) DESC) AS RANK"
					+ " FROM TBL_SCORE)"
					+ " WHERE NAME = ?";
			
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, name);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				ScoreDTO dto = new ScoreDTO();
				
				dto.setSid(rs.getInt("SID"));
				dto.setName(rs.getString("NAME"));
				dto.setKor(rs.getInt("KOR"));
				dto.setEng(rs.getInt("ENG"));
				dto.setMat(rs.getInt("MAT"));
				dto.setTot(rs.getInt("TOT"));
				dto.setAvg(rs.getDouble("AVG"));
				dto.setRank(rs.getInt("RANK"));
				
				result.add(dto);
			}
			rs.close();
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
		
	}
	
	// 학생번호 검색 출력
	public ArrayList<ScoreDTO> lists(int sid)
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();

		try
		{
			String sql = "SELECT * FROM( SELECT SID, NAME, KOR, ENG, MAT"
					+ ", (KOR+ENG+MAT) AS TOT, (KOR+ENG+MAT)/3 AS AVG"
					+ ", RANK() OVER (ORDER BY (KOR+ENG+MAT) DESC) AS RANK"
					+ " FROM TBL_SCORE)"
					+ " WHERE SID = ?";
			
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, sid);
			
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				ScoreDTO dto = new ScoreDTO();
				
				dto.setSid(rs.getInt("SID"));
				dto.setName(rs.getString("NAME"));
				dto.setKor(rs.getInt("KOR"));
				dto.setEng(rs.getInt("ENG"));
				dto.setMat(rs.getInt("MAT"));
				dto.setTot(rs.getInt("TOT"));
				dto.setAvg(rs.getDouble("AVG"));
				dto.setRank(rs.getInt("RANK"));
				
				result.add(dto);
			}
			rs.close();
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
		
	}
	
	
	// 성적 수정
	public int modify(ScoreDTO dto)
	{
		int result=0;
		
		try
		{
			String sql = "UPDATE TBL_SCORE"
					+ " SET NAME = ?"
					+ ", KOR = ?, ENG = ?, MAT = ?"
					+ " WHERE SID = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getKor());
			pstmt.setInt(3, dto.getKor());
			pstmt.setInt(4, dto.getMat());
			pstmt.setInt(5, dto.getSid());
			
			result = pstmt.executeUpdate();
			if (result>0)
				System.out.println(">>데이터 수정 완료");
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return result;
		
	}
	
	// 성적 삭제
	public int remove(int sid)
	{
		int result=0;
		
		try
		{
			String sql = "DELETE FROM TBL_SCORE WHERE SID = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sid);
			
			result = pstmt.executeUpdate();
			if (result>0)
				System.out.println(">> 데이터 삭제 완료~!!");
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
		
	}
	
	
	
}
