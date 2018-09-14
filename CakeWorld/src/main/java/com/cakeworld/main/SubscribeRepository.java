package com.cakeworld.main;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cakeworld.model.Menu;
import com.cakeworld.model.SubscribedEmail;
import com.cakeworld.model.User;

public interface SubscribeRepository  extends CrudRepository<SubscribedEmail, Integer> {

	List<SubscribedEmail> findByEmailId(String email);
}
