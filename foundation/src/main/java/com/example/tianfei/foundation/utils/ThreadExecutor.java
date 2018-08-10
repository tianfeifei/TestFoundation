/*
 *     Copyright (c) 2016 Meituan Inc.
 *
 *     The right to copy, distribute, modify, or otherwise make use
 *     of this software may be licensed only pursuant to the terms
 *     of an applicable Meituan license agreement.
 *
 */

package com.example.tianfei.foundation.utils;


import com.example.tianfei.foundation.log.LogUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadExecutor {
    private static final Object lock = new Object();
    private static ExecutorService executorService;

    public static void destroyExecutorService() {
        if (executorService != null) {
            synchronized (lock) {
                if (executorService != null) {
                    LogUtil.i("ThreadExecutor","shutdown thread pool");
                    executorService.shutdown();
                    executorService = null;
                }
            }
        }
    }

    public static void execute(Runnable r) {
        if (executorService == null) {
            synchronized (lock) {
                if (executorService == null) {
                    LogUtil.i("ThreadExecutor","create thread pool");
                    executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 10L, TimeUnit.SECONDS,
                            new SynchronousQueue<Runnable>());
                }
            }
            executorService.execute(r);
        }
    }
}
