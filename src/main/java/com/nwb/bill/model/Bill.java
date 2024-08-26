package com.nwb.bill.model;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Bill.java
 * 
 * Author: Grace Hephzibah M
 * Date: 24-Aug-2024
 * 
 * This class represents the Bill class with its attributes, constructors, getters and setters. 
 */

/*
 * Bill Category: ALL, DEBT_PAYMENTS, HOUSE_RENT, GROCERIES, INTERNET_CHARGES,RETIREMENT_CHARGES, CELL_PHONE_CHARGES
 * Bill Frequency: DAILY, WEEKLY, BIWEEKLY, MONTHLY, BIMONTHLY, QUARTERLY, SEMIANNUAL, ANNUAL; 
 * Bill Status: UPCOMING, PENDING, PAID
 */

public class Bill {
    private String billId;                     
    private String billName;                
    private String billCategory;            
    private Date dueDate;                   
    private Float amount;            
    private String reminderFrequency;       
    private String attachment;                
    private String notes;                   
    private boolean isRecurring;            
    private String paymentStatus;           
    private int overdueDays;                

 // Constructors 
    public Bill(){
    	
    }
    
 // Getters
    public int getBillId() {
        return Integer.parseInt(billId);
    }

    public String getBillName() {
        return billName;
    }

    public String getBillCategory() {
        return billCategory;
    }

    public java.sql.Date getDueDate() {
    	return (new java.sql.Date(dueDate.getTime()));
//        return dueDate;
    }

    public Float getAmount() {
        return amount;
    }

    public String getReminderFrequency() {
        return reminderFrequency;
    }

    public String getAttachment() {
        return attachment;
    }

    public String getNotes() {
        return notes;
    }

    public int isRecurring() {
    	if(isRecurring) return 1;
        return 0;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public int getOverdueDays() {
        return overdueDays;
    }

    // Setters
    public void setBillId(String billId) {
        this.billId = billId;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public void setBillCategory(String billCategory) {
        this.billCategory = billCategory;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setReminderFrequency(String reminderFrequency) {
        this.reminderFrequency = reminderFrequency;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setRecurring(boolean isRecurring) {
        this.isRecurring = isRecurring;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setOverdueDays(int overdueDays) {
        this.overdueDays = overdueDays;
    }
    
    // Additional Functions according to class diagram 
    public void addBill() {
    	
    }
    
    public void updateBill() {
    	
    }
    
    public void deleteBill() {
    	
    }
    
    // override toString override 
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        return "Bill ID: " + billId + "\n" +
               "Bill Name: " + billName + "\n" +
               "Category: " + billCategory + "\n" +
               "Due Date: " + dateFormat.format(dueDate) + "\n" +
               "Amount: " + amount + "\n" +
               "Reminder Frequency: " + reminderFrequency + "\n" +
               "Is Recurring: " + isRecurring + "\n" +
               "Payment Status: " + paymentStatus + "\n" +
               "Overdue Days: " + overdueDays + "\n" +
               "-------------";
    }

}
