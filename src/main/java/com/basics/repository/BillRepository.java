package com.basics.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.basics.model.Bill;

/**
 * Author: Hemalata L Chavan, Jatin Johar
 * Date: 03-Sept-2024
 * BillRepository interface extends CrudRepository to provide custom query methods
 * for managing Bill entities in the database.
 */
public interface BillRepository extends CrudRepository<Bill, Long> {
    
    // Custom query to get overdue bills
    @Query("SELECT b FROM Bill b WHERE b.overdueDays > 0")
    List<Bill> findOverdueBills();
    
    // Custom query to get bills overview based on category, date range, and status
    @Query("SELECT b FROM Bill b WHERE " +
            "(:category IS NULL OR :category = 'all' OR b.billCategory = :category) AND " +
            "(:fromDate IS NULL OR b.dueDate >= :fromDate) AND " +
            "(:toDate IS NULL OR b.dueDate <= :toDate) AND " +
            "(:status IS NULL OR b.paymentStatus = :status)")
    List<Bill> findBillsOverview(@Param("category") String category,
                                 @Param("fromDate") Date fromDate,
                                 @Param("toDate") Date toDate,
                                 @Param("status") String status);
    
    // Custom query to get bills with 'Upcoming' payment status
    @Query("SELECT b FROM Bill b WHERE b.paymentStatus = 'Upcoming'")
    List<Bill> findUpcomingBills();
}
