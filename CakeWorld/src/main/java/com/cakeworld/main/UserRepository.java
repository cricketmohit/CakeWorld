package com.cakeworld.main;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cakeworld.model.Users;



public interface UserRepository extends CrudRepository<Users, Integer> {

}