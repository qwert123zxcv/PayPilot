package com.basics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    
    @PostMapping
    public void addNewBill(@RequestBody Bill bill) {
        billService.addNewBill(bill);
    }
}
