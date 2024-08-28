
/*
 * Bill Category: ALL, DEBT_PAYMENTS, HOUSE_RENT, GROCERIES, INTERNET_CHARGES,RETIREMENT_CHARGES, CELL_PHONE_CHARGES
 * Bill Frequency: DAILY, WEEKLY, BIWEEKLY, MONTHLY, BIMONTHLY, QUARTERLY, SEMIANNUAL, ANNUAL; 
 * Bill Status: UPCOMING, PENDING, PAID
 */

package com.basics.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

@Entity
@Table(name = "bills") 
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key, auto-generated

    private String billName;
    private String billCategory;

    @Temporal(TemporalType.DATE)
    private Date dueDate;

    private Float amount;
    private String reminderFrequency;
    private String attachment;
    private String notes;
    private boolean isRecurring;
    private String paymentStatus;
    private int overdueDays;

    // Constructors
    public Bill() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getBillCategory() {
        return billCategory;
    }

    public void setBillCategory(String billCategory) {
        this.billCategory = billCategory;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getReminderFrequency() {
        return reminderFrequency;
    }

    public void setReminderFrequency(String reminderFrequency) {
        this.reminderFrequency = reminderFrequency;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean isRecurring) {
        this.isRecurring = isRecurring;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(int overdueDays) {
        this.overdueDays = overdueDays;
    }

    @Override
    public String toString() {
        return "Bill ID: " + id + "\n" +
               "Bill Name: " + billName + "\n" +
               "Category: " + billCategory + "\n" +
               "Due Date: " + dueDate + "\n" +
               "Amount: " + amount + "\n" +
               "Reminder Frequency: " + reminderFrequency + "\n" +
               "Is Recurring: " + isRecurring + "\n" +
               "Payment Status: " + paymentStatus + "\n" +
               "Overdue Days: " + overdueDays + "\n" +
               "-------------";
    }
 // Additional Functions according to class diagram 
    public void addBill() {
    	
    }
    
    public void updateBill() {
    	
    }
    
    public void deleteBill() {
    	
    }
}

    
    
    
