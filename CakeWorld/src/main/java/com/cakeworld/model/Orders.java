package com.cakeworld.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Orders{
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long id;
  
    public long price;
    
    public String currency;
    
    @ManyToOne
    public Bill bill;
    
    @OneToOne
    @JoinColumn(name = "menu_id")
    public Menu menu;
    
    public String menu_name;
    
    public int quantity;
    
    public Date creationTime;
    
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }
}
