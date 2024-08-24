
package com.nwb.bill.repo;
import com.nwb.bill.connection.DBConnection;
import com.nwb.bill.model.Bill;

import java.sql.*;
import java.util.Date;
import java.util.*;


public class BillManager {
	private List<Bill> bills;
	Scanner s=new Scanner(System.in);

	public BillManager() {
		this.bills = new ArrayList<>();
	}

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

	public void addNewBill(Bill bill) {
		bills.add(bill);
	}

	//Get all overdue bills
	public List<Bill> getOverdueBills() {
		//Must add some code here
		List<Bill> overdueBills=new ArrayList<>();
		Connection conn=DBConnection.getConnection();
		String sql="SELECT * FROM bills";
		Statement stmt=null;
		ResultSet res=null;
		try {
			stmt=conn.createStatement();
			res=stmt.executeQuery(sql);
			if (res != null) { 
				while(res.next()) {
					if(res.getInt("overdue_days")>0) {
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
				}
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

		//		for(int i=0;i<bills.size();i++) {
		//			if(bills.get(i).getOverdueDays()>0) {
		//				overdueBills.add(bills.get(i));
		//			}
		//		}
		return overdueBills;
	}


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

	public List<Bill> getUpcomingBills() {
		List<Bill> upcomingBills=new ArrayList<>();
		for(int i=0;i<bills.size();i++) {
			if(!bills.get(i).getPaymentStatus().equals("Paid") && bills.get(i).getOverdueDays()==0) {
				upcomingBills.add(bills.get(i));
			}
		}
		return upcomingBills;
	}

	public void markBillAsPaid(Bill bill) {
		String sql = "UPDATE bills SET payment_status = ?, overdue_days = ? WHERE bill_id = ?";
	    
	    try{
	    	Connection conn = DBConnection.getConnection();
	    	PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, "Paid");
	        pstmt.setInt(2, 0);
	        pstmt.setString(3, bill.getBillId());
	        pstmt.executeUpdate();  
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
//		bill.setPaymentStatus("PAID");
//		bill.setOverdueDays(0); 
	}

	public void snoozeBill(Bill bill, Date snoozeDate) {
		bill.setDueDate(snoozeDate);
		bill.setOverdueDays(0); 
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
