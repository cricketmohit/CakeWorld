package com.cakeworld.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static String getDateFormat(Date orderDate) { 
		SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd, yyyy");
		return df.format(orderDate);
		}

}
