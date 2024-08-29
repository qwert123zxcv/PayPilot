package com.basics.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.basics.model.Bill;

public interface BillRepository extends CrudRepository<Bill, Long>{
	 // Custom query to get overdue bills
    @Query("SELECT b FROM Bill b WHERE b.overdueDays > 0")
    List<Bill> findOverdueBills();
    
    @Query("SELECT b FROM Bill b WHERE " +
            "(:category IS NULL OR :category = 'all' OR b.billCategory = :category) AND " +
            "(:fromDate IS NULL OR b.dueDate >= :fromDate) AND " +
            "(:toDate IS NULL OR b.dueDate <= :toDate) AND " +
            "(:status IS NULL OR b.paymentStatus = :status)")
     List<Bill> findBillsOverview(@Param("category") String category,
                                  @Param("fromDate") Date fromDate,
                                  @Param("toDate") Date toDate,
                                  @Param("status") String status);
}
