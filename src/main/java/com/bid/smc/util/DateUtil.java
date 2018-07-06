package com.bid.smc.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bid.smc.constants.SmcConstants;

public class DateUtil {


	/**
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date)   {
		if (date == null) {
			return null;
		}
		DateFormat df = new SimpleDateFormat(SmcConstants.DATE_FORMAT);
		return  df.format(date);
	}
	
	/**
	 * @param date
	 * @return
	 */
	public static Date formatDateTime(Date date) {
		if (date == null) {
			return null;
		}
		DateFormat df = new SimpleDateFormat(SmcConstants.DATE_FORMAT);
		try {
			return df.parse(df.format(date));
		} catch (ParseException e) {
			
		}
		return date;
	}
	
	
	public static Date parseDate(String date) {
		if (date == null) {
			return null;
		}
		try {
			return new SimpleDateFormat(SmcConstants.DATE_FORMAT).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean compareDate(Date d1, Date d2) {
		return d1.after(d2);
	}
	
	
	

}

