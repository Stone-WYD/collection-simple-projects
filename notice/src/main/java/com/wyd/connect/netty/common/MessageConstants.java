package com.wyd.connect.netty.common;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.wyd.util.ConfigPropertiesUtil;

public class MessageConstants {

    // message type 0.心跳包，1.连接建立成功,2.定时任务获取待办任务消息

    public static final String MESSAGE_TYPE_PING = "0";
    public static final String MESSAGE_TYPE_START = "1";
    public static final String MESSAGE_TYPE_TASK = "2";

    public static final String PROGRAM_ID = UUID.fastUUID().toString();

    public static String generateClientMessage(String messageType) {
        String userName = ConfigPropertiesUtil.getProperty("userName");
        return userName + "," + PROGRAM_ID + "," + messageType + "\n";
    }

    public static String getClientMessageUserName(String clientMessage) {
        if (StrUtil.isNotEmpty(clientMessage)) {
            return clientMessage.split(",")[0];
        } else return "";
    }

    public static String getClientMessageProgramId(String clientMessage) {
        if (StrUtil.isNotEmpty(clientMessage)) {
            return clientMessage.split(",")[1];
        } else return "";
    }

    public static String getClientMessageType(String clientMessage) {
        if (StrUtil.isNotEmpty(clientMessage)) {
            return clientMessage.split(",")[2];
        } else return "";
    }


}
