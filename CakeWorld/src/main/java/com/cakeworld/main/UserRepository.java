package com.cakeworld.main;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cakeworld.model.Users;



public interface UserRepository extends CrudRepository<Users, Integer> {
	
	List<Users> findByEmail(String name);

}