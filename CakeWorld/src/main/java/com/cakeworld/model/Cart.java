package com.cakeworld.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Cart {
	
	public Cart(String loggedInUser,String cookieCartCounts){
		this.loggedInUser=loggedInUser;
		this.cookieCartCounts=cookieCartCounts;
	}
	public Cart(){
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	long id;
	@Column(unique=true)
	String loggedInUser;
	String cookieCartCounts;

}
