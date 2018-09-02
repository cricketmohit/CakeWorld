package com.cakeworld.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity // This tells Hibernate to make a table out of this class
@Data
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer id;

	@OneToMany(targetEntity = Menu.class, mappedBy = "category", fetch = FetchType.EAGER)
	private List<Menu> menuList;

	private String categoryType;
	private boolean categoryAvailable;
}