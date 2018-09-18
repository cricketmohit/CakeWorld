package com.cakeworld.main;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cakeworld.model.Pincode;

public interface PincodeRepository  extends CrudRepository<Pincode, Integer> {

	@Query(" FROM Pincode where pincode = ?")
	Pincode findPincode(String userPincode);
	
}
