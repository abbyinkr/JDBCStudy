
package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.util.DBConn;

public class MemberDAO
{
	private Connection conn;
	
	// 데이터베이스 연결 메소드
	public Connection connection()
	{
		conn = DBConn.getConnection();
		return conn;
	}
	
	// 데이터베이스 연결 종료 메소드
	public void close()
	{
		DBConn.close();
	}
	
	// 데이터 입력 담당 메소드
	public int add(MemberDTO dto) throws SQLException
	{	
		int result = 0;
		
		// 작업객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 구성
		String sql = String.format("INSERT INTO TBL_EMP (EMP_ID, EMP_NAME, SSN, IBSADATE"
				+ ", CITY_ID, TEL, BUSEO_ID, JIKWI_ID, BASICPAY, SUDANG)"
				+ " VALUES (EMPSEQ.NEXTVAL, '%s', '%s', TO_DATE('%s', 'YYYY-MM-DD')"
				+ ", (SELECT CITY_ID FROM TBL_CITY WHERE CITY_LOC='%s')"
				+ ", '%s'"
				+ ", (SELECT BUSEO_ID FROM TBL_BUSEO WHERE BUSEO_NAME='%s')"
				+ ", (SELECT JIKWI_ID FROM TBL_JIKWI WHERE JIKWI_NAME='%s')"
				+ ", %d, %d)", dto.getEmpName(), dto.getSsn(), dto.getIbsaDate()
				   , dto.getCityLoc(), dto.getTel(), dto.getBuseoName(), dto.getJikwiName()
				   , dto.getBasicPay(), dto.getSudang());
		
		// 쿼리문 수행
		result = stmt.executeUpdate(sql);
		
		if (result > 0)
		{
			System.out.println(">> 데이터 입력이 완료되었습니다.");
		}
		
		// 리소스 반납
		stmt.close();
		
		// 연결 종료
		return result;
	}//end add()
	
	
	// 전체 직원수 카운트 메소드
	public int memberCount() throws SQLException
	{
		int count=0;
		
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_EMP";
		
		ResultSet rs = stmt.executeQuery(sql);
		
		if (rs.next())
			count = rs.getInt("COUNT");
		
		rs.close();
		stmt.close();
		
		return count;
		
	}//end memberCount()
	
	// 검색한 직원수 카운트 메소드
	// EMP_ID = 1001			  → key : EMP_ID    	/ 	value : 1001
	// EMP_NAME = '이상화'	  → key : EMP_NAME	   / 	value : '이상화'
	// BUSEO_NAME = '개발부'  → key : BUSEO_NAME	/ 	value : '개발부'
	// JIKWI_NAME = '대리'    → key : JIKWI_NAME	/ 	value : '대리' 
	public int memberCount(String key, String value) throws SQLException
	{
		int result=0;
		
		Statement stmt = conn.createStatement();
		
		String sql = "";
		
		if (key.equals("ENP_ID"))
		{	
			sql = String.format("SELECT COUNT(*) AS COUNT FROM EMPVIEW WHERE %s = %s", key ,value);
		}
		else
		{
			sql = String.format("SELECT COUNT(*) AS COUNT FROM EMPVIEW WHERE %s = '%s' ", key, value);
		}
		
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{	
			result = rs.getInt("COUNT");
		}
		rs.close();
		stmt.close();
		
		
		return result;
	}
	
	
	// 직원 데이터 전체 출력 담당 메소드(사번/이름/부서/직위/ 급여 내림차) → select 
	//--사번, 이름, 주민번호, 입사일, 지역, 전화번호, 부서, 직위, 기본급, 수당, 급여
	public ArrayList<MemberDTO> lists(String key) throws SQLException 
	{
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();

		// 쿼리문 준비
		String sql = String.format("SELECT EMP_ID, EMP_NAME, SSN"
						+ ", TO_CHAR(IBSADATE, 'YYYY-MM-DD') AS IBSADATE"
						+ ", CITY_LOC, TEL,BUSEO_NAME, JIKWI_NAME"
						+ ", MIN_BASICPAY, BASICPAY, SUDANG, PAY"
						+ " FROM EMPVIEW"
						+ " ORDER BY %s", key);
		// 쿼리문 수행
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{
			MemberDTO dto = new MemberDTO();
			dto.setEmpId(rs.getInt("EMP_ID"));
			dto.setEmpName(rs.getString("EMP_NAME"));
			dto.setSsn(rs.getString("SSN"));
			dto.setIbsaDate(rs.getString("IBSADATE"));
			dto.setCityLoc(rs.getString("CITY_LOC"));
			dto.setTel(rs.getString("TEL"));
			dto.setBuseoName(rs.getString("BUSEO_NAME"));
			dto.setJikwiName(rs.getString("JIKWI_NAME"));
			dto.setBasicPay(rs.getInt("BASICPAY"));
			dto.setSudang(rs.getInt("SUDANG"));
			dto.setPay(rs.getInt("PAY"));
			
			result.add(dto);
			
		}
		rs.close();
		stmt.close();

		return result;
	}//end lists()

	
	// 직원 검색 출력 메소드(사번/이름/부서/직위)
	public ArrayList<MemberDTO> searchLists(String key, String value) throws SQLException
	{
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		
		// 작업객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = "";
		
		if (key.equals("EMP_ID"))
		{
			sql = String.format("SELECT EMP_ID, EMP_NAME, SSN"
					+ ", TO_CHAR(IBSADATE, 'YYYY-MM-DD') AS IBSADATE"
					+ ", CITY_LOC, TEL, BUSEO_NAME, JIKWI_NAME, BASICPAY, SUDANG, PAY"
					+ " FROM EMPVIEW"
					+ " WHERE %s = %s", key, value);
		} else
		{
			sql = String.format("SELECT EMP_ID, EMP_NAME, SSN"
					+ ", TO_CHAR(IBSADATE, 'YYYY-MM-DD') AS IBSADATE"
					+ ", CITY_LOC, TEL, BUSEO_NAME, JIKWI_NAME"
					+ ", BASICPAY, SUDANG, PAY"
					+ " FROM EMPVIEW"
					+ " WHERE %s = '%s' ", key, value);
		}
		
		// 쿼리문 실행
		ResultSet rs= stmt.executeQuery(sql);
		
		// ResultSet 처리 -> 반복문 -> MemberDTO객체 ArrayList 에 저장
		while (rs.next())
		{
			// MemberDTO 객체 생성
			MemberDTO dto = new MemberDTO();

			dto.setEmpId(rs.getInt("EMP_ID"));
			dto.setEmpName(rs.getString("EMP_NAME"));
			dto.setSsn(rs.getString("SSN"));
			dto.setIbsaDate(rs.getString("IBSADATE"));
			dto.setCityLoc(rs.getString("CITY_LOC"));
			dto.setTel(rs.getString("TEL"));
			dto.setBuseoName(rs.getString("BUSEO_NAME"));
			dto.setJikwiName(rs.getString("JIKWI_NAME"));
			dto.setBasicPay(rs.getInt("BASICPAY"));
			dto.setSudang(rs.getInt("SUDANG"));
			dto.setPay(rs.getInt("PAY"));
			
			result.add(dto);
			
		}
		// 리소스 반납
		rs.close();
		stmt.close();
		// 최종 결과값 반환
		return result; 
		
	}//end searchLists()
	
	
	/*// 내가 쓴거
	public ArrayList<MemberDTO> searchLists(String key, String value) throws SQLException 
	{
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 구성
		String sql = "";
		
		if (key.equals("EMP_ID"))
		{
			sql = String.format("SELECT EMP_ID, EMP_NAME, SSN"
					+ ", TO_CHAR(IBSADATE, 'YYYY-MM-DD') AS IBSADATE"
					+ ", CITY_LOC, TEL, BUSEO_NAME, JIKWI_NAME"
					+ ", BASICPAY, SUDANG, PAY"
					+ " FROM EMPVIEW"
					+ " WHERE %s=%s", key, value);
		}
		else
		{
			sql = String.format("SELECT EMP_ID, EMP_NAME, SSN"
					+ ", TO_CHAR(IBSADATE, 'YYYY-MM-DD') AS IBSADATE"
					+ ", CITY_LOC, TEL, BUSEO_NAME, JIKWI_NAME"
					+ ", BASICPAY, SUDANG, PAY"
					+ " FROM EMPVIEW"
					+ " WHERE %s='%s' ", key, value);
			
		}
		
		// 쿼리문 수행
		ResultSet rs = stmt.executeQuery(sql);
		
		// ResultSet 처리 - 반복문 - DTO 인스턴스 생성 - arrayList에 적재
		
		while (rs.next())
		{
			MemberDTO dto = new MemberDTO();
			dto.setEmpId(rs.getInt("EMP_ID"));
			dto.setEmpName(rs.getString("EMP_NAME"));
			dto.setSsn(rs.getString("SSN"));
			dto.setIbsaDate(rs.getString("IBSADATE"));
			dto.setCityLoc(rs.getString("CITY_LOC"));
			dto.setTel(rs.getString("TEL"));
			dto.setBuseoName(rs.getString("BUSEO_NAME"));
			dto.setJikwiName(rs.getString("JIKWI_NAME"));
			dto.setBasicPay(rs.getInt("BASICPAY"));
			dto.setSudang(rs.getInt("SUDANG"));
			dto.setPay(rs.getInt("PAY"));
			
			result.add(dto);
			
		}
		// 리소스 반납
		rs.close();
		stmt.close();

		return result;
	
	}//end searchLists()
	*/
	

	// 지역 리스트 조회
	public ArrayList<String> searchCity() throws SQLException
	{
		ArrayList<String> result = new ArrayList<String>();
		
		Statement stmt = conn.createStatement();
		String sql = "SELECT CITY_LOC FROM TBL_CITY";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
			result.add(rs.getString("CITY_LOC"));
		rs.close();
		stmt.close();
		
		return result;
	
	}
	
	
	// 부서 리스트 조회
	public ArrayList<String> searchBuseo() throws SQLException
	{
		ArrayList<String> result = new ArrayList<String>();
		Statement stmt = conn.createStatement();
		String sql = "SELECT BUSEO_NAME FROM TBL_BUSEO";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
			result.add(rs.getString("BUSEO_NAME"));
		rs.close();
		stmt.close();
		
		return result;
	
	}
	// 직위 리스트 조회
	public ArrayList<String> searchJikwi() throws SQLException
	{
		ArrayList<String> result = new ArrayList<String>();
		Statement stmt = conn.createStatement();
		String sql = "SELECT JIKWI_NAME FROM TBL_JIKWI";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
			result.add(rs.getString("JIKWI_NAME"));
		rs.close();
		stmt.close();
		
		return result;
	
	}
	
	// 직위에 따른 기본급 조회
	public int searchBasicPay(String jikwiName) throws SQLException
	{
		int result=0;
		
		Statement stmt = conn.createStatement();
		
		String sql = String.format("SELECT MIN_BASICPAY FROM TBL_JIKWI WHERE JIKWI_NAME = '%s'", jikwiName);
		
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{	
			result = rs.getInt("MIN_BASICPAY");
		}
		rs.close();
		stmt.close();
		
		return result;
		
	}
	
	
	
	
	// 직원 정보 수정 메소드 → update
	public int modify(MemberDTO dto) throws SQLException
	{
		int result=0;
		
		Statement stmt = conn.createStatement();
		
		String sql = String.format("UPDATE TBL_EMP"
				+ " SET EMP_NAME = '%s', SSN = '%s'"
				+ ", IBSADATE = TO_DATE('%s','YYYY-MM-DD')"
				+ ", CITY_ID = (SELECT CITY_ID FROM TBL_CITY WHERE CITY_LOC = '%s')"
				+ ", TEL = '%s'"
				+ ", BUSEO_ID = (SELECT BUSEO_ID FROM TBL_BUSEO WHERE BUSEO_NAME = '%s')"
				+ ", JIKWI_ID = (SELECT JIKWI_ID FROM TBL_JIKWI WHERE JIKWI_NAME = '%s')"
				+ ", BASICPAY = %d, SUDANG = %d"
				+ " WHERE EMP_ID = %d"
				, dto.getEmpName(), dto.getSsn()
				, dto.getIbsaDate(), dto.getCityLoc()
				, dto.getTel(), dto.getBuseoName(), dto.getJikwiName()
				, dto.getBasicPay(), dto.getSudang()
				, dto.getEmpId());
		result = stmt.executeUpdate(sql);
		stmt.close();
		
		return result;
	}
	
	
	// 직원 삭제 메소드
	public int remove(int empId) throws SQLException
	{
		int result=0;
		
		Statement stmt = conn.createStatement();
		String sql = String.format("DELETE  FROM TBL_EMP WHERE EMP_ID = %d", empId);
		result = stmt.executeUpdate(sql);
		stmt.close();
		
		return result;
		
	}
	

}
