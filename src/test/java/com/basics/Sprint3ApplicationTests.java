package com.basics;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.basics.model.Bill;
import com.basics.repository.BillRepository;
import com.basics.service.BillService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest // This will start the full application context for testing
@ActiveProfiles("test") // Uses the test profile
public class Sprint3ApplicationTests {
	public SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Autowired
    private BillService billService;

    @Autowired
    private BillRepository billRepository;

    @BeforeEach
    void setUp() {
        // If you want to clear the database before each test, uncomment this line
        // billRepository.deleteAll();
        // Ensure the initial count of bills is 14
        long initialCount = billRepository.count();
        assertThat(initialCount).isEqualTo(14);
    }
    @Test
    public void testGetBillsOverview_AllBills() {
        // Create sample Bill objects
        // You might need to add test data to the database or mock the data as needed

        // Fetch all bills regardless of filters
        List<Bill> bills = billService.getBillsOverview(null, null, null, null);

        // Verify the number of bills returned
        assertThat( bills.size()).isEqualTo(14);
    }
    @Test
    public void testGetBillsOverview_ByCategory() {
  	// Fetch bills by category
       List<Bill> bills = billService.getBillsOverview("House Rent", null, null, null);
        assertEquals(3, bills.size());
       // assertEquals("Electricity Bill", bills.get(0).getBillName()); // Write the bill name according to database
    } // 

    @Test
    public void testGetBillsOverview_ByDateRange() {
  	    // Define date format and initialize SimpleDateFormat
  	    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
  	    
  	    try {
  	        // Define the date range for the test
  	        Date fromDate = sdf.parse("01-04-2024"); // Start date of range
  	        Date toDate = sdf.parse("30-04-2024");   // End date of range

  	        // Fetch bills within the date range
  	        List<Bill> bills = billService.getBillsOverview(null, fromDate, toDate, null);

  	        // Check if the result is not null
  	        assertNotNull( bills);

  	        // Assert that bills returned are within the date range
  	        for (Bill bill : bills) {
  	            Date billDueDate = bill.getDueDate();
  	            assertTrue( 
  	                !billDueDate.before(fromDate) && !billDueDate.after(toDate));
  	        }
  	        
  	        // Additional assertions based on expected results
  	        // Example: Check if the number of bills matches the expected count
  	        // Replace with actual expected count as needed
  	        int expectedCount = 1; // Example expected count
  	        assertEquals(expectedCount, bills.size());
  	        
  	    } catch (ParseException e) {
  	        e.printStackTrace();
  	        fail("Date parsing failed: " + e.getMessage());
  	    }
    }//

    @Test
    public void testGetBillsOverview_ByStatus() {
  	   // Fetch bills by payment status
    List<Bill> bills = billService.getBillsOverview(null, null, null, "Pending");
    assertEquals(2, bills.size());
   assertEquals("March Rent", bills.get(0).getBillName());
    }

    @Test
    public void testGetBillsOverview_NoResults() {
  	  // Test case with no matching results
       List<Bill> bills = billService.getBillsOverview("NON_EXISTENT_CATEGORY", null, null, null);
         assertTrue(bills.isEmpty());
    }
    @Test
    public void testAddNewBill() {
        // Setup test bill
		 
	    Bill testBill = new Bill();
	    testBill.setId((long) 20);
	    testBill.setBillName("Test Bill");
	    testBill.setBillCategory("Utilities");
	    testBill.setAmount((float) 100.00);
	    try {
	        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	        Date dueDate = sdf.parse("10-08-2024");
	        testBill.setDueDate(dueDate);
	    } catch (ParseException e) {
	        fail("Failed to parse date.");
	    }
	    testBill.setReminderFrequency("monthly");
	    testBill.setAttachment("yes");
	    testBill.setNotes("Test notes");
	    testBill.setRecurring(true);
	    testBill.setPaymentStatus("Not Paid");

	    // Test addNewBill()
	    billService.addNewBill(testBill);

        // Verify the bill was added correctly
        List<Bill> bills = billService.getBillsOverview(null, null, null, null);
        assertEquals(15, bills.size());
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
        // You might need to add test data to the database or mock the data as needed

        // Test getOverdueBills()
        List<Bill> overdueBills = billService.getOverdueBills();

        // Verify only the overdue bill is returned
        assertThat( overdueBills.size()).isEqualTo(1);
       // assertEquals("Internet Bill", overdueBills.get(0).getBillName());
    }
  


   
    
}
