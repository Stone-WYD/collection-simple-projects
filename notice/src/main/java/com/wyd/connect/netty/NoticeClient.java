package com.wyd.connect.netty;

import cn.hutool.core.util.ObjectUtil;
import com.wyd.util.ConfigPropertiesUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import javafx.scene.control.TextArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static com.wyd.connect.netty.common.NettyConstants.MESSAGE_TYPE_PING;
import static com.wyd.connect.netty.common.NettyConstants.getContent;

public class NoticeClient {

    private static final Logger logger = LoggerFactory.getLogger(NoticeClient.class);

    private final TrayIcon trayIcon;

    private TextArea logArea;

    NioEventLoopGroup group = new NioEventLoopGroup();
    LoggingHandler LOGGING_HANDLER = new LoggingHandler();
    StringClientHandler stringClientHandler;
    Bootstrap bootstrap = new Bootstrap();

    public NoticeClient(TrayIcon trayIcon, TextArea logArea) {
        if (ObjectUtil.isNull(trayIcon)) {
            throw new RuntimeException("Netty Client 创建失败！");
        }
        this.trayIcon = trayIcon;
        this.logArea = logArea;
        stringClientHandler = new StringClientHandler(trayIcon);
        // 初始化 bootstrap 配置
        bootstrap.channel(NioSocketChannel.class)
                .group(group)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) // 连接超时时间
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        // 最长长度
                        pipeline.addLast(new LineBasedFrameDecoder(5000));
                        // 解析消息内容
                        pipeline.addLast(new StringEncoder(Charset.forName("GBK")));
                        pipeline.addLast(new StringDecoder(StandardCharsets.UTF_8));
                        pipeline.addLast(LOGGING_HANDLER);

                        // 用来判断是不是 读或者写空闲时间过长 避免有假连接
                        // 5min 内如果没有向服务器写数据，会触发一个IdeState#WRITER_IDIE 事件
                        //                                         读空闲时间             写空闲时间             所有(读写)空闲时间
                        pipeline.addLast(new IdleStateHandler(0, 60 * 5, 0));
                        // pipeline.addLast(new IdleStateHandler(0, 5, 0));
                        pipeline.addLast(new ChannelDuplexHandler() {
                            // 用来出发特殊事件
                            @Override
                            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
                                IdleStateEvent event = (IdleStateEvent) evt;
                                if (IdleState.WRITER_IDLE == event.state()) {
                                    // 每隔五分钟 发送一个心跳包
                                    ctx.writeAndFlush(getContent(MESSAGE_TYPE_PING));
                                }
                            }
                        });
                        // 添加业务handler
                        pipeline.addLast(stringClientHandler);
                    }
                });
    }

    public void setLogArea(TextArea logArea) {
        this.logArea = logArea;
    }

    private Channel channel;
    public void start(){
        if (ObjectUtil.isNotNull(channel)) {
            // 已有连接，先关闭已有连接
            try {
                channel.close().sync();
            } catch (InterruptedException e) {
                logger.error("关闭已有连接异常！", e);
            }
        }

        try {
            // 再建立新的连接
            String url = ConfigPropertiesUtil.getProperty("url");
            String[] split = url.split(":");
            if (split.length != 2) {
                throw new Exception("url格式错误");
            }
            ChannelFuture connectFuture = bootstrap.connect(split[0], Integer.parseInt(split[1]));
            connectFuture.addListener(future -> {
                if (future.isSuccess()) {
                    if (logArea != null) {
                        logArea.appendText("连接成功！\n");
                    }
                } else {
                    if (logArea != null) {
                        logArea.appendText("连接失败！，请联系管理员！\n");
                    }
                    trayIcon.displayMessage("连接状态", "通知程序与服务器连接失败，请联系管理员！", TrayIcon.MessageType.ERROR);
                }
            });
            channel = connectFuture.sync().channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            logger.error("Netty客户端启动失败！请检查参数配置", e);
            if (ObjectUtil.isNotNull(logArea)) {
                logArea.appendText("Netty客户端启动失败！请检查参数配置\r\n");
            }
        } /*finally {
            group.shutdownGracefully();
        }*/
    }

    public void stop() {
        if (ObjectUtil.isNotNull(group)) {
            group.shutdownGracefully();
        }
    }

}
