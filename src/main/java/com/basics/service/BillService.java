/*
 * Author: Hemalata L Chavan, Jatin Johar
 * Date: 03-Sept-2024
 * Description: The BillService class provides business logic for handling bills.
 * It includes methods for adding, updating, deleting, and retrieving bills, 
 * as well as handling specific actions like marking bills as paid or snoozing them.
 */

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
    private BillRepository billRepository; // Injecting the BillRepository to interact with the database
	
	// Get all bills
	public Iterable<Bill> getAllBills() {
        return billRepository.findAll();
    }
	
	// Add a new bill
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

    // Update an existing bill
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
	    
    // Get overdue bills
    public List<Bill> getOverdueBills() {
        return billRepository.findOverdueBills(); // Fetch overdue bills using JPA repository
    }
	    
    // Get a list of bills overview based on filters
    public List<Bill> getBillsOverview(String category, Date fromDate, Date toDate, String status) {
        return billRepository.findBillsOverview(category, fromDate, toDate, status);
    }
	
    // Get upcoming bills
    public List<Bill> getUpcomingBills() {
        return billRepository.findUpcomingBills();
    }

    // Snooze a bill by setting a new due date
    public String snoozeBill(String billId, Date snoozeDate) {
        String returnValue = "Bill not found"; // Default message if the bill is not found
        
        Date currentDate = new Date();
        if (snoozeDate.before(currentDate)) {
            returnValue = "Snooze date is earlier than the current date"; // Invalid snooze date
        } else {
            Optional<Bill> optionalBill = billRepository.findById(Long.parseLong(billId));
            if (optionalBill.isPresent()) {
                Bill searchBill = optionalBill.get();
                searchBill.setDueDate(snoozeDate); // Update the due date to the snooze date
                billRepository.save(searchBill); // Save the updated bill
                returnValue = "Bill found and snoozed"; // Success message
            }
        }
        
        return returnValue;
    }

    // Mark a bill as paid
    public boolean markBillAsPaid(String billId) {
        Optional<Bill> optionalBill = billRepository.findById(Long.parseLong(billId));
        if (optionalBill.isPresent()) {
            Bill bill = optionalBill.get();
            bill.setPaymentStatus("Paid");
            billRepository.save(bill);
            return true;
        }
        return false; // Return false if the bill is not found
    }
}
