package com.nwb.bill.controller;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.nwb.bill.model.Bill;
import com.nwb.bill.repo.BillManager;
import com.nwb.bill.service.BillManagerService;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BillController {
    private static Scanner s = new Scanner(System.in);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static BillManagerService billManagerService = new BillManagerService();
    private static BillInputSupport bis = new BillInputSupport();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\nBill Management System");
            System.out.println("1. Add New Bill");
            System.out.println("2. View Bills Overview");
            System.out.println("3. View Overdue Bills");
            System.out.println("4. View Upcoming Bills");
            System.out.println("5. Snooze a Bill");
            System.out.println("6. Mark Bill as Paid");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = s.nextInt();
            s.nextLine();

            switch (choice) {
                case 1:
                     executeAddNewBill();
                    break;
                case 2:
                    executeGetBillsOverview();
                    break;
                case 3:
                     executeViewOverdueBills();
                    break;
                case 4:
                    executeViewUpcomingBills();
                    break;
                case 5:
                    executeSnoozeBill();
                    break;
                case 6:
                    executeMarkBillAsPaid();
                    break;                
                case 7:
                    System.out.println("Exiting the application");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void executeAddNewBill() 
    {
    	System.out.println("\nNew Bill Information:");
    	Bill newBill = bis.inputNewBill();
    	
        System.out.println("Do you want to save?(y/n)");
        char save_response = s.nextLine().charAt(0);
        save_response = Character.toLowerCase(save_response);
        
        if (save_response == 'y') {
            billManagerService.addNewBill(newBill);
            System.out.println("Bill saved successfully!");
        } else {
            System.out.println("Bill not saved.");
        }
    }
    
    private static void executeViewOverdueBills(){
        System.out.println("\nView Overdue Bills:");
        List<Bill> overdueBills = billManagerService.getOverdueBills();
        displayBills(overdueBills);
    }
    
    private static void executeGetBillsOverview() {
    	  System.out.println("\nView Bills Overview:");
    	  String category = bis.getBillCategory();
    	  Date fromDate = BillInputSupport.getBillDate("Bill Date From (dd-MM-yyyy): ");
    	  Date toDate = BillInputSupport.getBillDate("Bill Date To (dd-MM-yyyy): ");
    	  String status = bis.getPaymentStatus();

          List<Bill> bills = billManagerService.getBillsOverview(category, fromDate, toDate, status);
          displayBills(bills);
    }
    
    
    private static void executeViewUpcomingBills() {
    	System.out.println("\nView Upcoming Bills:");
        List<Bill> upcomingBills = billManagerService.getUpcomingBills();
        displayBills(upcomingBills);
    }
    
    private static void executeSnoozeBill() {
    	System.out.println("\nSnooze Bill:");
    	int billId = bis.getBillId();
    	Date snoozeDate = BillInputSupport.getBillDate("Bill Date From (dd-MM-yyyy): ");
    	boolean isExistingBill = billManagerService.snoozeBill(billId, snoozeDate);
    	if (!isExistingBill) {
    		System.out.println("Bill does not exist");
    	}
    	else {
    		System.out.println("Bill snoozed");
    	}
    }
    
    private static void executeMarkBillAsPaid() {
    	System.out.println("\nMark Bill As Bill:");
    	int billId = bis.getBillId();
  
    	boolean isExistingBill = billManagerService.markBillAsPaid(billId);
    	if (!isExistingBill) {
    		System.out.println("Bill does not exist");
    	}
    	else {
    		System.out.println("Bill marked as paid");
    	}
    }
    
    private static void displayBills(List<Bill> bills) {
        if (bills.isEmpty()) {
            System.out.println("No bills found.");
        } else {
            System.out.println("\nBill Details:");
            for (Bill bill : bills) {
                System.out.println(bill);
            }
        }
    } 
}