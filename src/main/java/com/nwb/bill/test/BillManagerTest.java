/**
 * BillManagerTest.java
 * 
 * Authors Name: Hemalata L Chavan, Jatin Johar
 * Date: 19-Aug-2024
 * 
 * This class contains unit tests for the BillManager class in the Bill Management System. 
 * It uses JUnit to verify the functionality of the methods in the BillManager class.
 * 
 * The BillManagerTest class includes the following test cases:
 * 
 * 1. **testAddNewBill**: Tests the `addNewBill(Bill bill)` method of the BillManager to ensure that a new bill is added correctly.
 *    - Creates a test bill and adds it using the BillManager.
 *    - Verifies that the bill has been added by checking the list of bills.
 * 
 * 2. **testGetOverdueBills**: Tests the `getOverdueBills()` method to ensure it returns only overdue bills.
 *    - Sets up sample bills, including overdue and non-overdue bills.
 *    - Verifies that only the overdue bills are returned.
 * 
 * 3. **testGetBillsOverview_AllBills**: Tests the `getBillsOverview()` method to retrieve all bills.
 *    - Ensures that the correct number of bills is returned when no filters are applied.
 * 
 * 4. **testGetBillsOverview_ByCategory**: Tests the `getBillsOverview(String category, Date fromDate, Date toDate, String status)` method 
 *    to retrieve bills by category.
 *    - Verifies that bills are correctly filtered by category.
 * 
 * 5. **testGetBillsOverview_ByDateRange**: Tests the `getBillsOverview(String category, Date fromDate, Date toDate, String status)` method 
 *    to retrieve bills within a specified date range.
 *    - Ensures that bills falling within the date range are returned.
 * 
 * 6. **testGetBillsOverview_ByStatus**: Tests the `getBillsOverview(String category, Date fromDate, Date toDate, String status)` method 
 *    to retrieve bills by payment status.
 *    - Verifies that bills are correctly filtered by status.
 * 
 * 7. **testGetBillsOverview_NoResults**: Tests the `getBillsOverview()` method with a non-existent category to ensure that no results are returned.
 *    - Verifies that an empty list is returned when an invalid category is specified.
 * 
 * 8. **testGetUpcomingBills**: Tests the `getUpcomingBills()` method to ensure it returns bills that are upcoming.
 *    - Adds sample bills and verifies that the correct upcoming bills are returned.
 * 
 * 9. **testMarkBillAsPaid**: Tests the `markBillAsPaid(Bill bill)` method to mark a bill as paid.
 *    - Updates the payment status of a bill and verifies that the status and overdue days are updated correctly.
 * 
 * 10. **testSnoozeBill**: Tests the `snoozeBill(Bill bill, Date newDueDate)` method to update the due date of a bill.
 *     - Changes the due date of a bill and verifies that the new due date and overdue days are updated correctly.
 * 
 * Dependencies:
 * - BillManager: The class under test, responsible for managing bills.
 * - Bill: The entity representing a bill.
 * 
 * Note: Ensure that the BillManager class is properly instantiated and that the necessary setup is done in the test constructor.
 * 
 */



package com.nwb.bill.test;

import com.nwb.bill.model.Bill;
import com.nwb.bill.repo.BillManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class BillManagerTest {

    private BillManager billManager;
    private Bill bill1;
    private Bill bill2;

    public BillManagerTest() {
//    	
//        //billManager the BillManager with a sample userId
    	billManager = new BillManager();

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
        public void testAddNewBill() {
            // Setup test bill
			 
		    Bill testBill = new Bill();
		    testBill.setBillName("Test Bill");
		    testBill.setBillCategory("Utilities");
		    testBill.setAmount(100.00);
		    try {
		        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		        Date dueDate = sdf.parse("10-08-2024");
		        testBill.setDueDate(dueDate);
		    } catch (ParseException e) {
		        fail("Failed to parse date.");
		    }
		    testBill.setReminderFrequency("monthly");
		    testBill.setAttachment(new File("C:\\Users\\bhatt\\Downloads\\Documents\\PayPilot"));
		    testBill.setNotes("Test notes");
		    testBill.setRecurring(true);
		    testBill.setPaymentStatus("Not Paid");

		    // Test addNewBill()
		    billManager.addNewBill(testBill);

            // Verify the bill was added correctly
            List<Bill> bills = billManager.getBillsOverview(null, null, null, null);
            assertEquals(3, bills.size());
            Bill savedBill = bills.get(2);
            assertEquals("Test Bill", savedBill.getBillName());
            assertEquals("Utilities", savedBill.getBillCategory());
            assertEquals(100.00, savedBill.getAmount(), 0.001);
            assertEquals("monthly", savedBill.getReminderFrequency());
            assertEquals("Test notes", savedBill.getNotes());
            assertTrue(savedBill.isRecurring());
            assertEquals("Not Paid", savedBill.getPaymentStatus());
            assertEquals(0, savedBill.getOverdueDays());
        }


		@Test
       public void testGetOverdueBills() {
            // Setup test bills
            
            // Test getOverdueBills()
            List<Bill> overdueBills = billManager.getOverdueBills();

            // Verify only the overdue bill is returned
            assertEquals(1, overdueBills.size());
           assertEquals("Internet Bill", overdueBills.get(0).getBillName(), "Internet Bill");
        }
		

      @Test
      public void testGetBillsOverview_AllBills() {
    	  // Create sample Bill objects

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
	// Test case for upcoming bills
      @Test
	    public void testGetUpcomingBills() throws ParseException {
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	        
	        // Add bills to the manager
	        billManager.addNewBill(bill1);
	        billManager.addNewBill(bill2);
	        //billManager.addNewBill(bill3);

	        // Get the upcoming bills (bills that are not paid yet)
	        List<Bill> upcomingBills = billManager.getUpcomingBills();

	        // Verify that the correct bills are returned
	        assertEquals(2, upcomingBills.size());
	        assertTrue(upcomingBills.contains(bill1));
	        assertTrue(!upcomingBills.contains(bill2)); // The paid bill should not be in the list
	    }
		
      //test case for markBillAsPaid
      @Test
      public void testMarkBillAsPaid() {
          billManager.markBillAsPaid(bill1);
          assertEquals("PAID", bill1.getPaymentStatus());
          assertEquals(0, bill1.getOverdueDays());
      }
      
      
      //test case for snoozeBill
      @Test
      public void testSnoozeBill() {
          Date oldDueDate = bill2.getDueDate();
          Date newDueDate = new Date(oldDueDate.getTime() + 1000 * 60 * 60 * 24 * 7); // Set to 7 days later
          billManager.snoozeBill(bill2, newDueDate);
          assertEquals(newDueDate, bill2.getDueDate());
          assertEquals(0, bill2.getOverdueDays());
      }

}
