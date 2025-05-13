package com.jgdsun.ba.utils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ws
 *
 */
public class TimeUtils {




	/**
	 * 获取时间 yyyyMMddHHmmss
	 * @return
	 */
	public static String getNowTimeDayHourMinuteSecond()
	{
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyyMMddHHmmss");
		return dateformat.format(new Date());
	}

	/**
	 * 获取时间 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getNowTimeDayHourMinuteSecond2()
	{
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateformat.format(new Date());
	}


}
