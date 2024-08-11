package com.nwb.bill.controller;
import java.util.*;

import com.nwb.bill.manager.BillManager;
import com.nwb.bill.model.Bill;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BillController {
    private static Scanner scanner = new Scanner(System.in);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static BillManager billManager;

    public static void main(String[] args) {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.println("You Have Successfully Logged in" + userId);
        billManager = new BillManager(userId); // Passing the userId to the constructor

        while (true) {
            System.out.println("\nBill Management System");
            System.out.println("1. Add New Bill");
            System.out.println("2. View Bills Overview");
            System.out.println("3. View Overdue Bills");
            //System.out.println("4. View Upcoming Bills");
           // System.out.println("5. Snooze a Bill");
            //System.out.println("6. Mark Bill as Paid");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

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
              /*  case 4:
                    viewUpcomingBills();
                    break;
                case 5:
                    snoozeBill();
                    break;
                case 6:
                    markBillAsPaid();
                    break; */                
                    case 7:
                    System.out.println("Exiting the application");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void executeAddNewBill() {
		
    	 System.out.println("\nAdd New Bill:");
    	    Bill newBill = new Bill();
    	    billManager.addNewBill(newBill);
	}

    private static void executeViewOverdueBills() {
        System.out.println("\nView Overdue Bills:");
        List<Bill> overdueBills = billManager.getOverdueBills(billManager.getBillsOverview("ALL", new Date(Long.MIN_VALUE), new Date(), "pending"));
        displayBills(overdueBills);
    }

	

	// Placeholder methods for the different functionalities
    private static void executeGetBillsOverview() {
    	List<Bill> bills = billManager.getFilteredBillsOverview();
        displayBills(bills);
    }
    
    
    
    private static void displayBills(List<Bill> bills) {
        if (bills.isEmpty()) {
            System.out.println("No bills found.");
        } else {
            System.out.println("\nBill Details:");
            for (Bill bill : bills) {
                System.out.println("Bill ID: " + bill.getBillId());
                System.out.println("Bill Name: " + bill.getBillName());
                System.out.println("Category: " + bill.getBillCategory());
                System.out.println("Due Date: " + dateFormat.format(bill.getDueDate()));
                System.out.println("Amount: " + bill.getAmount());
                System.out.println("Reminder Frequency: " + bill.getReminderFrequency());
               System.out.println("Is Recurring: " + bill.isRecurring());
                System.out.println("Payment Status: " + bill.getPaymentStatus());
                System.out.println("Overdue Days: " + bill.getOverdueDays());
                System.out.println("-------------");
            }
        }
    }
}