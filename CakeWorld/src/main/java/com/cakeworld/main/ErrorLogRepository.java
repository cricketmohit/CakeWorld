package com.cakeworld.main;
import org.springframework.data.repository.CrudRepository;

import com.cakeworld.model.ErrorLog;



public interface ErrorLogRepository extends CrudRepository<ErrorLog, Integer> {
	
	

}