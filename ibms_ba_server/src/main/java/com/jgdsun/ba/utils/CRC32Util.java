package com.jgdsun.ba.utils;

import java.util.zip.CRC32;

public class CRC32Util {
	
	CRC32 crc32;
	
	public long getCRC32(byte[] b)
	{
		long r = 0;
		try
		{
		CRC32 crc32 = new CRC32();
		crc32.update(b);
		r = crc32.getValue();
		}catch(Exception e)
		{
			
		}
		return r;
	}

}
