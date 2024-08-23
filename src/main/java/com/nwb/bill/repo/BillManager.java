
package com.nwb.bill.repo;
import com.nwb.bill.connection.DBConnection;
import com.nwb.bill.model.Bill;
import com.nwb.bill.connection.DBInsert;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class BillManager {
	private List<Bill> bills;
	Scanner s=new Scanner(System.in);

	public BillManager() {
		this.bills = new ArrayList<>();
	}

	public List<Bill> getBills() {
		return bills;
	}

	public void addNewBill(Bill bill) {
		try {
			Connection connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe", "system", "harsho123");
			System.out.println("Connected " + connection);

			PreparedStatement psmt = connection.prepareStatement("INSERT INTO bills (bill_id, bill_name, bill_category, due_date, amount, reminder_frequency, attachment, notes, is_recurring, payment_status, overdue_days) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			Scanner s = new Scanner(System.in);

			// Set the values in the PreparedStatement
			psmt.setInt(1, bill.getBillId());
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
		for (Bill bill : bills) {

			boolean matchesCategory = true;
			if (category != null && !category.equalsIgnoreCase("all")) {
				matchesCategory = bill.getBillCategory().equalsIgnoreCase(category);
			}

			boolean matchesFromDate = fromDate == null? true : !bill.getDueDate().before(fromDate);
			boolean matchesToDate = toDate == null? true : !bill.getDueDate().after(toDate);
			boolean matchesStatus = status == null? true: bill.getPaymentStatus().equalsIgnoreCase(status);

			if (matchesCategory && matchesFromDate && matchesToDate && matchesStatus) {
				filteredBills.add(bill);
			}
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
		bill.setPaymentStatus("PAID");
		bill.setOverdueDays(0); 
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
