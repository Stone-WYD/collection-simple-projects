package com.jgdsun.ba.utils;


import java.io.*;
import java.util.Properties;


public class PropertiesUtil {
    private String properiesName = "";

    public PropertiesUtil() {

    }
    public PropertiesUtil(String fileName) {
        this.properiesName = fileName;
    }
    public String readProperty(String key) {
        String value = "";
        InputStream is = null;
        try {
          //  is = PropertiesUtil.class.getClassLoader().getResourceAsStream(properiesName);

            is = new FileInputStream(new File(properiesName));

            Properties p = new Properties();
            p.load(is);
            value = p.getProperty(key);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return value;
    }

    public Properties getProperties() {
        Properties p = new Properties();
        InputStream is = null;
        try {
            //is = PropertiesUtil.class.getClassLoader().getResourceAsStream(properiesName);
            is = new FileInputStream(new File(properiesName));
            p.load(is);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return p;
    }

    public void writeProperty(String key, String value) {
        InputStream is = null;
        OutputStream os = null;
        Properties p = new Properties();
        try {




            //is = new FileInputStream(PropertiesUtil.class.getClassLoader().getResource(properiesName).getFile());
            is = new FileInputStream(new File(properiesName));
            p.load(is);
            System.out.println(p.getProperty(key));
            //os = new FileOutputStream(PropertiesUtil.class.getClassLoader().getResource(properiesName).getFile());
            os = new FileOutputStream(new File(properiesName));

            p.setProperty(key, value);
            p.store(os, key);
            os.flush();
            os.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (null != is)
                    is.close();
                if (null != os)
                    os.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }



}