package com.basics.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
	 
	// Get a bill by ID
	    public Bill getBillById(Long id) {
	        Optional<Bill> optionalBill = billRepository.findById(id);
	        return optionalBill.orElse(null); // Return the bill if found, else return null
	    }

	    // Delete all bills
	    public void deleteAllBills() {
	        billRepository.deleteAll();
	    }

	    // Delete a bill by ID
	    public void deleteBillById(Long id) {
	        billRepository.deleteById(id);
	    }

	    // Update a bill
	    public Bill updateBill(Long id, Bill updatedBill) {
	        Optional<Bill> optionalBill = billRepository.findById(id);
	        if (optionalBill.isPresent()) {
	            Bill existingBill = optionalBill.get();
	            // Update the fields of the existing bill with the new values
	            existingBill.setBillName(updatedBill.getBillName());
	            existingBill.setBillCategory(updatedBill.getBillCategory());
	            existingBill.setDueDate(updatedBill.getDueDate());
	            existingBill.setAmount(updatedBill.getAmount());
	            existingBill.setReminderFrequency(updatedBill.getReminderFrequency());
	            existingBill.setAttachment(updatedBill.getAttachment());
	            existingBill.setNotes(updatedBill.getNotes());
	            existingBill.setPaymentStatus(updatedBill.getPaymentStatus());
	            existingBill.setOverdueDays(updatedBill.getOverdueDays());
	            existingBill.setRecurring(updatedBill.isRecurring());
	            return billRepository.save(existingBill); // Save and return the updated bill
	        } else {
	            return null; // If the bill doesn't exist, return null
	        }
	    }
	    
	    public List<Bill> getOverdueBills() {
	      // Fetch overdue bills using JPA repository
	        return billRepository.findOverdueBills();
	        }
	    
	    public List<Bill> getBillsOverview(String category, Date fromDate, Date toDate, String status) {
	        return billRepository.findBillsOverview(category, fromDate, toDate, status);
	    }
}