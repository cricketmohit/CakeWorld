package com.cakeworld.main;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cakeworld.model.User;



public interface UserRepository extends CrudRepository<User, Integer> {
	
	List<User> findByEmail(String name);

}