package com.six.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
* @author gede
* @version date：2019年7月2日 下午4:56:00
* @description ：
*/
public class DateFormatUtil {
	public static String getFormatDate(Date date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
}
