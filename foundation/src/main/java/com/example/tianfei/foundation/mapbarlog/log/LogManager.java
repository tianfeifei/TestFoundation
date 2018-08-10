package com.example.tianfei.foundation.mapbarlog.log;

import android.content.Context;
import android.text.TextUtils;

import com.example.tianfei.foundation.utils.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志管理器
 */
public class LogManager {

    /**
     * 初始化
     */
    /**
     * 打印日志到外部存储设备
     */
    private boolean isLogFile = false;
    /**
     * 打印日志到logcat控制台
     */
    private boolean isLog = false;
    /**
     * 数据结构
     */
    private Map<LogTagInterface, List<LogRA>> map = new HashMap<LogTagInterface, List<LogRA>>();
    /**
     * 存放Log的文件
     */
    private File file;

    private LogManager() {
    }

    public static LogManager getInstance() {
        return Holder.INSTANCE;
    }

    public void jsonToLogManager(JSONObject jsonObject) {
        try {
            isLogFile = jsonObject.optBoolean("isLogFile", isLogFile);
            isLog = jsonObject.optBoolean("isLog", isLog);
            boolean has = jsonObject.has("logRAs");
            int l = 0;
            if (has) {
                JSONArray ja = jsonObject.getJSONArray("logRAs");
                l = ja.length();
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    LogRA logRA = LogRA.jsonToLogRA(jo);
                    add(logRA);
                }
            }

            if (!has || (l == 0)) {
                LogRA logRA = new LogRA();
                LogRule rule = new LogRule();
                rule.setTag(LogTag.ALL);
                logRA.setRule(rule);
                LogAction action = new LogAction();
                action.setLoggable(true);
                logRA.setAction(action);
                add(logRA);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void init(Context context) {
        String strJson = StringUtil.getStringFromAssets(context, "config.json");
        if (!TextUtils.isEmpty(strJson)) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(strJson);
                JSONObject jsonConfig = jsonObject.getJSONObject("logConfig");
                jsonToLogManager(jsonConfig);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param logRA
     * @return
     * @see ArrayList#add(Object)
     */
    public boolean add(LogRA logRA) {
        boolean r = false;
        LogTagInterface tag = logRA.getRule().getTag();
        if (map.containsKey(tag)) {
            List<LogRA> list = map.get(tag);
            r = list.add(logRA);
        } else {
            ArrayList<LogRA> list = new ArrayList<LogRA>();
            r = list.add(logRA);
            map.put(tag, list);
        }
        return r;
    }

    /**
     * 是否可以打印日志
     *
     * @param tag
     * @param level
     * @return
     */
    public boolean isLoggable(String tag, int level) {
        return isLoggable(LogCustomTag.createOrGetLogCustomTag(tag), level);
    }

    /**
     * 是否可以打印日志
     *
     * @param tag
     * @param level
     * @return
     */
    public boolean isLoggable(LogTagInterface tag, int level) {
        if (isLog) {
            LogRA logRA = LogManager.getInstance().smartFindLogRA(level, tag, false);
            // System.out.println("Tag:" + tag.getTagName() + "    logRa:" +
            // logRA+ "   loggable:true"+ (null != logRA ?
            // logRA.getAction().isLoggable() : false));
            return (null != logRA ? logRA.getAction().isLoggable() : false);
        } else {
            return false;
        }
    }

    /**
     * 是否可以打印日志到文件
     *
     * @param tag
     * @param level
     * @return
     */
    public boolean isFileLoggable(String tag, int level) {
        return isFileLoggable(LogCustomTag.createOrGetLogCustomTag(tag), level);
    }

    /**
     * 是否可以打印日志到文件
     *
     * @param tag
     * @param level
     * @return
     */
    public boolean isFileLoggable(LogTagInterface tag, int level) {
        return isLogFile;
    }

    @SuppressWarnings("deprecation")
    public LogRA smartFindLogRA(int level, LogTagInterface tag, boolean stack) {
        LogRA r = findLogRA(level, tag, stack);
        if (null == r) {
            r = findLogRA(level, LogTag.ALL, stack);
        }
        return r;
    }

    private LogRA findLogRA(int level, LogTagInterface tag, boolean stack) {
        LogRA r = null;
        List<LogRA> list = map.get(tag);
        if (null != list) {
            for (LogRA logRA : list) {
                if (logRA.check(level, tag, stack)) {
                    r = logRA;
                    break;
                }
            }
        }
        return r;
    }

    public boolean isLogFile() {
        return isLogFile;
    }

    public boolean isLog() {
        return isLog;
    }

    /**
     * @return
     */
    public File getLogFile() {
        return file;
    }

    public void setLogFile(File file) {
        this.file = file;
    }

    private static class Holder {
        private static final LogManager INSTANCE = new LogManager();
    }

}
