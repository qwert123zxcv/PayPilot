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
        newBill.setBillId(getBillId());
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
        newBill.setBillId(newBill.getBillId()+"");

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
        int billId=0;
        boolean flag=true;
        do {
        	System.out.println("\nEnter Bill Id(integer values only):");
        	try {
        		billId=Integer.parseInt(s.nextLine());
        		flag=false;
        	}catch(Exception e){
        		System.out.println("Wrong Input. Please try again.");
        	}
        }while(flag);
    	return billId+"";
    }
    
    String getBillName() {
        System.out.println("Enter bill name");
        return s.nextLine();
    }

    String getBillCategory() {
        String category=null;
        boolean flag=true;
        do {
        	System.out.println("Enter bill category('House Rent', 'Debt Payments', 'Groceries', 'Internet Charges', 'Cellphone Charges')");
        	category=s.nextLine().trim().toLowerCase();
        	if(category.equals("house rent") || category.equals("debt payments") || category.equals("groceries") || 
        			category.equals("internet charges") || category.equals("cellphone charges")) {
        		flag=false;
        	}else {
        		System.out.println("Wrong Input. Please try again.");
        	}
        }while(flag);
        return category;
    }

    Float getAmount() {
        System.out.println("Enter amount");
        return Float.parseFloat(s.nextLine().trim());
    }

    String getReminderFrequency() {
    	String remFreq=null;
        boolean flag=true;
        do {
        	System.out.println("Enter reminder frequency (weekly/monthly/yearly)");
        	remFreq=s.nextLine().trim().toLowerCase();
        	if(remFreq.equals("weekly") || remFreq.equals("monthly") || remFreq.equals("yearly")) {
        		flag=false;
        	}else {
        		System.out.println("Wrong Input. Please try again.");
        	}
        }while(flag);
        return remFreq;
    }

    String getAttachment() {
        String attachment=null;
        boolean flag=true;
        do {
        	System.out.println("Do you have attachment(yes/no)");
        	attachment=s.nextLine().trim().toLowerCase();
        	if(attachment.equals("yes") || attachment.equals("no")) {
        		flag=false;
        	}else {
        		System.out.println("Wrong Input. Please try again.");
        	}
        }while(flag);
        return attachment;
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
    	String paymentStatus=null;
        boolean flag=true;
        do {
        	System.out.print("Payment Status (Pending/Upcoming/Paid): ");
        	paymentStatus=s.nextLine().trim().toLowerCase();
        	if(paymentStatus.equals("pending") || paymentStatus.equals("upcoming") || paymentStatus.equals("paid")) {
        		flag=false;
        	}else {
        		System.out.println("Wrong Input. Please try again.");
        	}
        }while(flag);
        return paymentStatus;
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
