package com.cakeworld.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class ContactUs {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	long id;
	String emailId;
	String name;
	String subject;
	String message;

}
