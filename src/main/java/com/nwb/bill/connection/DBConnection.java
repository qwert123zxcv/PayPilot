package com.nwb.bill.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	public static Connection getConnection(){
		Connection conn=null;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "harsho123");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class DBConnection {
//
//    public static void main(String[] args) {
//        try {
//            Connection connection = DriverManager.getConnection(
//                "jdbc:oracle:thin:@localhost:1521:xe", "system", "singh13012001");
//            System.out.println("Connected " + connection);
//
//            Statement smt = connection.createStatement();
//            String sql = "CREATE TABLE bills (" +
//                         "bill_id VARCHAR2(255) PRIMARY KEY, " +
//                         "bill_name VARCHAR2(255), " +
//                         "bill_category VARCHAR2(255) CHECK (bill_category IN ('House Rent', 'Debt Payments', 'Groceries', 'Internet Charges', 'Cellphone Charges')), " +
//                         "due_date DATE, " +
//                         "amount FLOAT, " +
//                         "reminder_frequency VARCHAR2(255), " +
//                         "attachment VARCHAR2(255) CHECK (attachment IN ('Yes', 'No')), " +
//                         "notes VARCHAR2(255), " +
//                         "is_recurring NUMBER(1), " +
//                         "payment_status VARCHAR2(255) CHECK (payment_status IN ('Paid', 'Not Paid')), " +
//                         "overdue_days NUMBER(20)" +
//                         ")";
//            smt.executeUpdate(sql);
//            System.out.println("Table created");
//
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}


