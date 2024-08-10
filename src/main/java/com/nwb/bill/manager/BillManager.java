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
		//Must add some code here
    	return bills;
        
    }

    public void addNewBill(Bill bill) {
        //must add some code here
    }

    public List<Bill> getOverdueBills() {
    	//Must add some code here
    	return bills;
        
    }
}

