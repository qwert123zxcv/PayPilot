package com.nwb.bill.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	public static Connection getConnection(){
		Connection conn=null;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "username", "password");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}
