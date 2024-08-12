package com.nwb.bill;

import com.nwb.bill.manager.BillManager;
import com.nwb.bill.model.Bill;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;

public class BillManagerTest {

    private BillManager billManager;
    private Bill bill1;
    private Bill bill2;

    public BillManagerTest() {
    	
        // Initialize the BillManager with a sample userId
        billManager = new BillManager("user123");

        // Create sample Bill objects
        bill1 = new Bill();
        bill1.setBillId(1);
        bill1.setBillName("Electricity Bill");
        bill1.setBillCategory("HOUSE_RENT");
        bill1.setDueDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 5)); // Due in 5 days
        bill1.setAmount(100.0);
        bill1.setReminderFrequency("MONTHLY");
        bill1.setAttachment(null);
        bill1.setNotes("Pay before end of month");
        bill1.setRecurring(true);
        bill1.setPaymentStatus("UPCOMING");
        bill1.setOverdueDays(0);

        bill2 = new Bill();
        bill2.setBillId(2);
        bill2.setBillName("Internet Bill");
        bill2.setBillCategory("INTERNET_CHARGES");
        bill2.setDueDate(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 10)); // Overdue by 10 days
        bill2.setAmount(50.0);
        bill2.setReminderFrequency("MONTHLY");
        bill2.setAttachment(null);
        bill2.setNotes("Check connection issues");
        bill2.setRecurring(true);
        bill2.setPaymentStatus("PENDING");
        bill2.setOverdueDays(10);

        // Add bills to BillManager
        billManager.addNewBill(bill1);
        billManager.addNewBill(bill2);
    }

    // You can now add test methods here to test the BillManager functionalities
    
    @Test
    public void testGetBillsOverview_AllBills() {
    	 List<Bill> bills = billManager.getBillsOverview(null, null, null, null);
    	 assertEquals(2, bills.size());
    }
    
    @Test
    public void testGetBillsOverview_ByCategory() {
    	 List<Bill> bills = billManager.getBillsOverview("HOUSE_RENT", null, null, null);
         assertEquals(1, bills.size());
         assertEquals("Electricity Bill", bills.get(0).getBillName());
    }
    
    @Test
    public void testGetBillsOverview_ByDateRange() {
    	 Date fromDate = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 15); // 15 days ago
         Date toDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15); // 15 days from now
         List<Bill> bills = billManager.getBillsOverview(null, fromDate, toDate, null);
         assertEquals(2, bills.size());
    }
    
    @Test
    public void testGetBillsOverview_ByStatus() {
    List<Bill> bills = billManager.getBillsOverview(null, null, null, "PENDING");
    assertEquals(1, bills.size());
    assertEquals("Internet Bill", bills.get(0).getBillName());
    }
    
    @Test
    public void testGetBillsOverview_NoResults() {
    	 List<Bill> bills = billManager.getBillsOverview("NON_EXISTENT_CATEGORY", null, null, null);
         assertTrue(bills.isEmpty());
    }
    
    
}

