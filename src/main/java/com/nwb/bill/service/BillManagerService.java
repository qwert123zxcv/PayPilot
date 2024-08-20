package com.nwb.bill.service;

import java.util.Date;
import java.util.List;

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

	public boolean snoozeBill(int billId, Date snoozeDate) {
		
		Bill searchBill = searchBillWithId(billId);
		if (searchBill != null) {
			billManager.snoozeBill(searchBill, snoozeDate);
			return true;
		}
		return false;
	}
	
	public boolean markBillAsPaid(int billId) {
		Bill searchBill = searchBillWithId(billId);
		if (searchBill != null) {
			billManager.markBillAsPaid(searchBill);
			return true;
		}
		return false;
	}
	
	public Bill searchBillWithId(int billId) {
		List<Bill> allBills = billManager.getBills();
		Bill searchBill = null;
		for (Bill bill : allBills) {
            if (bill.getBillId() == billId) {
            	searchBill = bill;
            	break;
            }
        }
		return searchBill;
	}

	
}
