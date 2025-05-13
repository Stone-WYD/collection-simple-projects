package com.jgdsun.ba.utils;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;


/**
 * 3DES加密工具类
 */
public class Des3 {
	// 密钥
	//private final static String secretKey = "382E383838384646";
	// 向量
	//private final static String iv = "01234567";
	// 加解密统一使用的编码方式
	private final static String encoding = "utf-8";
	
	//private static byte[] secretKey = new byte[8];
	
	public static void setKey(String key)
	{
		
		/*for(int i=0;i<key.length()/2;i++)
		{
			secretKey[i] = (byte)(Integer.parseInt(key.substring(i*2,i*2+2),16));
		}*/
		
	}

	/**
	 * 3DES加密
	 * 
	 * @param plainText 普通文本
	 * @return
	 * @throws Exception 
	 */
	/*
	public static String encode(String plainText) throws Exception {
		Key deskey = null;
		
		DESKeySpec spec = new DESKeySpec(secretKey);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DES");
		deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
		
		
		
		IvParameterSpec ips = new IvParameterSpec(new byte[8]);
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		
		
		byte[] bytes2 = new byte[plainText.length()/2];
		for(int i=0;i<plainText.length()/2;i++)
		{
			bytes2[i] = (byte)(Integer.parseInt(plainText.substring(i*2,i*2+2),16));
		}
		
		
		byte[] encryptData = cipher.doFinal(bytes2);
		
		return bytesToHexString(encryptData);
		
		//return Base64.encode(encryptData);
	}
	*/
	   /**
	 	 * 3DES加密
	 	 * 
	 	 * @param src 需加密数据
	 	 * @param key 秘钥
	 	 * @return 返回 Base64 处理后的加密数据
	 	 * @throws Exception 
	 	 */
	 	public static String encodeECBBase64(byte[] src, byte[] key) throws Exception {
	 	
	 		return Base64.encode(encodeECB(src,key));
	 	}
	
	
     /**
 	 * 3DES加密
 	 * 
 	 * @param src 普通文本
 	 * @return
 	 * @throws Exception 
 	 */
 	public static byte[] encodeECB(byte[] src, byte[] key) throws Exception {
 		
 		
 		/*
 		 int length = src.length;  
 	        int x = length % 8;  
 	        int addLen = 0;  
 	        if (x != 0) {  
 	            addLen = 8 - length % 8;  
 	        }  
 	        
 	        byte[] data = new byte[length + addLen];  
 	        System.arraycopy(src, 0, data, 0, length);
 		
 		*/
 		

 		
 	//	byte ba[] = {0,0,0,0,0,0,0,0};
 	//	IvParameterSpec ips = new IvParameterSpec(ba);//new byte[8]);
 	//	cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
 		
 		
 		
 	       Key deskey = null;
 	       deskey = new SecretKeySpec(key, "DESede"); 
 	       
 			
 		
 		 SecretKeySpec skey = new SecretKeySpec(deskey.getEncoded(), "DESede"); 
 		 
 	     Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding"); 
 	     cipher.init(Cipher.ENCRYPT_MODE, skey); 
 	     byte[] encryptedData = cipher.doFinal(src); 
 	    
 		return encryptedData;
 		
 		//return Base64.encode(encryptData);
 	}
 	
 	//keybyte为加密密钥，长度为24字节      
    //src为加密后的缓冲区  
    public static byte[] decryptMode(byte[] src,byte[] keybyte){  
        try {  
            //生成密钥  
            SecretKey deskey = new SecretKeySpec(keybyte, "DESede");  
            //解密  
            Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");  
            c1.init(Cipher.DECRYPT_MODE, deskey);  
            return c1.doFinal(src);  
        } catch (java.security.NoSuchAlgorithmException e1) {  
            // TODO: handle exception  
            e1.printStackTrace();  
        }catch(javax.crypto.NoSuchPaddingException e2){  
            e2.printStackTrace();  
        }catch(Exception e3){
            e3.printStackTrace();  
        }  
        return null;          
    }  
     
    /**
 	 * 3DES解密
 	 * 
 	 * @param src Base64文本
 	 * @param key 秘钥
 	 * @return 返回 Base64 编码 解密后的数据 
 	 * @throws Exception 
 	 */
 	public static byte[] decodeECBBase64(String src, byte[] key) throws Exception {
 		
 		return decodeECB(Base64.decode(src), key);
 	}
    
 	/**
 	 * 3DES解密
 	 * 
 	 * @param src 普通文本
 	 * @return
 	 * @throws Exception 
 	 */
 	public static byte[] decodeECB(byte[] src, byte[] key) throws Exception {
 		
 		
 		 
 		
 		
 		

 		
 	//	byte ba[] = {0,0,0,0,0,0,0,0};
 	//	IvParameterSpec ips = new IvParameterSpec(ba);//new byte[8]);
 	//	cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
 		
 		
 		
 	       Key deskey = null;
 	       deskey = new SecretKeySpec(key, "DESede"); 
 	       
 			
 		
 		 SecretKeySpec skey = new SecretKeySpec(deskey.getEncoded(), "DESede"); 
 		 
 	     Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding"); 
 	     cipher.init(Cipher.DECRYPT_MODE, skey); 
 	     byte[] encryptedData = cipher.doFinal(src); 
 	    
 		return encryptedData;
 		
 		//return Base64.encode(encryptData);
 	}
 	 /**
     * 自定义一个key
     * @param keyRule
     */ 
    public static byte[] getKey(String keyRule) { 
        Key key = null; 
        byte[] keyByte = keyRule.getBytes(); 
        // 创建一个空的八位数组,默认情况下为0  
        byte[] byteTemp = new byte[8]; 
        // 将用户指定的规则转换成八位数组  
        for (int i = 0; i < byteTemp.length && i < keyByte.length; i++) { 
            byteTemp[i] = keyByte[i]; 
        } 
        key = new SecretKeySpec(byteTemp, "DES"); 
        return key.getEncoded(); 
    } 
	/**
	 * 3DES加密
	 * 
	 * @param src 普通文本
	 * @return
	 * @throws Exception 
	 */
	public static byte[] encode(byte[] src, byte[] key) throws Exception {
		
		
		 int length = src.length;  
	        int x = length % 8;  
	        int addLen = 0;  
	        if (x != 0) {  
	            addLen = 8 - length % 8;  
	        }  
	        
	        byte[] data = new byte[length + addLen];  
	        System.arraycopy(src, 0, data, 0, length);
		
		
		
		Key deskey = null;
		
		DESKeySpec spec = new DESKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
		deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		
		
		byte ba[] = {0,0,0,0,0,0,0,0};
		IvParameterSpec ips = new IvParameterSpec(ba);//new byte[8]);
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		
		
		
		
		byte[] encryptData = cipher.doFinal(data);
		
		return encryptData;
		
		//return Base64.encode(encryptData);
	}
	public static byte[] XOR(byte[] edata, byte[] temp) {
        byte [] result = new byte[8];
        for (int i = 0 , j = result.length ; i < j; i++) {
            result [i] = (byte) (edata[i] ^ temp[i]);
        }
        return result;
    }
	
	 /** 
     * mac计算,数据不为8的倍数，需要补0，将数据8个字节进行异或，再将异或的结果与下一个8个字节异或，一直到最后，将异或后的数据进行DES计算 
     *  
     * @param key 
     * @param Input 
     * @return 
     */  
    public static byte[] clacMac( byte[] Input, byte[] key) {  
    	
    	
    	
    	 
    	  	//进行分组
        int group = (Input.length + (8 - 1)) / 8;
        //偏移量
        int offset = 0 ;
        //输入计算数据
        byte[] edata = null;
        for(int i = 0 ; i < group; i++){
            byte[] temp = new byte[8];
            if(i != group - 1){ 
                System.arraycopy(Input, offset, temp, 0, 8);
                offset += 8;
            }else{//只有最后一组数据才进行填充0x00
                System.arraycopy(Input, offset, temp, 0, Input.length - offset);
            }


            if(i != 0){//只有第一次不做异或
                temp = XOR(edata,temp);
            }
            try {
				edata = encode(temp,key);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    	
    	return edata;
    	//return bytesToHexString(edata);
    	
    }  
    
    /*
    public static byte byteXOR(byte src, byte src1) {  
        return (byte) ((src & 0xFF) ^ (src1 & 0xFF));  
    }  
  
    public static byte[] bytesXOR(byte[] src, byte[] src1) {  
        int length = src.length;  
        if (length != src1.length) {  
            return null;  
        }  
        byte[] result = new byte[length];  
        for (int i = 0; i < length; i++) {  
            result[i] = byteXOR(src[i], src1[i]);  
        }  
        return result;  
    }  
*/
	//字符序列转换为16进制字符串
	private static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		char[] buffer = new char[2];
		for (int i = 0; i < src.length; i++) {
			buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
			buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
			//System.out.println(buffer);
			stringBuilder.append(buffer);
		}
		return stringBuilder.toString();
	}
	/**
	 * 3DES解密
	 * 
	 * @param src 加密文本
	 * @return
	 * @throws Exception
	 */
	public static byte[] decode(byte[] src, byte[] key) throws Exception {
		Key deskey = null;
		DESKeySpec spec = new DESKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DES");
		deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
		
		
		
		IvParameterSpec ips = new IvParameterSpec(new byte[8]);
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

		byte[] decryptData = cipher.doFinal(src);

		return decryptData;
		//return bytesToHexString(decryptData);
	}
	
	/**
	 * 解码秘钥
	 * @param len
	 * @param data
	 * @return
	 */
	public static byte[] decryptGrant(int len, byte data[])
	{
		byte rdata[] = new byte[len];
		byte key = (byte)0xAC;
		for (int i = 0;i < len;i++)
		{
			rdata[i] = (byte)(data[i] ^ key);
		}
		return rdata;
	}
	
	
}
