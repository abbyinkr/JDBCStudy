/*============================
    DBConn.java
 - try ~ catch 로 예외 처리
============================*/

package com.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConn
{
	private static Connection dbConn;
	
	// 연결메소드
	public static Connection getConnection()
	{
			// if 문 안에 try ~ catch문 
			if (dbConn == null)
			{
				
				try
				{
					String url = "jdbc:oracle:thin:@211.238.142.166:1521:xe";
					String user = "scott";
					String pwd = "tiger";
					
					//DB에 연결할 드라이버객체 생성
					Class.forName("oracle.jdbc.driver.OracleDriver");
					
					dbConn = DriverManager.getConnection(url, user, pwd);
					
				} catch (Exception e)
				{
					System.out.println(e.toString());
				}

			}
			

		// 구성된 연결객체 반환
		return dbConn;
	}
	
	// 연결메소드
	public static Connection getConnection(String url, String user, String pwd)
	{

		if (dbConn == null)
		{
			try
			{
	
				//DB에 연결할 드라이버객체 생성
				Class.forName("oracle.jdbc.driver.OracleDriver");
				dbConn = DriverManager.getConnection(url, user, pwd);
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}

		}
		return dbConn;
	}
	
	// 종료메소드
	public static void close()
	{
		if (dbConn != null)
		{
			try
			{
				if (!dbConn.isClosed())
				{
					dbConn.close();
				}
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}

		}
		dbConn = null;
		
	}
	
}
