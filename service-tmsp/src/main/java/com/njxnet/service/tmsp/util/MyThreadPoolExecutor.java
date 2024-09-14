package com.njxnet.service.tmsp.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class MyThreadPoolExecutor extends ThreadPoolExecutor {

    // 这个自定义的线程池主要是为了处理异常，防止线程运行抛出异常无法被捕获处理
    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                                RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public void execute(Runnable command) {
        RunWithLogHandle runWithLogHandle = new RunWithLogHandle(command);
        super.execute(runWithLogHandle);
    }

    private static class RunWithLogHandle implements Runnable {

        Runnable runnable;

        public RunWithLogHandle(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            try {
                runnable.run();
            } catch (Exception e) {
                log.error(Thread.currentThread().getName() + "线程执行过程中遇到错误，异常信息为：{}", e.getMessage());
            }
        }
    }

    /*@Override
    protected void afterExecute(Runnable r, Throwable t) {
        FutureTask futureTask = (FutureTask) r;
        try {
            futureTask.get();
        } catch (Exception e) {
            log.error("MyThreadPoolExecutor occur error:{}", e.getLocalizedMessage());
        }
    }*/


}
