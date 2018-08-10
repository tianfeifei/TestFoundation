package com.example.tianfei.foundation.log;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合多个 logger
 * Created by zhangyunfei on 16/9/23.
 */
public class GroupLogger implements Logger {
    private static List<Logger> loggerList = new ArrayList<>();

    public void addChildLogger(Logger logger) {
        loggerList.add(logger);
    }

    @Override
    public void d(String tag, String msg) {
        for (int i = 0; i < loggerList.size(); i++) {
            Logger logger = loggerList.get(i);
            if (logger != null)
                logger.d(tag, msg);
        }
    }

    @Override
    public void i(String tag, String msg) {
        for (int i = 0; i < loggerList.size(); i++) {
            Logger logger = loggerList.get(i);
            if (logger != null)
                logger.i(tag, msg);
        }
    }

    @Override
    public void e(String tag, String msg) {
        for (int i = 0; i < loggerList.size(); i++) {
            Logger logger = loggerList.get(i);
            if (logger != null)
                logger.e(tag, msg);
        }
    }

    @Override
    public void e(String tag, String msg, Exception ex) {
        for (int i = 0; i < loggerList.size(); i++) {
            Logger logger = loggerList.get(i);
            if (logger != null)
                logger.e(tag, msg, ex);
        }
    }

    public void clear(){
        if(loggerList !=null){
            loggerList.clear();
        }
    }
}