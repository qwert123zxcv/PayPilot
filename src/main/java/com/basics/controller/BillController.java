/*
 * Author: Hemalata L Chavan, Jatin Johar
 * Date: 03-Sept-2024
 * Description: The BillController class manages the REST API endpoints for the Bill Management System.
 * It provides functionalities to add, update, delete, and retrieve bills, as well as to handle overdue and upcoming bills.
 */

package com.basics.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.basics.model.Bill;
import com.basics.service.BillService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/bills")
public class BillController {

    @Autowired
    private BillService billService; // Injecting BillService to handle business logic

    @GetMapping("/all")
    public Iterable<Bill> getAllBills() {
        return billService.getAllBills(); // Fetch all bills
    }
    
    @GetMapping("/{id}")
    public Bill getBillById(@PathVariable Long id) {
        return billService.getBillById(id); // Fetch a bill by its ID
    }
    
    @PostMapping
    public void addNewBill(@RequestBody Bill bill) {
        billService.addNewBill(bill); // Add a new bill
    }
    
    @PutMapping("/{id}")
    public Bill updateBill(@PathVariable Long id, @RequestBody Bill bill) {
        return billService.updateBill(id, bill); // Update an existing bill by its ID
    }
    
    @DeleteMapping("/{id}")
    public void deleteBillById(@PathVariable Long id) {
        billService.deleteBillById(id); // Delete a bill by its ID
    }
    
    @DeleteMapping("/deleteAll")
    public void deleteAllBills() {
        billService.deleteAllBills(); // Delete all bills
    }
    
    @GetMapping("/overdue")
    public List<Bill> getOverdueBills() {
        return billService.getOverdueBills(); // Fetch all overdue bills
    }
    
    @GetMapping("/overview")
    public List<Bill> getBillsOverview(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate,
            @RequestParam(required = false) String status) {

        return billService.getBillsOverview(category, fromDate, toDate, status); // Fetch bills overview based on filters
    }
    
    @GetMapping("/upcoming")
    public List<Bill> getUpcomingBills() {
        return billService.getUpcomingBills(); // Fetch all upcoming bills
    }
    
    @PutMapping("/snooze/{billId}")
    public String snoozeBill(@PathVariable String billId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date snoozeDate) {
        return billService.snoozeBill(billId, snoozeDate); // Snooze a bill to a new date
    }

    @PutMapping("/{id}/markAsPaid")
    public boolean markBillAsPaid(@PathVariable Long id) {
        return billService.markBillAsPaid(id.toString()); // Mark a bill as paid
    }
}
