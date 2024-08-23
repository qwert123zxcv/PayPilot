package com.nwb.bill.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.nwb.bill.model.Bill;

public class BillInputSupport {
    private static Scanner s = new Scanner(System.in);
//    private static int billId = 0;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    Bill inputNewBill() {
        // Creating a Bill Object 
        Bill newBill = new Bill();

        // Setting the Bill
        newBill.setBillName(getBillName());
        newBill.setBillCategory(getBillCategory());
        Date dueDate = getBillDate("Enter due date (dd-mm-yyyy)");
        newBill.setDueDate(dueDate);
        newBill.setAmount(getAmount());
        newBill.setReminderFrequency(getReminderFrequency());
        newBill.setAttachment(getAttachment());
        newBill.setNotes(getNotes());
        newBill.setRecurring(isRecurring());
        newBill.setPaymentStatus(getPaymentStatus());
        newBill.setBillId(newBill.getBillId());

        // Calculate overdue days if applicable
        Date todaysDate = new Date();
        if (todaysDate.compareTo(dueDate) > 0) {
            long diffInMillis = Math.abs(todaysDate.getTime() - dueDate.getTime());
            long diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
            newBill.setOverdueDays((int) diffInDays);
        } else {
            newBill.setOverdueDays(0);
        }
        // Returning bill
        return newBill;
    }

    String getBillId() {
    	System.out.println("\nEnter Bill Id:");
    	return s.nextLine();
    }
    
    String getBillName() {
        System.out.println("Enter bill name");
        return s.nextLine();
    }

    String getBillCategory() {
        System.out.println("Enter bill category");
        return s.nextLine();
    }

    double getAmount() {
        System.out.println("Enter amount");
        return Double.parseDouble(s.nextLine().trim());
    }

    String getReminderFrequency() {
        System.out.println("Enter reminder frequency (weekly/monthly/yearly)");
        return s.nextLine();
    }

    String getAttachment() {
        System.out.println("Upload attachment");
        File attachment = new File("C:\\Users\\bhatt\\Downloads\\Documents\\PayPilot");
        if (!attachment.exists()) {
            return "yes";
        }
        return "no";
    }

    String getNotes() {
        System.out.println("Do you want to add any notes? (y/n)");
        char notesResponse = s.nextLine().charAt(0);
        notesResponse = Character.toLowerCase(notesResponse);
        if (notesResponse == 'y') {
            System.out.print("Write notes: ");
            return s.nextLine();
        }
        return "";
    }

    boolean isRecurring() {
        System.out.println("Do you want to toggle recurring bill? (y/n)");
        char recBill = s.nextLine().charAt(0);
        recBill = Character.toLowerCase(recBill);
        return recBill == 'y';
    }

    String getPaymentStatus() {
        System.out.print("Payment Status (Pending/Upcoming/Paid): ");
        return s.nextLine();
    }
    
    static Date getBillDate(String prompt) { 
        System.out.print(prompt);
        Date date = null;
        boolean flag = true;
        do {
	        try {
	            date = dateFormat.parse(s.nextLine().trim());
	            flag = false;
	        } catch (ParseException e) {
	            System.out.println("Invalid date format. Please enter the date in dd-MM-yyyy format.");
	        }
       } while (flag);
        return date;
    }
}
