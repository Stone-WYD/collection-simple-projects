package com.njxnet.service.tmsp.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class CustomizedThreadPoolExecutor extends ThreadPoolExecutor {

    // 私有构造函数，防止外部直接创建实例
    // 这个自定义的线程池主要是为了处理异常，防止线程运行抛出异常无法被捕获处理
    private CustomizedThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                         BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                                         RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        FutureTask futureTask = (FutureTask) r;
        try {
            futureTask.get();
        } catch (Exception e) {
            log.error("CustomizedThreadPoolExecutor occur error:{}", e.getLocalizedMessage());
        }
    }

    // 使用内部枚举类来实现单例
    private enum SingletonEnum {
        INSTANCE;

        // 单例对象
        private final CustomizedThreadPoolExecutor singleton;

        // JDK 1.5 起提供的枚举创建单例的简洁方法
        SingletonEnum() {
            // 给线程命名
            ThreadFactory threadFactory = new ThreadFactoryBuilder()
                    .setNameFormat("myThreadPoolExecutor-worker-%d").build();
            // 通过核心数确定线程数
            int processors = 1;
            singleton =  new CustomizedThreadPoolExecutor(processors,
                    processors * 2,
                    0L,
                    TimeUnit.MINUTES,
                    new LinkedBlockingDeque<>(1000),
                    threadFactory,
                    new AbortPolicy());
        }

        public CustomizedThreadPoolExecutor getInstance() {
            return singleton;
        }
    }

    // 提供公有的静态方法，用于获取单例对象
    public static CustomizedThreadPoolExecutor getInstance() {
        // 枚举类是静态的，在第一次使用时会被 jvm 加载，可以保证只被创建一次且避免反射
        return SingletonEnum.INSTANCE.getInstance();
    }

}
