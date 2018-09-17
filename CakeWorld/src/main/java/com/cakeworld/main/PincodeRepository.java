package com.cakeworld.main;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cakeworld.model.Pincode;

public interface PincodeRepository  extends CrudRepository<Pincode, Integer> {

	@Query(" FROM Pincode")
	List<Pincode> findPincode();
	
}
