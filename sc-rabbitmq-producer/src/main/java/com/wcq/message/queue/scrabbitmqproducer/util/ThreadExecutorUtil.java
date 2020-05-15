package com.wcq.message.queue.scrabbitmqproducer.util;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadExecutorUtil {

    public static ThreadPoolExecutor get(){
        SynchronousQueue executorQueue = new SynchronousQueue();
        return new ThreadPoolExecutor(10, 20, 60L, TimeUnit.SECONDS,
                executorQueue);
    }

}
