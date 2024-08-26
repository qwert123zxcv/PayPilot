
package com.nwb.bill.repo;
import com.nwb.bill.connection.DBConnection;
import com.nwb.bill.model.Bill;
import java.sql.*;
import java.util.*;

import java.util.Date;

/**
 * BillManager.java
 * 
 * Author: Harshit Bhatt, Grace Hephzibah
 * Date: 24-Aug-2024
 * 
 * This class represents the manager for handling bill-related operations in the Bill Management System.
 * It provides functionalities to retrieve, add, update, and filter bills using an Oracle database.
 */
public class BillManager {
	private List<Bill> bills;
	Scanner s=new Scanner(System.in);

	public BillManager() {
		this.bills = new ArrayList<>();
	}

	/**
	 * Method to retrieve all bills from the database. This method connects to the 
	 * database, executes a SQL query to select all records from the "bills" table, 
	 * and returns a list of Bill objects representing each record.
	 *
	 * @return A list of Bill objects representing all the bills stored in the database.
	 */
	public List<Bill> getBills() {
		List<Bill> allBills =new ArrayList<>();
		Connection conn=DBConnection.getConnection();
		String sql="SELECT * FROM bills";
		Statement stmt=null;
		ResultSet res=null;
		try {
			stmt=conn.createStatement();
			res=stmt.executeQuery(sql);
			if (res != null) { 
				while(res.next()) {
					Bill b=new Bill();
					b.setBillId(res.getString("bill_id"));
					b.setBillName(res.getString("bill_name"));
					b.setBillCategory(res.getString("bill_category"));
					b.setDueDate(res.getDate("due_date"));
					b.setAmount(res.getFloat("amount"));  
					b.setReminderFrequency(res.getString("reminder_frequency"));
					b.setAttachment(res.getString("attachment"));
					b.setNotes(res.getString("notes"));
					b.setRecurring(res.getInt("is_recurring") == 1); 
					b.setPaymentStatus(res.getString("payment_status"));
					b.setOverdueDays(res.getInt("overdue_days")); 
					allBills.add(b);
				}
			}
		} catch (SQLException e) {
			System.out.println();
			e.printStackTrace();
		}
		return bills;
	}

	/**
	 * Method to add a new bill to the database. It establishes a connection to the 
	 * Oracle database, prepares an SQL INSERT statement with the bill details, 
	 * executes the statement, and displays a message indicating whether the 
	 * record was successfully inserted.
	 *
	 * @param bill The Bill object containing details like bill ID, bill name, 
	 *             category, due date, amount, reminder frequency, attachment, 
	 *             notes, recurring status, payment status, and overdue days.
  	 */
	public void addNewBill(Bill bill) {
		try {
			Connection connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe", "system", "harsho123");
			System.out.println("Connected " + connection);

			PreparedStatement psmt = connection.prepareStatement("INSERT INTO bills (bill_id, bill_name, bill_category, due_date, amount, reminder_frequency, attachment, notes, is_recurring, payment_status, overdue_days) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			// Set the values in the PreparedStatement
			psmt.setString(1, bill.getBillId()+"");
			psmt.setString(2, bill.getBillName());
			psmt.setString(3, bill.getBillCategory());
			psmt.setDate(4, bill.getDueDate());  // Use java.sql.Date here
			psmt.setFloat(5, bill.getAmount());
			psmt.setString(6, bill.getReminderFrequency());
			psmt.setString(7, bill.getAttachment());
			psmt.setString(8, bill.getNotes());
			int rec=bill.isRecurring();
			psmt.setInt(9, rec);
			psmt.setString(10, bill.getPaymentStatus());
			psmt.setInt(11, bill.getOverdueDays());

			int count = psmt.executeUpdate();
			if (count > 0) {
				System.out.println("Record inserted");
			} else {
				System.out.println("No record inserted");
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to retrieve a list of overdue bills from the database. It establishes 
	 * a connection to the database, executes a SQL SELECT query to fetch all bills 
	 * with overdue days greater than zero, and maps the results to a list of Bill 
	 * objects.
	 *
	 * @return A list of Bill objects representing the overdue bills.
	 */
	public List<Bill> getOverdueBills() {
		//Must add some code here
		List<Bill> overdueBills=new ArrayList<>();
		Connection conn=DBConnection.getConnection();
		String sql="SELECT * FROM bills where overdue_days>0";
		Statement stmt=null;
		ResultSet res=null;
		try {
			stmt=conn.createStatement();
			res=stmt.executeQuery(sql);
			while(res.next()) {
				Bill b=new Bill();
				b.setBillId(res.getString("bill_id"));
				b.setBillName(res.getString("bill_name"));
				b.setBillCategory(res.getString("bill_category"));
				b.setDueDate(res.getDate("due_date"));
				b.setAmount(res.getFloat("amount"));  
				b.setReminderFrequency(res.getString("reminder_frequency"));
				b.setAttachment(res.getString("attachment"));
				b.setNotes(res.getString("notes"));
				b.setRecurring(res.getInt("is_recurring") == 1); 
				b.setPaymentStatus(res.getString("payment_status"));
				b.setOverdueDays(res.getInt("overdue_days")); 
				overdueBills.add(b);
			}
		} catch (SQLException e) {
			System.out.println();
			e.printStackTrace();
		}finally {
			try {
				if (res != null) res.close();
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return overdueBills;
	}

	/**
	 * Method to retrieve a filtered list of bills from the database based on specified 
	 * criteria such as category, date range, and payment status. The method constructs 
	 * a dynamic SQL query based on the provided filters.
	 *
	 * @param category The category of the bills to filter by. If null or "all", this 
	 *                 filter is ignored.
	 * @param fromDate The start date for the due date filter. Bills with a due date 
	 *                 on or after this date will be included. If null, this filter is ignored.
	 * @param toDate The end date for the due date filter. Bills with a due date 
	 *               on or before this date will be included. If null, this filter is ignored.
	 * @param status The payment status of the bills to filter by. If null or empty, 
	 *               this filter is ignored.
	 * @return A list of Bill objects that match the specified filters.
	 */
	public List<Bill> getBillsOverview(String category, Date fromDate, Date toDate, String status) {
	    
		List<Bill> filteredBills = new ArrayList<>();
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
	    StringBuilder sql = new StringBuilder("SELECT * FROM bills WHERE 1=1");
	    
	    boolean categoryCondition = category != null && !category.equalsIgnoreCase("all") && category.length() != 0;
	    boolean fromDateCondition = fromDate != null;
	    boolean toDateCondition = toDate != null;
	    boolean statusCondition = status != null && status.length() != 0;

	    if (categoryCondition) {
	        sql.append(" AND bill_category = ?");
	    }
	    if (fromDateCondition) {
	        sql.append(" AND due_date >= ?");
	    }
	    if (toDateCondition) {
	        sql.append(" AND due_date <= ?");
	    }
	    if (statusCondition) {
	        sql.append(" AND payment_status = ?");
	    }

	    try{
	    	String query = sql.toString();
	    	pstmt = conn.prepareStatement(query);
	        
	        int paramIndex = 1;
	        if (categoryCondition) {
	            pstmt.setString(paramIndex++, category);
	        }
	        if (fromDateCondition) {
	        	// Convert java.util.Date to java.sql.Date
	            pstmt.setDate(paramIndex++, new java.sql.Date(fromDate.getTime())); 
	        }
	        if (toDateCondition) {
	        	// Convert java.util.Date to java.sql.Date
	            pstmt.setDate(paramIndex++, new java.sql.Date(toDate.getTime())); 
	        }
	        if (statusCondition) {
	            pstmt.setString(paramIndex++, status);
	        }

	        rs = pstmt.executeQuery();
	            while (rs.next()) {
	                Bill bill = new Bill();
	                bill.setBillId(rs.getString("bill_id"));
	                bill.setBillName(rs.getString("bill_name"));
	                bill.setBillCategory(rs.getString("bill_category"));
	                bill.setDueDate(rs.getDate("due_date"));
	                bill.setAmount(rs.getFloat("amount"));
	                bill.setReminderFrequency(rs.getString("reminder_frequency"));
	                bill.setAttachment(rs.getString("attachment"));
	                bill.setNotes(rs.getString("notes"));
	                bill.setRecurring(rs.getInt("is_recurring") == 1);
	                bill.setPaymentStatus(rs.getString("payment_status"));
	                bill.setOverdueDays(rs.getInt("overdue_days"));
	                filteredBills.add(bill);
	            }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return filteredBills;
	}

	/**
	 * Method to retrieve a list of overdue bills from the database. It establishes 
	 * a connection to the database, executes a SQL SELECT query to fetch all bills 
	 * with overdue days greater than zero, and maps the results to a list of Bill 
	 * objects.
	 *
	 * @return A list of Bill objects representing the overdue bills.
	 */
	public List<Bill> getUpcomingBills() {
		List<Bill> upcomingBills = new ArrayList<>();
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
	    StringBuilder sql = new StringBuilder("SELECT * FROM bills WHERE payment_status = ?");

	    try{
	    	String query = sql.toString();
	    	pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, "upcoming");
	        
	        rs = pstmt.executeQuery();
	            while (rs.next()) {
	                Bill bill = new Bill();
	                bill.setBillId(rs.getString("bill_id"));
	                bill.setBillName(rs.getString("bill_name"));
	                bill.setBillCategory(rs.getString("bill_category"));
	                bill.setDueDate(rs.getDate("due_date"));
	                bill.setAmount(rs.getFloat("amount"));
	                bill.setReminderFrequency(rs.getString("reminder_frequency"));
	                bill.setAttachment(rs.getString("attachment"));
	                bill.setNotes(rs.getString("notes"));
	                bill.setRecurring(rs.getInt("is_recurring") == 1);
	                bill.setPaymentStatus(rs.getString("payment_status"));
	                bill.setOverdueDays(rs.getInt("overdue_days"));
	                upcomingBills.add(bill);
	            }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return upcomingBills;
	}

	/**
	 * Method to mark a bill as paid in the database. It updates the payment status 
	 * of the specified bill to "paid" and sets the overdue days to 0.
	 *
	 * @param bill The Bill object representing the bill to be marked as paid. 
	 *             The bill's ID is used to identify the specific record to update.
	 */
	public void markBillAsPaid(Bill bill) {
		String sql = "UPDATE bills SET payment_status = ?, overdue_days = ? WHERE bill_id = ?";
	    
	    try{
	    	Connection conn = DBConnection.getConnection();
	    	PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, "paid");
	        pstmt.setInt(2, 0);
	        pstmt.setString(3, bill.getBillId()+"");
	        pstmt.executeUpdate();  
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	/**
	 * Method to snooze a bill by updating its due date in the database. The method 
	 * sets the bill's due date to the specified snooze date and resets the overdue 
	 * days to 0.
	 *
	 * @param bill The Bill object representing the bill to be snoozed. The bill's 
	 *             ID is used to identify the specific record to update.
	 * @param snoozeDate The new due date to which the bill is snoozed.
	 */
	public void snoozeBill(Bill bill, Date snoozeDate) {
		String sql = "UPDATE bills SET due_date = ?, overdue_days = ? WHERE bill_id = ?";
        
	    try {
	    	Connection conn = DBConnection.getConnection();
	    	PreparedStatement pstmt = conn.prepareStatement(sql);
	    	pstmt.setDate(1, new java.sql.Date(snoozeDate.getTime())); 
	        pstmt.setInt(2, 0); 
	        pstmt.setString(3, bill.getBillId()+"");
	        pstmt.executeUpdate();	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }    
	}

	//	//If bill category and bill name is selected
	//	public List<Bill> getOverdueBills(List<Bill> bills, String bill_category, String bill_name) {
	//		//Must add some code here
	//		List<Bill> overdueBills=new ArrayList<>();
	//
	//		for(int i=0;i<bills.size();i++) {
	//			if(bills.get(i).getBillCategory().trim().equals(bill_category)
	//					&& bills.get(i).getBillName().trim().equals(bill_name)
	//					&& bills.get(i).getOverdueDays()>0) {
	//				overdueBills.add(bills.get(i));
	//				break;
	//			}
	//		}
	//		return overdueBills;
	//	}
	//	
	//	//For filter BillCategory in UI
	//	public List<Bill> getOverdueBills(List<Bill> bills, String bill_category){
	//		List<Bill> overdueBills=new ArrayList<>();
	//		for(int i=0;i<bills.size();i++) {
	//			if(bills.get(i).getBillCategory().trim().equals(bill_category)) {
	//				overdueBills.add(bills.get(i));
	//			}
	//		}
	//		return overdueBills;
	//	}
	//	
	//	//For filter Date Range in UI
	//	public List<Bill> getOverdueBills(List<Bill> bills, Date start_date, Date end_date){
	//		List<Bill> overdueBills=new ArrayList<>();
	//		for(int i=0;i<bills.size();i++) {
	//			Bill curr_bill=bills.get(i);
	//			Date bill_date=curr_bill.getDueDate();
	//			
	//			if(start_date.compareTo(bill_date)<=0 && 
	//					end_date.compareTo(bill_date)>=0) {
	//				overdueBills.add(curr_bill);
	//			}
	//		}
	//		return overdueBills;
	//	}

}
