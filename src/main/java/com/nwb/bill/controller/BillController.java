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
       
        System.out.println("Enter Bill ID: ");
        int billId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter Bill Name: ");
        String billName = scanner.nextLine();

        System.out.println("Enter Bill Category ( ALL, DEBT_PAYMENTS, HOUSE_RENT, etc.): ");
        String billCategory = scanner.nextLine();

       /* System.out.println("Enter Due Date (yyyy-mm-dd): ");
        String dueDateStr = scanner.nextLine();// change accordingly 
        Date dueDate = new Date(dueDateStr);*/ // SimpleDateFormat would be better for real applications
        System.out.print("Due Date (dd-MM-yyyy): ");
        Date dueDate = null;
        try {
            dueDate = dateFormat.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please try again.");
            return;
        }

        System.out.println("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter Reminder Frequency (e.g., DAILY, WEEKLY, MONTHLY, etc.): ");
        String reminderFrequency = scanner.nextLine();

        System.out.println("Is this bill recurring? (true/false): ");
        boolean isRecurring = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter Payment Status (e.g., UPCOMING, PENDING, PAID): ");
        String paymentStatus = scanner.nextLine();

        System.out.println("Enter Overdue Days: ");
        int overdueDays = scanner.nextInt();

        // Create and set the Bill object
        Bill bill = new Bill();
        bill.setBillId(billId);
        bill.setBillName(billName);
        bill.setBillCategory(billCategory);
       bill.setDueDate(dueDate);
        bill.setAmount(amount);
        bill.setReminderFrequency(reminderFrequency);
        bill.setRecurring(isRecurring);
        bill.setPaymentStatus(paymentStatus);
        bill.setOverdueDays(overdueDays);
        billManager.addNewBill(bill);
        System.out.println("Bill has been created ");
	}

    private static void executeViewOverdueBills() {
		// TODO Auto-generated method stub
    	System.out.println("\nView Overdue Bills:");
    	 List<Bill> overdueBills = billManager.getOverdueBills();
    	 displayBills(overdueBills);
		
	}

	

	// Placeholder methods for the different functionalities
    private static void executeGetBillsOverview() {
        System.out.println("\nView Bills Overview:");
        System.out.print("Bill Category (All, Debt Payments, House Rent, etc.): ");
        String category = scanner.nextLine();

        System.out.print("Bill Date From (dd-MM-yyyy): ");
        Date fromDate = null;
        try {
            fromDate = dateFormat.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please try again.");
            return;
        }

        System.out.print("Bill Date To (dd-MM-yyyy): ");
        Date toDate = null;
        try {
            toDate = dateFormat.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please try again.");
            return;
        }

        System.out.print("Bill Status (Upcoming/Pending/Paid): ");
        String status = scanner.nextLine();

        List<Bill> bills = billManager.getBillsOverview(category, fromDate, toDate, status);
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
