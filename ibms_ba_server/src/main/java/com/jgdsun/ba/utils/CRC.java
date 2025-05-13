package com.jgdsun.ba.utils;

public class CRC {
    /**
     * CRC-CCITT(Kermit)��֤ģʽ
     * @param str 
     * @return
     */
	public String CRC_CCITT_Kermit(String str) {
		int j, b, rrrc, c, i;
		String tmpBalance;
		int k;
		rrrc = 0;
		tmpBalance = str;
		int tmpInt, CharInt;
		String tmpChar, tmpStr;
		tmpStr = "";
		int High;
		int Low;

	for (j = 1; j <= 3; j++) {

			if (Character.isDigit(tmpBalance.charAt(2 * j - 2))) {
				High = Integer.parseInt(tmpBalance.charAt(2 * j - 2) + "");

			} else {
				High = 0;

			}
			if (Character.isDigit(tmpBalance.charAt(2 * j - 1))) {
				Low = Integer.parseInt(tmpBalance.charAt(2 * j - 1) + "");

			} else {

				Low = 0;
			}

			High = (High & 0xff) << 4;

			High = High | Low;

			k = High;

			for (i = 1; i <= 8; i++) {
				c = rrrc & 1;
				rrrc = rrrc >> 1;
				if ((k & 1) != 0) {
					rrrc = rrrc | 0x8000;

				}
				if (c != 0) {

					rrrc = rrrc ^ 0x8408;
				}

				k = k >> 1;
			}
		}
		for (i = 1; i <= 16; i++) {
			c = rrrc & 1;
			rrrc = rrrc >> 1;

			if (c != 0) {

				rrrc = rrrc ^ 0x8408;
			}

		}
		c = rrrc >> 8;
		b = rrrc << 8;
		rrrc = c | b;
		tmpInt = rrrc;
		tmpStr = "";
		for (i = 1; i <= 4; i++) {
			tmpChar = "";
			CharInt = tmpInt % 16;
			if (CharInt > 9) {
				switch (CharInt) {
				case 10:
					tmpChar = "A";
					break;
				case 11:

					tmpChar = "B";
					break;
				case 12:
					tmpChar = "C";
					break;

				case 13:

					tmpChar = "D";
					break;

				case 14:

					tmpChar = "E";
					break;

				case 15:

					tmpChar = "F";
					break;

				}
			} else {

				tmpChar = Integer.toString(CharInt);
			}

			tmpInt = tmpInt / 16;
			tmpStr = tmpChar + tmpStr;

		}

		System.out.println("tmpStr:" + tmpStr);

		return tmpStr;

	}
	
	/**
	 * CRC-CCITT(XModem)
	 * CRC-CCITT(0xFFFF)  
	 * CRC-CCITT(0x1D0F) 
	 * У��ģʽ
	 * @param flag< XModem(flag=1) 0xFFFF(flag=2) 0x1D0F(flag=3)>
	 * @param str
	 * @return
	 */
	public String  CRC_CCITT( int flag,String str) { 
        int crc = 0x00;          // initial value
        int polynomial = 0x1021;   
        byte[] bytes=str.getBytes();
        
        switch(flag){
        case 1:
        	crc=0x00;
        	break;
        case 2:
        	crc=0xFFFF;
        	break;
        case 3:
        	crc=0x1D0F;
        	break;
        
        }
        for (int index = 0 ; index< bytes.length; index++) {
            byte b = bytes[index];
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b   >> (7-i) & 1) == 1);
                boolean c15 = ((crc >> 15    & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) crc ^= polynomial;
             }
        }
        crc &= 0xffff;
        str = Integer.toHexString(crc);
        return str;
        
    }
	/**
	 * CRC-CCITT(XModem)
	 * CRC-CCITT(0xFFFF)  
	 * CRC-CCITT(0x1D0F) 
	 * У��ģʽ
	 * @param flag< XModem(flag=1) 0xFFFF(flag=2) 0x1D0F(flag=3)>
	 * @param str
	 * @return
	 */
	public String  CRC_CCITT_Bytes( int flag, byte[] bytes) { 
        int crc = 0x00;          // initial value
        int polynomial = 0x1021;   
        //byte[] bytes=str.getBytes();
        
        switch(flag){
        case 1:
        	crc=0x00;
        	break;
        case 2:
        	crc=0xFFFF;
        	break;
        case 3:
        	crc=0x1D0F;
        	break;
        
        }
        for (int index = 0 ; index< bytes.length; index++) {
            byte b = bytes[index];
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b   >> (7-i) & 1) == 1);
                boolean c15 = ((crc >> 15    & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) crc ^= polynomial;
             }
        }
        crc &= 0xffff;
     String   str = Integer.toHexString(crc);
        return str;
        
    }
	/**
	 * CRC-CCITT(XModem)
	 * CRC-CCITT(0xFFFF)  
	 * CRC-CCITT(0x1D0F) 
	 * У��ģʽ
	 * @param flag< XModem(flag=1) 0xFFFF(flag=2) 0x1D0F(flag=3)>
	 * @param str
	 * @return
	 */
	public String  CRC_CCITT_StringBufferToBytes( int flag, StringBuffer str) { 
        int crc = 0x00;          // initial value
        int polynomial = 0x1021;

        byte[] bytes = new byte[str.length()/2];
        
		for(int i=0;i<str.length()/2;i++)
		{
			String ba = str.substring(i*2,i*2+2);
			bytes[i] = (byte)(Integer.parseInt(ba,16));
			ba = null;
		}
        
        
        
        switch(flag){
        case 1:
        	crc=0x00;
        	break;
        case 2:
        	crc=0xFFFF;
        	break;
        case 3:
        	crc=0x1D0F;
        	break;
        
        }
        for (int index = 0 ; index< bytes.length; index++) {
            byte b = bytes[index];
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b   >> (7-i) & 1) == 1);
                boolean c15 = ((crc >> 15    & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) crc ^= polynomial;
             }
        }
        crc &= 0xffff;
     String   rstr = Integer.toHexString(crc);
        return rstr;
        
    }
	/**
	 * CRC-CCITT(XModem)
	 * CRC-CCITT(0xFFFF)  
	 * CRC-CCITT(0x1D0F) 
	 * У��ģʽ
	 * @param flag< XModem(flag=1) 0xFFFF(flag=2) 0x1D0F(flag=3)>
	 * @param str
	 * @return
	 */
	public String  CRC_CCITT_StringToBytes( int flag, String str) { 
        int crc = 0x00;          // initial value
        int polynomial = 0x1021;
         
        byte[] bytes = new byte[str.length()/2];
        
		for(int i=0;i<str.length()/2;i++)
		{
			String ba = str.substring(i*2,i*2+2);
			bytes[i] = (byte)(Integer.parseInt(ba,16));
			ba = null;
		}
        
        
        
        switch(flag){
        case 1:
        	crc=0x00;
        	break;
        case 2:
        	crc=0xFFFF;
        	break;
        case 3:
        	crc=0x1D0F;
        	break;
        
        }
        for (int index = 0 ; index< bytes.length; index++) {
            byte b = bytes[index];
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b   >> (7-i) & 1) == 1);
                boolean c15 = ((crc >> 15    & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) crc ^= polynomial;
             }
        }
        crc &= 0xffff;
     String   rstr = Integer.toHexString(crc);
        return rstr;
        
    }


	public static int  CRC_CCITT_int( int flag, byte bytes[],int len) {
		int crc = 0x00;          // initial value
		int polynomial = 0x1021;




		switch(flag){
			case 1:
				crc=0x00;
				break;
			case 2:
				crc=0xFFFF;
				break;
			case 3:
				crc=0x1D0F;
				break;

		}
		for (int index = 0 ; index< len; index++) {
			byte b = bytes[index];
			for (int i = 0; i < 8; i++) {
				boolean bit = ((b   >> (7-i) & 1) == 1);
				boolean c15 = ((crc >> 15    & 1) == 1);
				crc <<= 1;
				if (c15 ^ bit) crc ^= polynomial;
			}
		}
		crc &= 0xffff;

		return crc;

	}



	/**
	 * 计算CRC16校验码
	 *
	 * @param bytes
	 * @return
	 */
	public static String getCRCModbus(byte[] bytes) {
		int CRC = 0x0000ffff;
		int POLYNOMIAL = 0x0000a001;

		int i, j;
		for (i = 0; i < bytes.length; i++) {
			CRC ^= ((int) bytes[i] & 0x000000ff);
			for (j = 0; j < 8; j++) {
				if ((CRC & 0x00000001) != 0) {
					CRC >>= 1;
					CRC ^= POLYNOMIAL;
				} else {
					CRC >>= 1;
				}
			}
		}
		return Integer.toHexString(CRC);
	}

	/**
	 * 计算CRC16校验码 翻转顺序
	 *
	 * @param bytes
	 * @return
	 */
	public static String getCRCModbusFz(byte[] bytes) {
		int CRC = 0x0000ffff;
		int POLYNOMIAL = 0x0000a001;

		int i, j;
		for (i = 0; i < bytes.length; i++) {
			CRC ^= ((int) bytes[i] & 0x000000ff);
			for (j = 0; j < 8; j++) {
				if ((CRC & 0x00000001) != 0) {
					CRC >>= 1;
					CRC ^= POLYNOMIAL;
				} else {
					CRC >>= 1;
				}
			}
		}

		String str = Integer.toHexString(CRC).toUpperCase();

		while(str.length()<4)
		{
			str = "0"+str;
		}
		return str.substring(2,4)+str.substring(0,2);

	}

	/**
	 * 计算CRC16校验码 翻转顺序
	 *
	 * @param bytes
	 * @return
	 */
	public static String getCRCModbusFzByLen(byte[] bytes,int len) {
		int CRC = 0x0000ffff;
		int POLYNOMIAL = 0x0000a001;

		int i, j;
		for (i = 0; i < len; i++) {
			CRC ^= ((int) bytes[i] & 0x000000ff);
			for (j = 0; j < 8; j++) {
				if ((CRC & 0x00000001) != 0) {
					CRC >>= 1;
					CRC ^= POLYNOMIAL;
				} else {
					CRC >>= 1;
				}
			}
		}

		String str = Integer.toHexString(CRC).toUpperCase();

		while(str.length()<4)
		{
			str = "0"+str;
		}
		return str.substring(2,4)+str.substring(0,2);

	}
}
