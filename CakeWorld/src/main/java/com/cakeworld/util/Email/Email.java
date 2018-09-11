package com.cakeworld.util.Email;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cakeworld.util.AppUtil;

import lombok.Data;

@Data
public class Email {
	
	private String from;
 
	private List<String> to;
 
	private List<String> cc;
 
	private String subject;
 
	private String message;
	
	private boolean isHtml;
 
	public Email() {
		this.to = new ArrayList<String>();
		this.cc = new ArrayList<String>();
	}
 
	public Email(String from, String toList, String subject, String message) {
		this();
		this.from = from;
		this.subject = subject;
		this.message = message;
		this.to.addAll(Arrays.asList(splitByComma(toList)));
	}
 
	public Email(String from, String toList, String ccList, String subject, String message) {
		this();
		this.from = from;
		this.subject = subject;
		this.message = message;
		this.to.addAll(Arrays.asList(splitByComma(toList)));
		this.cc.addAll(Arrays.asList(splitByComma(ccList)));
	}
 
	
        //getters and setters not mentioned for brevity
 
	private String[] splitByComma(String toMultiple) {
		String[] toSplit = toMultiple.split(",");
		return toSplit;
	}
 
	public String getToAsList() {
		return AppUtil.concatenate(this.to, ",");
	}
}
