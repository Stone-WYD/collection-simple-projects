package com.wyd.util;

import java.io.*;
import java.util.Properties;

/**
 * @author xh
 * @date 2024-07-02
 * @Description:
 */
public class ConfigPropertiesUtil {

    private final static Properties prop = new Properties();

    static {
        try {
            File file = new File("config.properties");
            if (!file.exists()) file.createNewFile();
            InputStream input =  new FileInputStream("config.properties");
            // 加载properties文件
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

    public static void setProp(String key, String value) {
        prop.setProperty(key, value);
    }

    public static void saveProp() {
        try {
            File file = new File("config.properties");
            if (!file.exists()) file.createNewFile();
            FileOutputStream output = new FileOutputStream("config.properties");
            prop.store(output, null); // 第二个参数是注释，可选
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static Properties getProp() {
        return prop;
    }

}
