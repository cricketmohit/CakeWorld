package com.cakeworld.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Pincode {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long id;
	

    public String pincode;

   
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }

  
	

}
