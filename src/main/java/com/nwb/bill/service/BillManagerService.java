package com.nwb.bill.service;

import java.sql.*;
import java.util.*;
import java.util.Date;

import com.nwb.bill.connection.DBConnection;
import com.nwb.bill.model.Bill;
import com.nwb.bill.repo.BillManager;

public class BillManagerService {
	private static BillManager billManager = new BillManager();
	
	public BillManagerService(){
		
	}
	
	public void addNewBill(Bill bill) {
		billManager.addNewBill(bill);
	}
	
	public List<Bill> getOverdueBills() {
		List<Bill> overdueBills = billManager.getOverdueBills();
		return overdueBills;
	}

	public List<Bill> getBillsOverview(String category, Date fromDate, Date toDate, String status) {
		List<Bill> billOverview = billManager.getBillsOverview(category, fromDate, toDate, status);
		return billOverview;
	}

	public List<Bill> getUpcomingBills() {
		List<Bill> upcomingBills = billManager.getUpcomingBills();
		return upcomingBills;
	}

	public int snoozeBill(String billId, Date snoozeDate) {
		int returnValue = 3;
		
		Date currentDate = new Date();
		if (snoozeDate.before(currentDate)) {
			returnValue = 2;
		}
		else {
			Bill searchBill = searchBillWithId(billId);
			
			if (searchBill != null) {
				billManager.snoozeBill(searchBill, snoozeDate);
				return 1;
			}
		}
		
		return returnValue;
		// 1 -> Bill found 
		// 2 -> snooze date is less than current date
		// 3 -> bill not found 
	}
	
	public boolean markBillAsPaid(String billId) {
		Bill searchBill = searchBillWithId(billId);
		if (searchBill != null) {
			billManager.markBillAsPaid(searchBill);
			return true;
		}
		return false;
	}
	
	public Bill searchBillWithId(String billId) {
		Bill searchBill = null;
	    String sql = "SELECT * FROM bills WHERE bill_id = ?";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try{    
	    	conn = DBConnection.getConnection();
	    	pstmt = conn.prepareStatement(sql);
	    	
	        pstmt.setString(1, billId);
	        
	        try{
	        	ResultSet rs = pstmt.executeQuery();
	            if (rs.next()) {
	                searchBill = new Bill();
	                searchBill.setBillId(rs.getString("bill_id"));
	                searchBill.setBillName(rs.getString("bill_name"));
	                searchBill.setBillCategory(rs.getString("bill_category"));
	                searchBill.setDueDate(rs.getDate("due_date"));
	                searchBill.setAmount(rs.getFloat("amount"));
	                searchBill.setReminderFrequency(rs.getString("reminder_frequency"));
	                searchBill.setAttachment(rs.getString("attachment"));
	                searchBill.setNotes(rs.getString("notes"));
	                searchBill.setRecurring(rs.getInt("is_recurring") == 1);
	                searchBill.setPaymentStatus(rs.getString("payment_status"));
	                searchBill.setOverdueDays(rs.getInt("overdue_days"));
	            }   
	        } catch (SQLException e) {
	        e.printStackTrace();
	        }
	    }catch(SQLException e){
	    	e.printStackTrace();
	    }
	    return searchBill;
	
	}
}
