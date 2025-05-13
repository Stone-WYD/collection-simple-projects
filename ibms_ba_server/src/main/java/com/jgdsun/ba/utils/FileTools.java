package com.jgdsun.ba.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class FileTools {

    public static byte[] getFileSHA1(File file) {

        try {
            return getHash(file, "SHA-256");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private static byte[] getHash(File file, String hashType) throws Exception {
        InputStream fis = new FileInputStream(file);
        byte buffer[] = new byte[1024];
        MessageDigest md5 = MessageDigest.getInstance(hashType);
        for (int numRead = 0; (numRead = fis.read(buffer)) > 0; ) {
            md5.update(buffer, 0, numRead);
        }
        fis.close();
        return md5.digest();
    }

}
