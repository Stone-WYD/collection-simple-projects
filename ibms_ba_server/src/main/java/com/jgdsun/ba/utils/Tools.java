package com.jgdsun.ba.utils;

import java.math.BigDecimal;
import java.util.Random;

public class Tools {
	
	public static float getFloatByString(String str)
	{
		StringBuffer sb = new StringBuffer();
		for(int i=str.length()/2-1;i>=0;i--)
		{
			String ba = str.substring(i*2,i*2+2);
			//int ia = Integer.parseInt(b.substring(i*2,i*2+2));
			//System.out.println("ba "+ba);
			int ia = Integer.parseInt(ba,16);
			String sc = Integer.toBinaryString(ia);
			int len = sc.length();
			for(int j=0;j<8-len;j++)
			{
				sc = "0"+sc;
			}
			sb.append(sc);
		}
		float rf = getFloatUp(Float.intBitsToFloat(Integer.parseInt(sb.toString(), 2)),2);
		return rf;
	}
	public static String getStringByFloat(float fa)
	{
		String result="";
		int b = Float.floatToIntBits(fa);
		
		String hex = Integer.toHexString(b);
		//System.out.println(hex+"  "+hex.length());
		int len = hex.length();
		for(int i=0;i<8-len;i++)
		{
			hex = "0"+hex;
		}
		//System.out.println("hex2 "+hex);
		for(int i=3;i>=0;i--)
		{
			result+=hex.substring(i*2, i*2+2);
		}
		
		/*String str = Integer.toString(b, 2);
		 int len = str.length();
		for(int i=0;i<32-len;i++)
		{
			str = "0"+str;
		}
		System.out.println("str "+str);
		for(int k=str.length()-8;k>=0;k-=8)
		{
			String bstr = str.substring(k,k+8);
			int bi = Integer.parseInt(bstr, 2);
			result+=Integer.toHexString(bi);
		}*/
		
		return result;
		
	}
	public static float getFloatUp(float f,int k)
	{
		float f1 = 0;
		try
		{
		BigDecimal b = new BigDecimal(f);
		 f1 = b.setScale(k, BigDecimal.ROUND_HALF_UP).floatValue();
		}catch(NumberFormatException e)
		{
			
		}
		return f1;
	}

	public static int getFloatUpToInteger(double f)
	{
		float f1 = 0;
		try
		{
			BigDecimal b = new BigDecimal(f);
			f1 = b.setScale(0, BigDecimal.ROUND_HALF_UP).floatValue();
		}catch(NumberFormatException e)
		{

		}
		return (int)f1;
	}

	public static double getDoubleUp(double f,int k)
	{
		double f1 = 0;
		try
		{
			BigDecimal b = new BigDecimal(f);
			f1 = b.setScale(k, BigDecimal.ROUND_HALF_UP).doubleValue();
		}catch(NumberFormatException e)
		{

		}
		return f1;
	}


	public float bitsToFloat(String bits)
	{
		
		float f = 0;
		//String str="01000010110101100101000111001111";
		bits = bits.replaceAll(" ", "");
		int k = 1;
		if(bits.charAt(0)=='1')
		{
			k = -1;
		}
		String str2 = bits.substring(1, 9);
		
		int k2 = Integer.parseInt(str2,2);
		int k3 = k2-127;
		String str3 = "1"+bits.substring(9);
		
		if(k3<0)
		{
			for(int i=0;i<Math.abs(k3);i++)
			{
				str3="0"+str3;
			}
			str3 = str3.substring(0,1)+"."+str3.substring(1);
		}else if(k3>0)
		{
			if(k3>str3.length()-1)
			{
				for(int i=0;i<Math.abs(k3-(str3.length()-1));i++)
				{
					str3=str3+"0";
				}
				
			}else if(k3<str3.length()-1)
			{
				str3 = str3.substring(0,1+k3)+"."+str3.substring(1+k3);
				
			}
			
		}else
		{
			str3 = str3.substring(0,1)+"."+str3.substring(1);
		}
		int k4 = str3.indexOf(".");
		
		if(k4>0)
		{
			String str_1 = str3.substring(0,k4);
			
			String str_2 = str3.substring(k4+1);
			
			int ka = 0;
			for(int j=0;j<str_1.length();j++)
			{
				
				ka+= Integer.parseInt(""+str_1.charAt(j))*Math.pow(2, k4-j-1);
			}
			
			float ka2 = 0;
			
			for(int j=0;j<str_2.length();j++)
			{
				//System.out.println(Math.pow(2, k4-j-1));
				ka2+= Integer.parseInt(""+str_2.charAt(j))*Math.pow(2, -(j+1));
			}
			f = (ka+ka2)*k;
			
		}else
		{
			int len3 = str3.length();
			int ka = 0;
			for(int j=0;j<str3.length();j++)
			{
				ka+= Integer.parseInt(""+str3.charAt(j))*Math.pow(2, len3-j-1);
			}
			f = ka;
		}
		return f;
	}
	public float hexToFloat(String hex)
	{
		String hex1 = hex.substring(0,2);
		String hex2 = hex.substring(2,4);
		String hex3 = hex.substring(4,6);
		String hex4 = hex.substring(6,8);
		
		String bits = hexToBits(hex1)+hexToBits(hex2)+hexToBits(hex3)+hexToBits(hex4);
		return bitsToFloat(bits);
		
	}
	public String hexToBits(String hex)
	{
		int k = Integer.parseInt(hex,16);
		String str = Integer.toBinaryString(k);
		int len = str.length();
		for(int i=0;i<8-len;i++)
		{
			str = "0"+str;
		}
		return str;
	}
	public static String hexToBitsByClass(String hex)
	{
		int k = Integer.parseInt(hex,16);
		String str = Integer.toBinaryString(k);
		int len = str.length();
		for(int i=0;i<8-len;i++)
		{
			str = "0"+str;
		}
		return str;
	}

	/**
	 * 获取 len 位随机数字
	 * @return 数字字符串长度
	 */
	public  static String getNumString(int len)
	{
		String str = "";

		Random random = new Random();
		while(str.length()<len)
		{
			str+=random.nextInt(10);
		}
		return str;
	}

}
