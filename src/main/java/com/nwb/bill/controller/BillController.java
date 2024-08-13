package com.nwb.bill.controller;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.nwb.bill.manager.BillManager;
import com.nwb.bill.model.Bill;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BillController {
    private static Scanner scanner = new Scanner(System.in);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static BillManager billManager;
    private static int billId = 0;

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
    private static void executeAddNewBill() 
    {
    	Scanner s= new Scanner(System.in); 
	
     
        System.out.println("Enter bill name");
        String bill_name = s.nextLine();
        System.out.println("Enter bill category");
        String bill_category = s.nextLine();
        System.out.println("Enter due date(dd-mm-yyyy)");
        boolean flag = true;
        Date due_date = null;
        do {
            String dateInput = s.nextLine();
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                due_date = dateFormat.parse(dateInput);
                flag = false;
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter the date in dd-mm-yyyy format.");
            }
        } while (flag);
        Date todays_date = new Date();

        System.out.println("Enter amount");
        double amount = Double.parseDouble(s.nextLine().trim());
        System.out.println("Enter reminder frequency(weekly/monthly/yearly)");
        String reminder_frequency = s.nextLine();
        System.out.println("Upload attachment");
        File attachment = new File("C:\\Users\\bhatt\\Downloads\\Documents\\PayPilot");
        if (!attachment.exists()) {
            System.out.println("Invalid file path");
        }
        System.out.println("Do you want to add any notes?(y/n)");
        char notes_response = s.nextLine().charAt(0);
        notes_response = Character.toLowerCase(notes_response);
        String notes = "";
        if (notes_response == 'y') {
            System.out.print("Write notes: ");
            notes = s.nextLine();
        }
        System.out.println("Do you want to toggle recurring bill(y/n)");
        char rec_bill = s.nextLine().charAt(0);
        rec_bill = Character.toLowerCase(rec_bill);
        System.out.print("Payment Status (Pending/Upcoming/Paid): ");
        String paymentStatus = s.nextLine();
        System.out.println("Do you want to save?(y/n)");
        char save_response = s.nextLine().charAt(0);
        save_response = Character.toLowerCase(save_response);
        if (save_response == 'y') {
            Bill newBill = new Bill();
            newBill.setBillName(bill_name);
            newBill.setBillCategory(bill_category);
            newBill.setDueDate(due_date);
            newBill.setAmount(amount);
            newBill.setReminderFrequency(reminder_frequency);
            newBill.setAttachment(attachment);
            newBill.setNotes(notes);
            newBill.setPaymentStatus(paymentStatus);
            if (rec_bill == 'y') {
                newBill.setRecurring(true);
            } else {
                newBill.setRecurring(false);
            }
            newBill.setBillId(++billId);
            if (todays_date.compareTo(due_date) > 0) {
                long diffInMillis = Math.abs(todays_date.getTime() - due_date.getTime());
                long diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
                newBill.setOverdueDays((int) diffInDays);
            } else {
                newBill.setOverdueDays(0);
            }
            billManager.addNewBill(newBill);
            System.out.println("Bill saved successfully!");
        } else {
            System.out.println("Bill not saved.");
        }
    }

    private static void executeViewOverdueBills() {
        System.out.println("\nView Overdue Bills:");
        List<Bill> overdueBills = billManager.getOverdueBills(billManager.getBillsOverview("ALL", new Date(Long.MIN_VALUE), new Date(), "pending"));
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