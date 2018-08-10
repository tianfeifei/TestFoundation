package com.example.tianfei.foundation.log;

import android.text.TextUtils;

/**
 * 日志操作类
 * Created by zhangyunfei on 16/7/21.
 */
public class LogUtil {
    public static boolean isEnable = true;//是否开启日志
    //    private static LogPersenter logPersenter = new FileLogPersentoer();
    private static GroupLogger mainLogger;

    static {
        mainLogger = new GroupLogger();
        mainLogger.addChildLogger(new LogcatLogger());
    }

    /**
     * 获得 配置好的多个子 logger
     * @return
     */
    public static GroupLogger getAppenderGroup() {
        return mainLogger;
    }

    /**
     * 打印当前方法所在的调用堆栈
     */
    public static void printStackTrace(String tag, String msg) {
        RuntimeException e = new RuntimeException("打印调用堆栈");
        e.fillInStackTrace();
        e(tag, msg, e);
    }

    public static void d(String tag, String msg) {
        if (!isEnable) return;
        if (TextUtils.isEmpty(msg)) return;
        mainLogger.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (!isEnable) return;
        if (TextUtils.isEmpty(msg)) return;
        mainLogger.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (!isEnable) return;
        if (TextUtils.isEmpty(msg)) return;
        mainLogger.e(tag, msg);
    }

    public static void e(String tag, String msg, Exception ex) {
        if (!isEnable) return;
        if (TextUtils.isEmpty(msg)) return;
        mainLogger.e(tag, msg, ex);
    }


//    private static class FileLogPersentoer implements LogPersenter {
//        public void d(String tag, String msg) {
//            if (!isEnable) return;
//
//            if (Log.isLoggable(LogTag.OBD, Log.DEBUG))
//                Log.d(LogTag.OBD, msg);
//        }
//
//        public void i(String tag, String msg) {
//            if (!isEnable) return;
//
//            if (Log.isLoggable(LogTag.OBD, Log.INFO))
//                Log.i(LogTag.OBD, msg);
//        }
//
//        public void e(String tag, String msg) {
//            if (!isEnable) return;
//
//            if (Log.isLoggable(LogTag.OBD, Log.ERROR))
//                Log.e(LogTag.OBD, msg);
//        }
//
//        public void e(String tag, String msg, Exception ex) {
//            if (!isEnable) return;
//
//            if (Log.isLoggable(LogTag.OBD, Log.ERROR))
//                Log.e(LogTag.OBD, msg, ex);
//        }
//    }
}
