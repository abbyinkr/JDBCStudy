/*=================
  DBConn.java
=================*/
/*
※ 싱글톤(Singleton) 디자인 패턴을 이용한 Database 연결 객체 생성 전용 클래스
   → DB 연결 과정이 가장 부하가 크기 때문에
      한 번 연결된(생성된) 객체를 계속 사용하는 것이 좋다. 
*/

package com.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConn
{
	// 변수 선언
	private static Connection dbConn;
	// -- 자동으로 null 로 초기화

	// 메소드 정의 → 연결
	public static Connection getConnection()
	{
		// 한번 연결된 객체를 계속 사용
		// 즉, 연결되지 않은 경우에만 연결을 시도하겠다는 의미
		// → 싱글톤(디자인 패턴)
		if (dbConn == null)
		{
			try
			{
				String url = "jdbc:oracle:thin:@211.238.142.166:1521:xe";
				// -- 『211.238.142.166』 는 오라클 서버 ip 주소를 기재하는 부분
				// 원격지의 오라클이 아니라 로컬의 오라클 서버일 경우는
				// 『localhost』이나 『127.0.0.1』과 같이 loop back address 로 기재가능
				// 『1521』은 오라클 리스너 기본 Port Number
				// 『xe』는 오라클 SID(Express Edition은 xe)

				String user = "scott"; // -- 오라클 사용자 계정 이름
				String pwd = "tiger"; // -- 오라클 사용자 계정 암호

				Class.forName("oracle.jdbc.driver.OracleDriver");
				// -- OracleDriver 클래스에 대한 객체 생성

				dbConn = DriverManager.getConnection(url, user, pwd);
				// -- 오라클 서버 실제 연결
				// 갖고 있는 인자값(매개변수)은 오라클주소, 계정명, 패스워드

			} catch (Exception e) // ClassNotFoundException, SQLException
			{
				System.out.println(e.toString());
				// -- 오라클 서버 시연결 실패 시 오류 메세지 출력 부분
			}
		}

		return dbConn;
		// -- 구성된 연결 객체 반환
	}

	// getConnection() 메소드의 오버로딩 → 연결
	public static Connection getConnection(String url, String user, String pwd)
	{

		if (dbConn == null)
		{
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				// -- OracleDriver 클래스에 대한 객체 생성
				dbConn = DriverManager.getConnection(url, user, pwd);
				// -- 오라클 서버 실제 연결
				// 갖고 있는 인자값(매개변수)은 오라클주소, 계정명, 패스워드

			} catch (Exception e) // ClassNotFoundException, SQLException
			{
				System.out.println(e.toString());
				// -- 오라클 서버 시연결 실패 시 오류 메세지 출력 부분
			}
		}
		return dbConn;
		// -- 구성된 연결 객체 반환
	}

	// 메소드 정의 → 연결 종료
	public static void close()
	{
		// dbConn 변수(멤버 변수)는
		// Database가 연결된 상태일 경우 Connection을 갖는다.
		// 연결되지 않은 상태라면... null 을 갖는다.
		if (dbConn != null)
		{
			try
			{
				// 연결 객체의 isClosed() 메소드를 통해 연결 상태 확인
				// -- 연결이 닫혀있는 경우 true 반환
				// 연결이 닫혀있지 않은 경우 false 반환
				if (!dbConn.isClosed())
					dbConn.close();
				// -- 연결 객체의 close() 메소드를 통해 연결 종료

			} catch (Exception e)
			{
				System.out.println(e.toString());
				
				
			}
		}


		dbConn = null;
		// -- 연결 객체 초기화
	}

}
