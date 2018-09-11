package com.cakeworld.main;
import org.springframework.data.repository.CrudRepository;

import com.cakeworld.model.Bill;

public interface BillRepository extends CrudRepository<Bill, Integer> {
	
	

}