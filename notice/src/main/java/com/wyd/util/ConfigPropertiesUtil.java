package com.wyd.util;

import com.wyd.connect.netty.NoticeClient;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * @author xh
 * @date 2024-07-02
 * @Description:
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class ConfigPropertiesUtil {
    private static final Logger logger = LoggerFactory.getLogger(ConfigPropertiesUtil.class);

    @Getter
    private final static Properties prop = new Properties();

    static {
        try {
            File file = new File("config.properties");
            if (!file.exists()) file.createNewFile();
            InputStream input =  new FileInputStream("config.properties");
            // 加载properties文件
            prop.load(input);
        } catch (IOException ex) {
            logger.error("读取配置异常", ex);
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
            logger.error("保存配置文件失败", io);
        }
    }

}
