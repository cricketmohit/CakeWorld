package com.cakeworld.main;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cakeworld.model.ContactUs;
import com.cakeworld.model.Menu;
import com.cakeworld.model.SubscribedEmail;
import com.cakeworld.model.User;

public interface ContactRepository  extends CrudRepository<ContactUs, Integer> {

	List<ContactUs> findByEmailId(String email);
}
