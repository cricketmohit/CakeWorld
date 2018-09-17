package com.cakeworld.main;
import org.springframework.data.repository.CrudRepository;

import com.cakeworld.model.Cart;



public interface CartRepository extends CrudRepository<Cart, Integer> {
	
	Cart findByLoggedInUser(String loggedInUser);

}