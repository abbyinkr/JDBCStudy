/*==========================================
   DBConn.java
   - 예외처리 방법을 try ~ catch → throws
===========================================*/
package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn
{

	// 변수 선언
	private static Connection dbConn;
	
	// 메소드 정의
	public static Connection getConnection() throws ClassNotFoundException, SQLException
	{
		if (dbConn == null)
		{
			String url = "jdbc:oracle:thin:@211.238.142.166:1521:xe";
			String user = "scott";
			String pwd = "tiger";

			Class.forName("oracle.jdbc.driver.OracleDriver");
			// OracleDriver 객체 생성 
			
			dbConn = DriverManager.getConnection(url,user,pwd);
			// 오라클 서버 실제 연결 
			
		}
		
		return dbConn;
		
	}
	
	public static Connection getConnection(String url, String user, String pwd) throws ClassNotFoundException, SQLException
	{
		if (dbConn == null)
		{	
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 드라이버 객체 생성
			
			dbConn = DriverManager.getConnection(url, user, pwd);
			// 오라클 서버 실제 연결
		}
		
		return dbConn;
		
	}
	
	public static void close() throws SQLException
	{
		// 연결되있으면
		if (dbConn != null)
		{
			// 연결객체가 열려있으면 (서버에 연결되어있으면)
			if (!dbConn.isClosed())
				// 닫고
				dbConn.close();
		}
		
		dbConn = null;
		// 연결 객체 초기화
	}
	


	
}
	
	
	
	

