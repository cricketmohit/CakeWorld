package com.cakeworld.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity // This tells Hibernate to make a table out of this class
@Data
public class Menu {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer id;
    
    public Integer externalId;

    public String name;

    public String description;
    
    public int price;
    public int priceMedium;
    public int priceLarge;
    public int piece;
    public int gram;
    public String currency;
    
    @ManyToOne
    public Category category;
    
    public boolean availableToOrder;
    
    public String imageUrl;
    
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }
  
    }

	


