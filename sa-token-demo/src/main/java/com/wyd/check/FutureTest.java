package com.wyd.check;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureTest {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Boolean> future = executor.submit(() -> {
            System.out.println("休眠2s");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + ":" + "马上要抛出异常了！");
            System.out.println(1 / 0);
            return true;
        });

        try {
           future.get();
        } catch (InterruptedException e) {
            System.out.println("等待子线程执行完了...");
            System.out.println("子线程被打断了");
            executor.shutdown();
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            System.out.println("等待子线程执行完了...");
            System.out.println("子线程运行时抛出了异常");
            executor.shutdown();
            throw new RuntimeException(e);
        }
    }
}
