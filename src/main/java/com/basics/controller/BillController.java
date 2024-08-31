package com.basics.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping("/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @GetMapping("/all")
    public Iterable<Bill> getAllBills() {
        return billService.getAllBills();
    }
    
    @GetMapping("/{id}")
    public Bill getBillById(@PathVariable Long id) {
        return billService.getBillById(id);
    }
    
    @PostMapping
    public void addNewBill(@RequestBody Bill bill) {
        billService.addNewBill(bill);
    }
    
    @PutMapping("/{id}")
    public Bill updateBill(@PathVariable Long id, @RequestBody Bill bill) {
        return billService.updateBill(id, bill);
    }
    
    @DeleteMapping("/{id}")
    public void deleteBillById(@PathVariable Long id) {
        billService.deleteBillById(id);
    }
    
    @DeleteMapping("/deleteAll")
    public void deleteAllBills() {
        billService.deleteAllBills();
    }
    
    @GetMapping("/overdue")
    public List<Bill> getOverdueBills() {
        return billService.getOverdueBills();
    }
    
    @GetMapping("/overview")
    public List<Bill> getBillsOverview(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate,
            @RequestParam(required = false) String status) {

        return billService.getBillsOverview(category, fromDate, toDate, status);
    }
     @GetMapping("/upcoming")
    public List<Bill> getUpcomingBills() {
        return billService.getUpcomingBills();
    }
      @PutMapping("/snooze/{billId}")
    public int snoozeBill(@PathVariable String billId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date snoozeDate) {
        return billService.snoozeBill(billId, snoozeDate);
    }
     @PutMapping("/{id}/markAsPaid")
    public boolean markBillAsPaid(@PathVariable String id) {
        return billService.markBillAsPaid(id);
    }
}
