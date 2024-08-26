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
 * 1. testAddNewBill: Tests the addNewBill(Bill bill) method of the BillManager to ensure that a new bill is added correctly.
 *    - Creates a test bill and adds it using the BillManager.
 *    - Verifies that the bill has been added by checking the list of bills.
 * 
 * 2. testGetOverdueBills: Tests the getOverdueBills() method to ensure it returns only overdue bills.
 *    - Sets up sample bills, including overdue and non-overdue bills.
 *    - Verifies that only the overdue bills are returned.
 * 
 * 3. testGetBillsOverview_AllBills: Tests the getBillsOverview() method to retrieve all bills.
 *    - Ensures that the correct number of bills is returned when no filters are applied.
 * 
 * 4. testGetBillsOverview_ByCategory: Tests the getBillsOverview(String category, Date fromDate, Date toDate, String status) method 
 *    to retrieve bills by category.
 *    - Verifies that bills are correctly filtered by category.
 * 
 * 5. testGetBillsOverview_ByDateRange: Tests the getBillsOverview(String category, Date fromDate, Date toDate, String status) method 
 *    to retrieve bills within a specified date range.
 *    - Ensures that bills falling within the date range are returned.
 * 
 * 6. testGetBillsOverview_ByStatus: Tests the getBillsOverview(String category, Date fromDate, Date toDate, String status) method 
 *    to retrieve bills by payment status.
 *    - Verifies that bills are correctly filtered by status.
 * 
 * 7. testGetBillsOverview_NoResults: Tests the getBillsOverview() method with a non-existent category to ensure that no results are returned.
 *    - Verifies that an empty list is returned when an invalid category is specified.
 * 
 * 8. testGetUpcomingBills: Tests the getUpcomingBills() method to ensure it returns bills that are upcoming.
 *    - Adds sample bills and verifies that the correct upcoming bills are returned.
 * 
 * 9. testMarkBillAsPaid: Tests the markBillAsPaid(Bill bill) method to mark a bill as paid.
 *    - Updates the payment status of a bill and verifies that the status and overdue days are updated correctly.
 * 
 * 10. testSnoozeBill: Tests the snoozeBill(Bill bill, Date newDueDate) method to update the due date of a bill.
 *     - Changes the due date of a bill and verifies that the new due date and overdue days are updated correctly.
 * 
 * Dependencies:
 * - BillManager: The class under test, responsible for managing bills.
 * - Bill: The entity representing a bill.
 * 
 * Note: Ensure that the BillManager class is properly instantiated and that the necessary setup is done in the test constructor.
 * /*
 * Make sure to have these records in your bills table for a successful test run
 * 
 * BILL_ID  BILL_NAME            BILL_CATEGORY     DUE_DATE     AMOUNT  REMINDER_FREQUENCY  ATTACHMENT  NOTES                               IS_RECURRING  PAYMENT_STATUS  OVERDUE_DAYS
 * -------  -------------------  ----------------  ----------  -------  ------------------  -----------  ----------------------------------  ------------  --------------  ------------
 *       1  January Rent         House Rent        05-JAN-24   1200.00  Monthly             No          Paid via bank transfer               T             Paid              0
 *       2  Internet Bill        Internet Charges  10-JAN-24     50.00  Monthly             Yes         Includes both home and office usage  T             Upcoming          0
 *       3  Grocery Shopping     Groceries         15-JAN-24    300.00  Weekly              No          Includes fruits and vegetables       F             Pending           0
 *       4  Phone Bill           Cellphone Charges 20-JAN-24     30.00  Monthly             No          Includes international calls         T             Paid              0
 *       5  Credit Card Payment  Debt Payments     25-JAN-24    500.00  Monthly             Yes         Includes late fee                    F             Pending           5
 *       6  February Rent        House Rent        05-FEB-24   1200.00  Monthly             No          Next month's rent                    T             Upcoming          0
 *       7  February Internet    Internet Charges  10-FEB-24     50.00  Monthly             Yes         Monthly internet service             T             Upcoming          0
 *       8  February Groceries   Groceries         15-FEB-24    300.00  Weekly              No          Expected grocery expenses            F             Upcoming          0
 *       9  February Phone Bill  Cellphone Charges 20-FEB-24     30.00  Monthly             No          Cell phone charges for February      T             Upcoming          0
 *      10  February Credit Card Debt Payments     25-FEB-24    500.00  Monthly             Yes         Credit card bill for February        F             Upcoming          0
 *

 * 
 */



package com.nwb.bill.test;

import com.nwb.bill.model.Bill;
import com.nwb.bill.repo.BillManager;
import com.nwb.bill.service.BillManagerService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.sql.*; 
import java.util.Date;
// This is for java.sql.Date


import org.junit.Test;

public class BillManagerTest {

    private BillManager billManager;
    private Bill bill1;
    private Bill bill2;
    private BillManagerService billManagerService;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public BillManagerTest() {
//    	
//        //billManager the BillManager with a sample userId
    	billManager = new BillManager();
    	billManagerService=new BillManagerService();
//

  }
    // You can now add test methods here to test the BillManager functionalities
   
		@Test
public void testAddNewBill() {
    // Setup test bill
    Bill testBill = new Bill();
    testBill.setBillId(3+"");
    testBill.setBillName("Test Bill");
    testBill.setBillCategory("house rent");
    testBill.setAmount(100.00f);
    try {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date dueDate = sdf.parse("30-08-2024");
        testBill.setDueDate(dueDate);
    } catch (ParseException e) {
        fail("Failed to parse date.");
    }
    testBill.setReminderFrequency("monthly");
    testBill.setNotes("Test notes");
    testBill.setRecurring(true);
    testBill.setPaymentStatus("upcoming");

    // Test addNewBill()
    billManager.addNewBill(testBill);

    // Retrieve bills from the database and verify the bill was added correctly
    List<Bill> bills = billManager.getBillsOverview(null, null, null, null);
    assertEquals(3, bills.size()); // assuming there are already 2 bills in the table

    Bill savedBill = bills.get(2); // since it's zero-indexed, the new bill would be at index 2
    assertEquals("Test Bill", savedBill.getBillName());
    assertEquals("house rent", savedBill.getBillCategory());
    assertEquals(100.00, savedBill.getAmount(), 0.001);
    assertEquals("monthly", savedBill.getReminderFrequency());
    assertEquals("Test notes", savedBill.getNotes());
    assertTrue(savedBill.isRecurring()==1?true:false);
    assertEquals("upcoming", savedBill.getPaymentStatus());
    assertEquals(0, savedBill.getOverdueDays());
}
//jatin
		@Test
       public void testGetOverdueBills() {
            // Setup test bills
            
            // Test getOverdueBills()
            List<Bill> overdueBills = billManager.getOverdueBills();

            // Verify only the overdue bill is returned
            assertEquals(0, overdueBills.size());
           
        }//jatin
		

      @Test
      public void testGetBillsOverview_AllBills() {
    	  // Create sample Bill objects
    	  
    	  // Fetch all bills regardless of filters
         List<Bill> bills = billManager.getBillsOverview(null, null, null, null);
         assertEquals(3, bills.size());
      }// jagadesh

      @Test
      public void testGetBillsOverview_ByCategory() {
    	// Fetch bills by category
         List<Bill> bills = billManager.getBillsOverview("house rent", null, null, null);
          assertEquals(3, bills.size());
         // assertEquals("Electricity Bill", bills.get(0).getBillName());
      } // edi jagadesh

      @Test
      public void testGetBillsOverview_ByDateRange() {
    	    // Define date format and initialize SimpleDateFormat
    	    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    	    
    	    try {
    	        // Define the date range for the test
    	        Date fromDate = sdf.parse("29-08-2024"); // Start date of range
    	        Date toDate = sdf.parse("13-09-2024");   // End date of range

    	        // Fetch bills within the date range
    	        List<Bill> bills = billManagerService.getBillsOverview(null, fromDate, toDate, null);

    	        // Check if the result is not null
    	        assertNotNull("Bills list should not be null", bills);

    	        // Assert that bills returned are within the date range
    	        for (Bill bill : bills) {
    	            Date billDueDate = bill.getDueDate();
    	            assertTrue("Bill due date should be within the specified range", 
    	                !billDueDate.before(fromDate) && !billDueDate.after(toDate));
    	        }
    	        
    	        // Additional assertions based on expected results
    	        // Example: Check if the number of bills matches the expected count
    	        // Replace with actual expected count as needed
    	        int expectedCount = 3; // Example expected count
    	        assertEquals("Number of bills should match the expected count", expectedCount, bills.size());
    	        
    	    } catch (ParseException e) {
    	        e.printStackTrace();
    	        fail("Date parsing failed: " + e.getMessage());
    	    }
      }// edi jaagdesh

      @Test
      public void testGetBillsOverview_ByStatus() {
    	   // Fetch bills by payment status
      List<Bill> bills = billManager.getBillsOverview(null, null, null, "pending");
      assertEquals(1, bills.size());
     assertEquals("hospital", bills.get(0).getBillName());
      } // jagadesh

      @Test
      public void testGetBillsOverview_NoResults() {
    	  // Test case with no matching results
         List<Bill> bills = billManager.getBillsOverview("NON_EXISTENT_CATEGORY", null, null, null);
           assertTrue(bills.isEmpty());
      }//jagadesh
	// Test case for upcoming bills
     @Test
public void testGetUpcomingBills() throws ParseException {

    // Get the upcoming bills (bills that are not paid yet)
    List<Bill> upcomingBills = billManager.getUpcomingBills();

    // Verify that the correct bills are returned
    assertEquals(2, upcomingBills.size()); 
//    assertTrue(upcomingBills.contains(bill1));
//    assertFalse(upcomingBills.contains(bill2)); // The paid bill should not be in the list
}
//jatin		
      //test case for markBillAsPaid
     @Test
public void testMarkBillAsPending() {
    String billId = "1";  // Assuming this bill exists in the database

    // Step 1: Fetch the bill from the database using the billId
    Bill bill = billManagerService.searchBillWithId(billId);
    assertNotNull(bill);
    assertEquals("pending", bill.getPaymentStatus()); // Ensure the initial status is "Pending"

    // Step 2: Mark the bill as paid
    billManagerService.markBillAsPaid(billId); // This method should update the payment status in the database

    // Step 3: Verify the changes in the database
    Bill updatedBill = billManagerService.searchBillWithId(billId); // Fetch the updated bill after the status change
    assertNotNull(updatedBill);
    assertEquals("paid", updatedBill.getPaymentStatus()); // Check if the payment status is updated to "Paid"
    assertEquals(0, updatedBill.getOverdueDays()); // Ensure overdue days are set to 0
}
//jatin      
      
      //test case for snoozeBill
      @Test
      public void testSnoozeBillWithCustomDate() {
          String billId = "1"; // ID of the bill to test change accodingly to database
          SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	        Date NewdueDate;
			try {
				NewdueDate = sdf.parse("10-10-2024");
				 Bill oldBill = billManagerService.searchBillWithId(billId);
		          assertNotNull("Bill should not be null before snoozing", oldBill);
		          Date oldDueDate = oldBill.getDueDate();
		          int oldOverdueDays = oldBill.getOverdueDays();

		          // Act: Call the snoozeBill method with the custom date
		          billManagerService.snoozeBill(billId, NewdueDate);

		          // Fetch the updated bill from the service
		          Bill updatedBill = billManagerService.searchBillWithId(billId);
		          assertNotNull("Bill should not be null after snoozing", updatedBill);

		         
		         
		          // Assert: Verify the updated due date and overdue days
		          assertEquals("Due date should be updated", NewdueDate, updatedBill.getDueDate());
		          assertEquals("Overdue days should be 0", 0, updatedBill.getOverdueDays());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// Custom snooze date

          // Fetch the old bill details
         
      }
         
        
        
      } // jagadesh
