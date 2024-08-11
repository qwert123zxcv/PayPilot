
package com.nwb.bill.manager;
import com.nwb.bill.model.Bill;


import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class BillManager {
    private static int billId = 0;
    private String userId;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private List<Bill> bills;
    Scanner s = new Scanner(System.in);

    public BillManager(String userId) {
        this.userId = userId;
        this.bills = new ArrayList<>();
    }

    public List<Bill> getBillsOverview(String category, Date fromDate, Date toDate, String status) {
        List<Bill> filteredBills = new ArrayList<>();
        for (Bill bill : bills) {
            // Check for null dueDate
            if (bill.getDueDate() == null) {
                continue; // Skip this bill if dueDate is null
            }

            boolean matchesCategory = category.equalsIgnoreCase("ALL") || bill.getBillCategory().equalsIgnoreCase(category);
            boolean matchesFromDate = !bill.getDueDate().before(fromDate);
            boolean matchesToDate = !bill.getDueDate().after(toDate);
            boolean matchesStatus = bill.getPaymentStatus().equalsIgnoreCase(status);

            if (matchesCategory && matchesFromDate && matchesToDate && matchesStatus) {
                filteredBills.add(bill);
            }
        }
        return filteredBills;
    }

    public List<Bill> getFilteredBillsOverview() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nView Bills Overview:");
        System.out.print("Bill Category (All, Debt Payments, House Rent, etc.): ");
        String category = scanner.nextLine();

        System.out.print("Bill Date From (dd-MM-yyyy): ");
        Date fromDate = null;
        try {
            fromDate = dateFormat.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please try again.");
            return Collections.emptyList();
        }

        System.out.print("Bill Date To (dd-MM-yyyy): ");
        Date toDate = null;
        try {
            toDate = dateFormat.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please try again.");
            return Collections.emptyList();
        }

        System.out.print("Bill Status (Upcoming/Pending/Paid): ");
        String status = scanner.nextLine();

        return getBillsOverview(category, fromDate, toDate, status);
    }

    public void addNewBill(Bill bill) {
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
            bills.add(newBill);
            System.out.println("Bill saved successfully!!");
        } else {
            System.out.println("Bill not saved.");
        }
    }

   


	public List<Bill> getOverdueBills(List<Bill> bills) {
		//Must add some code here
		List<Bill> overdueBills=new ArrayList<>();
		System.out.println("Select if you\n1. Want to filter out or\n2. You want "+
				"to enter the details manually");
		switch(Integer.parseInt(s.nextLine().trim())) {
			case 1:
				System.out.println("Select one of the two options:\n"+
						"1. By date range\n2. By bill category");
				switch(Integer.parseInt(s.nextLine().trim())) {
					case 1:
						boolean flag=true;
						Date date_start=null;
						Date date_end=null;
						do {
							System.out.println("Enter start date(dd-mm-yyyy)");
							String start_date=s.nextLine();
							System.out.println("Enter end date(dd-mm-yyyy)");
							String end_date=s.nextLine();
							SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
							try {
								date_start=sdf.parse(start_date);
								date_end=sdf.parse(end_date);
								flag=false;
							}catch(Exception e) {
								System.out.println("Invalid date entered!! Try again");
							}

						}while(flag);
						for(int i=0;i<bills.size();i++) {
							Bill curr_bill=bills.get(i);
							Date bill_date=curr_bill.getDueDate();
							
							if(date_start.compareTo(bill_date)<=0 && 
									date_end.compareTo(bill_date)>=0) {
								overdueBills.add(curr_bill);
							}
						}
						break;
					case 2:
						System.out.println("Enter bill category");
						String bill_category=s.nextLine();
						for(int i=0;i<bills.size();i++) {
							if(bills.get(i).getBillCategory().trim().equals(bill_category)) {
								overdueBills.add(bills.get(i));
							}
						}
						break;
					default: System.out.println("Wrong Choice!!");
					break;
				}
				break;
			case 2:
				System.out.println("Select bill category\n1. House Rent\n2. Debt Payments\n"+
						"3. Groceries\n4. Internet Charges\n5. Cellphone Charges");
				int bill_category=Integer.parseInt(s.nextLine().trim());
				System.out.println("Enter bill name");
				String bill_name=s.nextLine();
				switch(bill_category) {
				case 1: 
					for(int i=0;i<bills.size();i++) {
						if(bills.get(i).getBillCategory().trim().equals("House Rent")
								&& bills.get(i).getBillName().trim().equals(bill_name)) {
							overdueBills.add(bills.get(i));
							break;
						}
					}
					break;
				case 2: 
					for(int i=0;i<bills.size();i++) {
						if(bills.get(i).getBillCategory().trim().equals("Debt Payments")
								&& bills.get(i).getBillName().trim().equals(bill_name)) {
							overdueBills.add(bills.get(i));
							break;
						}
					}
					break;
				case 3:
					for(int i=0;i<bills.size();i++) {
						if(bills.get(i).getBillCategory().trim().equals("Groceries")
								&& bills.get(i).getBillName().trim().equals(bill_name)) {
							overdueBills.add(bills.get(i));
							break;
						}
					}
					break;
				case 4:
					for(int i=0;i<bills.size();i++) {
						if(bills.get(i).getBillCategory().trim().equals("Internet Charges")
								&& bills.get(i).getBillName().trim().equals(bill_name)) {
							overdueBills.add(bills.get(i));
							break;
						}
					}
					break;
				case 5:
					for(int i=0;i<bills.size();i++) {
						if(bills.get(i).getBillCategory().trim().equals("Cellphone Charges")
								&& bills.get(i).getBillName().trim().equals(bill_name)) {
							overdueBills.add(bills.get(i));
							break;
						}
					}
					break;
				default: System.out.println("Wrong Choice!!");
				break;
				}
				break;
			default: System.out.println("Wrong Choice!!");
			break;
		}
		return overdueBills;

	}
}
