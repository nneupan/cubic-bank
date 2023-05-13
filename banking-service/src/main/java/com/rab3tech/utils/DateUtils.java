package com.rab3tech.utils;

import java.sql.Timestamp;
import java.util.Date;

public class DateUtils {
	
	public static  Timestamp getCurrentDate(){
		return new Timestamp(new Date().getTime());
	}

}
