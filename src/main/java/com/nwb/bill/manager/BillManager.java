
package com.nwb.bill.manager;
import com.nwb.bill.model.Bill;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class BillManager {
	
	private String userId;
	private List<Bill> bills;
	
	Scanner s=new Scanner(System.in);
  
	public BillManager(String userId) {
		this.userId = userId;
		this.bills = new ArrayList<>();
	}

	public void addNewBill(Bill bill) {
		//must add some code here
		bills.add(bill);
	}

	//Get all overdue bills
	public List<Bill> getOverdueBills() {
		//Must add some code here
		List<Bill> overdueBills=new ArrayList<>();
		for(int i=0;i<bills.size();i++) {
			if(bills.get(i).getOverdueDays()>0) {
				overdueBills.add(bills.get(i));
			}
		}
		return overdueBills;
	}
	
    
    public List<Bill> getBillsOverview(String category, Date fromDate, Date toDate, String status) {
        List<Bill> filteredBills = new ArrayList<>();
        for (Bill bill : bills) {

        	boolean matchesCategory = true;
           	if (category != null && !category.equalsIgnoreCase("all")) {
            matchesCategory = bill.getBillCategory().equalsIgnoreCase(category);
            }
		
            boolean matchesFromDate = fromDate == null? true : !bill.getDueDate().before(fromDate);
            boolean matchesToDate = toDate == null? true : !bill.getDueDate().after(toDate);
            boolean matchesStatus = status == null? true: bill.getPaymentStatus().equalsIgnoreCase(status);

            if (matchesCategory && matchesFromDate && matchesToDate && matchesStatus) {
                filteredBills.add(bill);
            }
        }
        return filteredBills;
    }
    
    public void markBillAsPaid(Bill bill) {
        bill.setPaymentStatus("PAID");
        bill.setOverdueDays(0); 
    }
//	//If bill category and bill name is selected
//	public List<Bill> getOverdueBills(List<Bill> bills, String bill_category, String bill_name) {
//		//Must add some code here
//		List<Bill> overdueBills=new ArrayList<>();
//
//		for(int i=0;i<bills.size();i++) {
//			if(bills.get(i).getBillCategory().trim().equals(bill_category)
//					&& bills.get(i).getBillName().trim().equals(bill_name)
//					&& bills.get(i).getOverdueDays()>0) {
//				overdueBills.add(bills.get(i));
//				break;
//			}
//		}
//		return overdueBills;
//	}
//	
//	//For filter BillCategory in UI
//	public List<Bill> getOverdueBills(List<Bill> bills, String bill_category){
//		List<Bill> overdueBills=new ArrayList<>();
//		for(int i=0;i<bills.size();i++) {
//			if(bills.get(i).getBillCategory().trim().equals(bill_category)) {
//				overdueBills.add(bills.get(i));
//			}
//		}
//		return overdueBills;
//	}
//	
//	//For filter Date Range in UI
//	public List<Bill> getOverdueBills(List<Bill> bills, Date start_date, Date end_date){
//		List<Bill> overdueBills=new ArrayList<>();
//		for(int i=0;i<bills.size();i++) {
//			Bill curr_bill=bills.get(i);
//			Date bill_date=curr_bill.getDueDate();
//			
//			if(start_date.compareTo(bill_date)<=0 && 
//					end_date.compareTo(bill_date)>=0) {
//				overdueBills.add(curr_bill);
//			}
//		}
//		return overdueBills;
//	}

}
