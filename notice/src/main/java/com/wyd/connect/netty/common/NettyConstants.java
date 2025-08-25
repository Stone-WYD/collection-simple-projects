package com.wyd.connect.netty.common;

import cn.hutool.core.lang.UUID;
import com.wyd.util.ConfigPropertiesUtil;

public class NettyConstants {

    // message type 0.task，1.start,2.心跳包
    public static final String MESSAGE_TYPE_TASK = "0";

    public static final String MESSAGE_TYPE_START = "1";

    public static final String MESSAGE_TYPE_PING = "2";

    public static final String DEVICE_ID = UUID.fastUUID().toString();


    public static String getContent(String messageType) {
        String userName = ConfigPropertiesUtil.getProperty("userName");
        return userName + "," + DEVICE_ID + "," + messageType + "\n";
    }
}
