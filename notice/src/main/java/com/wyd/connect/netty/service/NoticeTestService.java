package com.wyd.connect.netty.service;

import cn.hutool.core.util.StrUtil;
import com.wyd.connect.netty.NoticeClient;
import com.wyd.connect.netty.StringClientHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class NoticeTestService {

    private static final Logger logger = LoggerFactory.getLogger(NoticeTestService.class);

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup(2);

        LoggingHandler LOGGING_HANDLER = new LoggingHandler();


        try {
            Channel channel = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(LOGGING_HANDLER);
                            pipeline.addLast(new StringDecoder(Charset.forName("GBK")));
                            pipeline.addLast(new StringEncoder(StandardCharsets.UTF_8));
                            // 监听客户端连接是否还正常
                            pipeline.addLast(new IdleStateHandler(60*10,0,0));
                            // pipeline.addLast(new IdleStateHandler(10,0,0));
                            pipeline.addLast(new ChannelDuplexHandler(){
                                @Override
                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                    IdleStateEvent event = (IdleStateEvent) evt;
                                    // 触发读空闲事件
                                    if (event.state() == IdleState.READER_IDLE) {
                                        logger.debug("已经有 10min 没读到客户端的数据了 ");
                                        // logger.debug("已经有 10s 没读到客户端的数据了 ");
                                        ctx.channel().close();
                                    }
                                }
                            });
                            // 处理业务
                            ch.pipeline().addLast("test", new SimpleChannelInboundHandler<String> () {

                                @Override
                                protected void channelRead0(ChannelHandlerContext context, String s) throws Exception {
                                    if (StrUtil.isNotEmpty(s)) {
                                        context.writeAndFlush("您有 1 条待审核任务，");
                                    } else {
                                        context.writeAndFlush("服务端没有接收到数据！");
                                    }
                                }
                            });

                        }
                    }).bind(8888).sync().channel();
            channel.closeFuture().sync();
        }catch (Exception e){
            logger.error("server error", e);
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
