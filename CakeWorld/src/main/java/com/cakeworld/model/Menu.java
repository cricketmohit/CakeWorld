package com.cakeworld.model;

import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
    
    @ManyToOne
    public Category category;
    
    public boolean availableToOrder;
    
    public String imageUrl;
  
    }

	


