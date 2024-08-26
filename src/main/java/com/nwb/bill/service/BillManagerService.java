package com.nwb.bill.service;

import java.sql.*;
import java.util.*;
import java.util.Date;

import com.nwb.bill.connection.DBConnection;
import com.nwb.bill.model.Bill;
import com.nwb.bill.repo.BillManager;

/**
 * BillManagerService.java
 * 
 * Author: Harshit Bhatt, Grace Hephzibah
 * Date: 24-Aug-2024
 * 
 * This class represents the service layer for managing bills in the Bill Management System. 
 * It provides various methods to interact with the bill data, such as adding a new bill, 
 * retrieving overdue bills, getting an overview of bills based on specific criteria, snoozing a bill, 
 * and marking a bill as paid. The service uses the BillManager class to perform these operations.
 */
public class BillManagerService {
    private static BillManager billManager = new BillManager();

    public BillManagerService(){
        
    }
    
    /**
     * Adds a new bill to the system.
     * 
     * This method uses the BillManager to add the provided Bill object to the system.
     * 
     * @param bill The Bill object to be added.
     */
    public void addNewBill(Bill bill) {
        billManager.addNewBill(bill);
    }
    
    /**
     * Retrieves a list of overdue bills.
     * 
     * This method uses the BillManager to get a list of bills that are overdue.
     * 
     * @return A list of overdue Bill objects.
     */
    public List<Bill> getOverdueBills() {
        List<Bill> overdueBills = billManager.getOverdueBills();
        return overdueBills;
    }

    /**
     * Retrieves an overview of bills based on specified criteria.
     * 
     * This method retrieves bills based on category, date range, and payment status.
     * It uses the BillManager to fetch the list of bills that match the given criteria.
     * 
     * @param category The category of the bills to retrieve.
     * @param fromDate The start date of the date range.
     * @param toDate The end date of the date range.
     * @param status The payment status of the bills to retrieve.
     * @return A list of Bill objects matching the specified criteria.
     */
    public List<Bill> getBillsOverview(String category, Date fromDate, Date toDate, String status) {
        List<Bill> billOverview = billManager.getBillsOverview(category, fromDate, toDate, status);
        return billOverview;
    }

    /**
     * Retrieves a list of upcoming bills.
     * 
     * This method uses the BillManager to get a list of bills that are upcoming.
     * 
     * @return A list of upcoming Bill objects.
     */
    public List<Bill> getUpcomingBills() {
        List<Bill> upcomingBills = billManager.getUpcomingBills();
        return upcomingBills;
    }

    /**
     * Snoozes a bill to a later date.
     * 
     * This method attempts to snooze the bill identified by the provided bill ID to a new snooze date. 
     * It checks if the snooze date is valid (i.e., it is not before the current date) and if the bill exists.
     * 
     * @param billId The ID of the bill to snooze.
     * @param snoozeDate The new date to which the bill should be snoozed.
     * @return An integer code indicating the result of the snooze operation:
     *         1 if the bill was found and snoozed successfully,
     *         2 if the snooze date is before the current date,
     *         3 if the bill was not found.
     */
    public int snoozeBill(String billId, Date snoozeDate) {
        int returnValue = 3;
        
        Date currentDate = new Date();
        if (snoozeDate.before(currentDate)) {
            returnValue = 2;
        }
        else {
            Bill searchBill = searchBillWithId(billId);
            
            if (searchBill != null) {
                billManager.snoozeBill(searchBill, snoozeDate);
                return 1;
            }
        }
        
        return returnValue;
        // 1 -> Bill found 
        // 2 -> snooze date is less than current date
        // 3 -> bill not found 
    }
    
    /**
     * Marks a bill as paid.
     * 
     * This method marks the bill identified by the provided bill ID as paid. 
     * It checks if the bill exists before updating its status.
     * 
     * @param billId The ID of the bill to mark as paid.
     * @return true if the bill was found and marked as paid, false otherwise.
     */
    public boolean markBillAsPaid(String billId) {
        Bill searchBill = searchBillWithId(billId);
        if (searchBill != null) {
            billManager.markBillAsPaid(searchBill);
            return true;
        }
        return false;
    }
    
    /**
     * Searches for a bill by its ID.
     * 
     * This method queries the database for a bill with the specified ID. If found, it creates and returns a Bill 
     * object populated with the details retrieved from the database.
     * 
     * @param billId The ID of the bill to search for.
     * @return The Bill object with the specified ID, or null if no bill was found.
     */
    public Bill searchBillWithId(String billId) {
        Bill searchBill = null;
        String sql = "SELECT * FROM bills WHERE bill_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {    
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, billId);
            
            try {
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    searchBill = new Bill();
                    searchBill.setBillId(rs.getString("bill_id"));
                    searchBill.setBillName(rs.getString("bill_name"));
                    searchBill.setBillCategory(rs.getString("bill_category"));
                    searchBill.setDueDate(rs.getDate("due_date"));
                    searchBill.setAmount(rs.getFloat("amount"));
                    searchBill.setReminderFrequency(rs.getString("reminder_frequency"));
                    searchBill.setAttachment(rs.getString("attachment"));
                    searchBill.setNotes(rs.getString("notes"));
                    searchBill.setRecurring(rs.getInt("is_recurring") == 1);
                    searchBill.setPaymentStatus(rs.getString("payment_status"));
                    searchBill.setOverdueDays(rs.getInt("overdue_days"));
                }   
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchBill;
    }
}
