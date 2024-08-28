package com.basics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basics.model.Bill;
import com.basics.repository.BillRepository;

@Service
public class BillService {
	
	@Autowired
    private BillRepository billRepository;
	
	public Iterable<Bill> getAllBills() {
        return billRepository.findAll();
    }
	
	 public void addNewBill(Bill bill) {
	        billRepository.save(bill);
	    }
}
