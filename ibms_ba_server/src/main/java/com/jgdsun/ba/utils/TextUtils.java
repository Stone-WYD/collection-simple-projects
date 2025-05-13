package com.jgdsun.ba.utils;

public class TextUtils {
	
	public static boolean isEmpty(String str)
	{
		if(str==null)return true;
		if(str.contentEquals(""))return true;
		return false;
		
	}

}
