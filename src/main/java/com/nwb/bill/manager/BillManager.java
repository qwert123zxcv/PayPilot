package com.nwb.bill.manager;
import com.nwb.bill.model.Bill;

import java.util.*;

public class BillManager {
	private String userId;
    private List<Bill> bills;

    public BillManager(String userId) {
    	this.userId = userId;
        this.bills = new ArrayList<>();
    }
    
    public List<Bill> getBillsOverview(String category, Date fromDate, Date toDate, String status) {
        List<Bill> filteredBills = new ArrayList<>();
        for (Bill bill : bills) {
            boolean matchesCategory = bill.getBillCategory().equalsIgnoreCase(category);
            boolean matchesFromDate = !bill.getDueDate().before(fromDate);
            boolean matchesToDate = !bill.getDueDate().after(toDate);
            boolean matchesStatus = bill.getPaymentStatus().equalsIgnoreCase(status);
            
            if (matchesCategory && matchesFromDate && matchesToDate && matchesStatus) {
                filteredBills.add(bill);
            }
        }
        return filteredBills;
    }


    public void addNewBill(Bill bill) {
        //must add some code here
    }

    public List<Bill> getOverdueBills() {
    	//Must add some code here
    	return bills;
        
    }
}

