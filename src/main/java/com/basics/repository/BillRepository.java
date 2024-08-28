package com.basics.repository;

import org.springframework.data.repository.CrudRepository;

import com.basics.model.Bill;

public interface BillRepository extends CrudRepository<Bill, Long>{

}
