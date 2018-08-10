package com.example.tianfei.foundation.utils;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yun on 16/1/7.
 */
public class StringUtil {
//    public static String getString(int resId) {
//        return Global.getAppContext().getString(resId);
//    }
//
//    public static String getString(int resId, Object... formatArgs) {
//        return Global.getAppContext().getString(resId, formatArgs);
//    }
//
//    public static String[] getStringArray(int resId) {
//        return Global.getAppContext().getResources().getStringArray(resId);
//    }

    public static String getStringFromAssets(Context context, String fileName) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            while ((line = bufReader.readLine()) != null)
                sb.append(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 手机号验证
     *
     * @param str
     * @return
     */
    public static boolean isMobileNum(String str) {
        Pattern p = Pattern.compile("[1][0-9]\\d{9}");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 弹出短时间的Toast
     *
     * @param content
     */
//    public static void toastStringShort(String content) {
//        Toast.makeText(Global.getAppContext(), content, Toast.LENGTH_SHORT).show();
//    }
//
//    public static void toastStringShort(int id) {
//        Context appContext = Global.getAppContext();
//        Toast.makeText(appContext, appContext.getResources().getString(id), Toast.LENGTH_SHORT)
//                .show();
//    }

    /**
     * 弹出长时间的Toast
     *
     * @param content
     */
//    public static void toastStringLong(String content) {
//        Toast.makeText(Global.getAppContext(), content, Toast.LENGTH_LONG).show();
//    }
//
//    public static void toastStringLong(int id) {
//        Context appContext = Global.getAppContext();
//        Toast.makeText(appContext, appContext.getResources().getString(id), Toast.LENGTH_LONG).show();
//    }

    public static String subNum(double d) {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(d);
    }
}
