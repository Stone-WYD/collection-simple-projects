package com.wyd.connect.netty;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.wyd.util.ConfigPropertiesUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

import static com.wyd.connect.netty.common.NettyConstants.*;

@ChannelHandler.Sharable
public class StringClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(StringClientHandler.class);

    private final TrayIcon trayIcon;

    public StringClientHandler(TrayIcon trayIcon) {
        this.trayIcon = trayIcon;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //当被通知Channel是活跃的时候，发送一条消息
        Thread processThread = new Thread(() -> {
            // 连接成功，发送身份消息给服务端
            // fixme 定义消息格式："用户名,消息类型(0.定时任务发送，1.程序启动发送), ...(其他内容待定)"
            String content = getContent(MESSAGE_TYPE_START);
            ctx.writeAndFlush(Unpooled.copiedBuffer(content, Charset.forName("GBK")));
            // 服务端收到消息，启动定时任务按时给服务端发送消息
            String enableIntervalAlert = ConfigPropertiesUtil.getProperty("enableIntervalAlert");
            if ("true".equals(enableIntervalAlert)) {
                // 如果启用定时提醒
                int beginTime = 0;
                int endTime = 0;
                int interval = 0; // 分钟
                boolean check = true;
                try {
                    beginTime = Integer.parseInt(ConfigPropertiesUtil.getProperty("beginStr").replaceAll("-", ""));
                    endTime = Integer.parseInt(ConfigPropertiesUtil.getProperty("endStr").replaceAll("-", ""));
                    interval = Integer.parseInt(ConfigPropertiesUtil.getProperty("interval")) * 60;
                    if (interval < 10) interval = 10;
                } catch (Exception e) {
                    logger.error("参数配置有误", e);
                    check = false;
                    trayIcon.displayMessage(ConfigPropertiesUtil.getProperty("userName"), "参数配置有误，请检查后重新启用通知", TrayIcon.MessageType.ERROR);
                }
                if (check) {
                    int innerInterval = 0;
                    while (true) {
                        try {
                            // 每十分钟检测一次
                            Thread.sleep(60 * 10 * 1000);
                        } catch (InterruptedException e) {
                            logger.error("定时任务睡眠被打断！", e);
                        }
                        // 如果连接关闭了，则不再运行
                        if (ObjectUtil.isNotNull(ctx) && ObjectUtil.isNull(ctx.channel())
                                && !ctx.channel().isActive()) {
                            break;
                        }
                        innerInterval += 10;
                        if (innerInterval >= interval) {
                            innerInterval = 0;
                            LocalDateTime now = LocalDateTimeUtil.now();
                            int hour = now.getHour();
                            int minute = now.getMinute();
                            String hhmm = hour + "" + minute;
                            int inthhmm = Integer.parseInt(hhmm);
                            if (inthhmm >= beginTime && inthhmm <= endTime) {
                                // 该发送通知了
                                String taskContent = getContent(MESSAGE_TYPE_TASK);
                                ctx.writeAndFlush(Unpooled.copiedBuffer(taskContent, Charset.forName("GBK")));
                            }
                        }
                    }
                }
            }
        });
        processThread.setDaemon(false);
        processThread.start();
    }

    // 只要接收到服务端消息，则直接提示到 windows 任务栏
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) {
        String body = (String) msg;
        trayIcon.displayMessage(ConfigPropertiesUtil.getProperty("userName"), body, TrayIcon.MessageType.INFO);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("netty连接异常: ", cause);
        trayIcon.displayMessage(ConfigPropertiesUtil.getProperty("userName"), "失去与主机的连接", TrayIcon.MessageType.ERROR);
        ctx.close();
    }
}
