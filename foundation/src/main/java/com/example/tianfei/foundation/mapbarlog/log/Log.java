package com.example.tianfei.foundation.mapbarlog.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Log {

    /**
     * 日志级别
     */
    public static final int VERBOSE = 1;
    /**
     * 日志级别
     */
    public static final int DEBUG = 2;
    /**
     * 日志级别
     */
    public static final int INFO = 3;
    /**
     * 日志级别
     */
    public static final int WARN = 4;
    /**
     * 日志级别
     */
    public static final int ERROR = 5;
    /**
     * 时间格式
     */
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    private static BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
    private static ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, Log.workQueue);

    private static void smartPrint(int level, LogTagInterface tag, String msg, Throwable t, boolean printStackTrace) {

        if (!Log.isLoggable(tag, level)) {
            return;
        }

        Thread currentThread = Thread.currentThread();

        StackTraceElement[] stackTrace = currentThread.getStackTrace();

        int i = 4;

        StringBuilder sb = new StringBuilder();
        sb.append(currentThread.getId())//
                .append("|")//
                .append(Log.getCodeLocation(CodeLocationStyle.FIRST, null, stackTrace[i]))//
                .append("|")//
                .append(msg);

        String msgResult = sb.toString();
        Log.print(level, tag, msgResult, t);
        Log.filePrint(level, tag, msgResult, t);

        i++;

        for (; printStackTrace && (i < stackTrace.length); i++) {
            String s = Log.getCodeLocation(CodeLocationStyle.SUBSEQUENT, currentThread, stackTrace[i]).toString();
            Log.print(level, tag, s, null);
            Log.filePrint(level, tag, s, null);
        }

    }

    private static void print(int level, LogTagInterface tag, String msg, Throwable t) {
        switch (level) {
            case VERBOSE:
                if (t != null) {
                    android.util.Log.v("C_" + tag.getTagName(), msg, t);
                } else {
                    android.util.Log.v("C_" + tag.getTagName(), msg);
                }
                break;
            case DEBUG:
                if (t != null) {
                    android.util.Log.d("C_" + tag.getTagName(), msg, t);
                } else {
                    android.util.Log.d("C_" + tag.getTagName(), msg);
                }
                break;
            case INFO:
                if (t != null) {
                    android.util.Log.i("C_" + tag.getTagName(), msg, t);
                } else {
                    android.util.Log.i("C_" + tag.getTagName(), msg);
                }
                break;
            case WARN:
                if (t != null) {
                    android.util.Log.w("C_" + tag.getTagName(), msg, t);
                } else {
                    android.util.Log.w("C_" + tag.getTagName(), msg);
                }
                break;
            case ERROR:
                if (t != null) {
                    android.util.Log.e("C_" + tag.getTagName(), msg, t);
                } else {
                    android.util.Log.e("C_" + tag.getTagName(), msg);
                }
                break;
        }
    }

    private static StringBuilder getCodeLocation(CodeLocationStyle style, Thread currentThread, StackTraceElement stackTraceElement) {
        String className = stackTraceElement.getClassName();
        int lineNumber = stackTraceElement.getLineNumber();
        String methodName = stackTraceElement.getMethodName();
        String fileName = stackTraceElement.getFileName();
        StringBuilder sb = new StringBuilder();
        if (style.isAt()) {
            sb.append("	at ");
        }
        if (style.isSimpleClassName()) {
            sb.append(Log.getSimpleName(className));
        } else {
            sb.append(className);
        }
        sb.append(".").append(methodName).append("(").append(fileName).append(":").append(lineNumber).append(")");
        return sb;
    }

    private static String getSimpleName(String className) {
        String[] split = className.split("\\.");
        return split[split.length - 1];
    }

    /**
     * 是否可以打印日志
     *
     * @return
     */
    public static boolean isLoggable() {
        return LogManager.getInstance().isLog();
    }

    /**
     * 是否可以打印日志
     *
     * @param tag
     * @param level
     * @return
     */
    public static boolean isLoggable(String tag, int level) {
        return LogManager.getInstance().isLoggable(tag, level);
    }

    /**
     * 是否可以打印日志
     *
     * @param tag
     * @param level
     * @return
     */
    public static boolean isLoggable(LogTagInterface tag, int level) {
        return LogManager.getInstance().isLoggable(tag, level);
    }

    /**
     * 是否可以打印日志到文件
     *
     * @param tag
     * @param level
     * @return
     */
    public static boolean isFileLoggable(String tag, int level) {
        return LogManager.getInstance().isFileLoggable(tag, level);
    }

    /**
     * 是否可以打印日志到文件
     *
     * @param tag
     * @param level
     * @return
     */
    public static boolean isFileLoggable(LogTagInterface tag, int level) {
        return LogManager.getInstance().isFileLoggable(tag, level);
    }

    @Deprecated
    public static void v(String tag, String msg) {
        Log.smartPrint(Log.VERBOSE, LogCustomTag.createOrGetLogCustomTag(tag), msg, null, false);
    }

    public static void v(LogTagInterface tag, String msg) {
        Log.smartPrint(Log.VERBOSE, tag, msg, null, false);
    }

    public static void v(LogTagInterface tag, String msg, Throwable t) {
        Log.smartPrint(Log.VERBOSE, tag, msg, t, false);
    }

    public static void vs(LogTagInterface tag, String msg) {
        Log.smartPrint(Log.VERBOSE, tag, msg, null, true);
    }

    @Deprecated
    public static void d(String tag, String msg) {
        Log.smartPrint(Log.DEBUG, LogCustomTag.createOrGetLogCustomTag(tag), msg, null, false);
    }

    public static void d(LogTagInterface tag, String msg) {
        Log.smartPrint(Log.DEBUG, tag, msg, null, false);
    }

    public static void d(LogTagInterface tag, String msg, Throwable t) {
        Log.smartPrint(Log.DEBUG, tag, msg, t, false);
    }

    public static void ds(LogTagInterface tag, String msg) {
        Log.smartPrint(Log.DEBUG, tag, msg, null, true);
    }

    @Deprecated
    public static void i(String tag, String msg) {
        Log.smartPrint(Log.INFO, LogCustomTag.createOrGetLogCustomTag(tag), msg, null, false);
    }

    public static void i(LogTagInterface tag, String msg) {
        Log.smartPrint(Log.INFO, tag, msg, null, false);
    }

    public static void i(LogTagInterface tag, String msg, Throwable t) {
        Log.smartPrint(Log.INFO, tag, msg, t, false);
    }

    public static void is(LogTagInterface tag, String msg) {
        Log.smartPrint(Log.INFO, tag, msg, null, true);
    }

    @Deprecated
    public static void w(String tag, String msg) {
        Log.smartPrint(Log.WARN, LogCustomTag.createOrGetLogCustomTag(tag), msg, null, false);
    }

    public static void w(LogTagInterface tag, String msg) {
        Log.smartPrint(Log.WARN, tag, msg, null, false);
    }

    public static void w(LogTagInterface tag, String msg, Throwable t) {
        Log.smartPrint(Log.WARN, tag, msg, t, false);
    }

    public static void ws(LogTagInterface tag, String msg) {
        Log.smartPrint(Log.WARN, tag, msg, null, true);
    }

    @Deprecated
    public static void e(String tag, String msg) {
        Log.smartPrint(Log.ERROR, LogCustomTag.createOrGetLogCustomTag(tag), msg, null, false);
    }

    public static void e(LogTagInterface tag, String msg) {
        Log.smartPrint(Log.ERROR, tag, msg, null, false);
    }

    public static void e(LogTagInterface tag, String msg, Throwable t) {
        Log.smartPrint(Log.ERROR, tag, msg, t, false);
    }

    public static void es(LogTagInterface tag, String msg) {
        Log.smartPrint(Log.ERROR, tag, msg, null, true);
    }

    public static String levelName(int level) {
        switch (level) {
            case VERBOSE:
                return "VERBOSE";
            case DEBUG:
                return "DEBUG";
            case INFO:
                return "INFO";
            case WARN:
                return "WARN";
            case ERROR:
                return "ERROR";
            default:
                return "DEFAULT";
        }
    }

    public static void registerUncaughtExceptionHandler() {
//        final UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
//        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread thread, Throwable ex) {
//                Log.e(LogTag.GLOBAL, "uncaughtException", ex);
//                // MapNaviAnalysis.reportError(NaviApplication.getInstance(),
//                // ex);//umeng错误统计
//                defaultUncaughtExceptionHandler.uncaughtException(thread, ex);
//            }
//        });
    }

    public static void filePrint(int level, LogTagInterface tag, String msg, Throwable t) {
        if (!Log.isFileLoggable(tag, level)) {
            return;
        }
        Log.executorService.submit(new FilePrintRunnable(level, tag, msg, t));
    }

    public enum CodeLocationStyle {

        /**
         * 第一行
         */
        FIRST(true, true),
        /**
         * 随后的行
         */
        SUBSEQUENT(true, true);

        /**
         * 是否添加at字眼在行首
         */
        private boolean isAt;

        /**
         * 是否使用简单类名
         */
        private boolean isSimpleClassName;

        CodeLocationStyle(boolean isAt, boolean isSimpleClassName) {
            this.isAt = isAt;
            this.isSimpleClassName = isSimpleClassName;
        }

        /**
         * @return the {@link #isAt}
         */
        public boolean isAt() {
            return isAt;
        }

        /**
         * @return the {@link #isSimpleClassName}
         */
        public boolean isSimpleClassName() {
            return isSimpleClassName;
        }

    }

    private static final class FilePrintRunnable implements Runnable {

        private static PrintWriter printWriter = null;

        private int level;
        private LogTagInterface tag;
        private String msg;
        private Throwable t;

        private FilePrintRunnable(int level, LogTagInterface tag, String msg, Throwable t) {
            this.level = level;
            this.tag = tag;
            this.msg = msg;
            this.t = t;
        }

        @Override
        public void run() {

            boolean exception = false;

            try {

                if (null == FilePrintRunnable.printWriter) {
                    File logFile = LogManager.getInstance().getLogFile();
                    if (null == logFile) {
                        return;
                    }
                    FilePrintRunnable.printWriter = new PrintWriter(new FileOutputStream(logFile, true));
                }

                // 级别
                FilePrintRunnable.printWriter.print("|");
                FilePrintRunnable.printWriter.print(Log.levelName(level));

                // 时间
                FilePrintRunnable.printWriter.print("|");
                FilePrintRunnable.printWriter.print(Log.TIME_FORMAT.format(new Date()));

                // TAG
                FilePrintRunnable.printWriter.print("|");
                FilePrintRunnable.printWriter.print(tag);

                // 信息
                FilePrintRunnable.printWriter.print("|");
                FilePrintRunnable.printWriter.print(msg);

                // 异常
                if (null != t) {
                    t.printStackTrace(FilePrintRunnable.printWriter);
                }

                FilePrintRunnable.printWriter.println();
                FilePrintRunnable.printWriter.flush();

            } catch (IOException e) {
                e.printStackTrace();
                exception = true;
            }

            if ((null != FilePrintRunnable.printWriter) && ((Log.workQueue.size() < 1) || exception)) {
                FilePrintRunnable.printWriter.close();
                FilePrintRunnable.printWriter = null;
            }

        }
    }

}
