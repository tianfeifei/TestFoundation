package com.example.tianfei.foundation.mapbarlog.log;

import android.content.Context;
import android.os.Environment;
import android.util.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by tianff on 2016/7/20.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler instance;  //单例引用，这里我们做成单例的，因为我们一个应用程序里面只需要一个UncaughtExceptionHandler实例
    private int error = 0;
    private Context context;
    private String exceptionString;

    private CrashHandler() {
    }

    public synchronized static CrashHandler getInstance() {  //同步方法，以免单例多线程环境下出现异常
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    /**
     * 输出 异常的详细stack信息
     *
     * @param ex
     * @return
     */
    public static String getExceptionString(Throwable ex) {
        if (ex == null) return null;
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter, true);
        ex.printStackTrace(printWriter);
        return stringWriter.toString();
    }

    public void init(Context ctx, int error) {  //初始化，把当前对象设置成UncaughtExceptionHandler处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        context = ctx;
        this.error = error;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {  //当有未处理的异常发生时，就会来到这里。。

        String errorString = getExceptionString(ex);
        Log.e("CrashHandler", errorString);

        String logFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mapbar/obd_gsm" + "/client_Log/";
        File file = new File(logFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss");
        String fileName = "/log_" + format.format(c.getTime())
                + ".txt";
        File logFile = new File(logFilePath + fileName);
        if (Config.DEBUG) {
            try {
                PrintWriter printWriter = new PrintWriter(new FileOutputStream(logFile, true));
                printWriter.println(errorString);
                printWriter.flush();
                printWriter.close();
                printWriter = null;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }
}
