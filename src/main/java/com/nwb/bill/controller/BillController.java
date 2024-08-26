package com.nwb.bill.controller;
import java.util.*;

import com.nwb.bill.model.Bill;
import com.nwb.bill.service.BillManagerService;

/**
 * Author:  Grace Hephzibah M
 * Date: 24-Aug-2024
 * BillController class is responsible for managing user interactions with the Bill Management System.
 * It provides functionalities to add new bills, view bills overview, view overdue and upcoming bills,
 * snooze bills, and mark bills as paid.
 */

public class BillController {
    private static Scanner s = new Scanner(System.in);
    private static BillManagerService billManagerService = new BillManagerService();
    private static BillInputSupport bis = new BillInputSupport();

    /**
     * The main method is the entry point of the Bill Management System application.
     * It displays a menu and processes user input to perform various bill management operations.
     *
     * @param args Command line arguments (not used).
     */
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
    
    /**
     * Executes the process of adding a new bill. 
     * It prompts the user for bill details, confirms if they want to save the bill, 
     * and then saves the bill if confirmed.
     */
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
    
    /**
     * Executes the process of viewing overdue bills.
     * It retrieves and displays a list of bills that are overdue.
     */
    private static void executeViewOverdueBills(){
        System.out.println("\nView Overdue Bills:");
        List<Bill> overdueBills = billManagerService.getOverdueBills();
        displayBills(overdueBills);
    }
    
    /**
     * Executes the process of viewing a summary of bills based on user-specified criteria.
     * It prompts the user for a bill category, date range, and payment status, 
     * and then retrieves and displays matching bills.
     */
    private static void executeGetBillsOverview() {
    	  System.out.println("\nView Bills Overview:");
    	  String category = bis.getBillCategory();
    	  Date fromDate = BillInputSupport.getBillDate("Bill Date From (dd-MM-yyyy): ");
    	  Date toDate = BillInputSupport.getBillDate("Bill Date To (dd-MM-yyyy): ");
    	  String status = bis.getPaymentStatus();

          List<Bill> bills = billManagerService.getBillsOverview(category, fromDate, toDate, status);
          displayBills(bills);
    }
    
    /**
     * Executes the process of viewing upcoming bills.
     * It retrieves and displays a list of bills that are due soon.
     */
    private static void executeViewUpcomingBills() {
    	System.out.println("\nView Upcoming Bills:");
        List<Bill> upcomingBills = billManagerService.getUpcomingBills();
        displayBills(upcomingBills);
    }
    
    /**
     * Executes the process of snoozing a bill.
     * It prompts the user for a bill ID and a new snooze date, 
     * then updates the bill's due date if valid.
     */
    private static void executeSnoozeBill() {
    	System.out.println("\nSnooze Bill:");
    	String billId = bis.getBillId();
    	Date snoozeDate = BillInputSupport.getBillDate("Snooze Date (dd-MM-yyyy): ");
    	int executeSnoozeValue = billManagerService.snoozeBill(billId, snoozeDate);
    	if (executeSnoozeValue == 1) {
    		System.out.println("Bill snoozed");
    	}
    	else if (executeSnoozeValue == 2) {
    		System.out.println("Snooze date should be greater than today's date");
    	}
    	else {
    		System.out.println("Bill not found");
    	}
    }
    
    /**
     * Executes the process of marking a bill as paid.
     * It prompts the user for a bill ID and updates the bill's status to paid if it exists.
     */
    private static void executeMarkBillAsPaid() {
    	System.out.println("\nMark Bill As Bill:");
    	String billId = bis.getBillId();
  
    	boolean isExistingBill = billManagerService.markBillAsPaid(billId);
    	if (!isExistingBill) {
    		System.out.println("Bill does not exist");
    	}
    	else {
    		System.out.println("Bill marked as paid");
    	}
    }
    
    /**
     * Displays a list of bills. If no bills are found, it notifies the user.
     *
     * @param bills List of bills to be displayed.
     */
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