package com.cakeworld.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;

@Entity // This tells Hibernate to make a table out of this class
@Data
public class ErrorLog {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer id;
    
    @Lob
    @Column(length=100000)
    private byte[] paylaod;

    public String errorMessage;
    
    public Date createdDate;

    public String logicalKey;
    
    
    }

	


