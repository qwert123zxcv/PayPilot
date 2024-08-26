package com.nwb.bill.controller;

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

/**
 * Prompts the user to input details for a new bill and creates a Bill object with those details.
 * 
 * This method collects various attributes required to create a new bill, such as bill ID, name,
 * category, due date, amount, reminder frequency, attachment, notes, recurring status, and payment status.
 * It calculates the number of overdue days based on the due date and the current date, if applicable.
 * 
 * @return A Bill object populated with the details provided by the user and calculated overdue days.
 */
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

/**
 * Prompts the user to enter a bill ID and validates the input to ensure it is an integer.
 * 
 * This method repeatedly asks the user to enter a bill ID until a valid integer is provided.
 * It catches and handles any exceptions related to invalid input, ensuring that the user
 * is prompted to try again if the input is not an integer.
 * 
 * @return The entered bill ID as a String.
 */
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

/**
 * Prompts the user to enter the name of the bill.
 * 
 * This method displays a prompt asking the user to input the bill name and then
 * reads the user's input from the console. The entered name is returned as a String.
 * 
 * @return The entered bill name as a String.
 */
    String getBillName() {
        System.out.println("Enter bill name");
        return s.nextLine();
    }

/**
 * Prompts the user to enter a valid bill category from a predefined list of options.
 * 
 * This method repeatedly prompts the user to input a bill category until a valid category
 * is provided. It accepts categories in a case-insensitive manner and ensures the input
 * matches one of the specified valid categories.
 * 
 * @return The valid bill category entered by the user as a String.
 */
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

/**
 * Prompts the user to enter the amount for the bill.
 * 
 * This method reads the user input, trims any extra whitespace, and converts it
 * from a String to a Float. It does not perform validation for the format of the
 * amount or handle potential parsing errors. The method assumes that the input will
 * be a valid numeric value.
 * 
 * @return The amount entered by the user as a Float.
 */
    Float getAmount() {
        System.out.println("Enter amount");
        return Float.parseFloat(s.nextLine().trim());
    }

/**
 * Prompts the user to enter the reminder frequency for the bill.
 * 
 * This method repeatedly prompts the user to enter the reminder frequency, ensuring
 * that the input matches one of the accepted values: "weekly", "monthly", or "yearly".
 * The input is converted to lowercase to make the comparison case-insensitive.
 * If the user provides an invalid input, they are prompted again until a valid
 * frequency is entered.
 * 
 * @return The reminder frequency entered by the user as a String.
 */
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

/**
 * Prompts the user to specify whether an attachment is available for the bill.
 * 
 * This method repeatedly asks the user if they have an attachment by providing the options
 * "yes" or "no". The input is converted to lowercase to ensure case-insensitivity. 
 * If the user inputs something other than "yes" or "no", they are prompted again until 
 * a valid response is given.
 * 
 * @return The user's response regarding the attachment availability as a String.
 */
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

/**
 * Prompts the user to determine if they want to add any notes to the bill.
 * 
 * This method first asks the user if they wish to include notes by entering 'y' (yes) or 'n' (no). 
 * It then reads the user's response, which is converted to lowercase to handle different cases. 
 * If the user chooses 'y', they are prompted to enter their notes, which are then captured and returned.
 * If the user chooses 'n', an empty string is returned, indicating no notes are provided.
 * 
 * @return The notes provided by the user as a String. If no notes are provided, an empty string is returned.
 */
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

/**
 * Prompts the user to specify if the bill should be set as recurring.
 * 
 * This method asks the user if they want to set the bill as recurring by entering 'y' (yes) or 'n' (no). 
 * It reads the user's response, converts it to lowercase to handle different cases, 
 * and returns `true` if the user enters 'y', indicating that the bill should be recurring. 
 * If the user enters 'n', the method returns `false`, indicating that the bill should not be recurring.
 * 
 * @return A boolean value: `true` if the bill is recurring, `false` otherwise.
 */
    boolean isRecurring() {
        System.out.println("Do you want to toggle recurring bill? (y/n)");
        char recBill = s.nextLine().charAt(0);
        recBill = Character.toLowerCase(recBill);
        return recBill == 'y';
    }

/**
 * Prompts the user to enter the payment status of a bill.
 * 
 * This method asks the user to specify the payment status of the bill by entering one of the following options: 
 * "Pending", "Upcoming", or "Paid". It reads the user's input, converts it to lowercase to handle different cases, 
 * and checks if the input matches one of the valid status options. If the input is valid, the method returns the 
 * status; otherwise, it prompts the user to try again until a valid status is entered.
 * 
 * @return The payment status as a string: "pending", "upcoming", or "paid".
 */
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

/**
 * Prompts the user to enter a date and returns it as a {@link Date} object.
 * 
 * This method displays a prompt message to the user, requesting them to enter a date. It expects the date to be in 
 * the "dd-MM-yyyy" format. The method continuously prompts the user until a valid date is entered. It uses 
 * {@link SimpleDateFormat} to parse the date string and handle any formatting issues. If the input does not match 
 * the expected format, it catches the {@link ParseException} and informs the user to try again.
 * 
 * @param prompt The message to display to the user when requesting the date.
 * @return The date entered by the user as a {@link Date} object.
 */
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
