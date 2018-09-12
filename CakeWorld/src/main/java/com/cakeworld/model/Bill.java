package com.cakeworld.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Bill {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long id;
	

    public String name;

    public String email;
    
    public String  address;
    
    public String city;
    
    public long zip;
    
	public Date orderDate;
	
	public String timeSlot;
	
	@OneToMany(targetEntity = Orders.class, mappedBy = "bill", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Orders> orderList;
    
    public boolean delivered;
    
    public boolean shipped;
    
    public long subTotal;
    
    public long deliveryCharge;
    
    public long totalBillPrice;
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }

  
	

}
