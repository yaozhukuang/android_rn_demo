package com.example.administrator.test.react;

import android.util.Log;

import com.example.administrator.test.BuildConfig;

/**
 * @author zhanwei
 */
public class RNLog {

    /**
     * 打印日志
     *
     * @param message 日志信息
     */
    public static void log(String message) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        Log.e("RNFileHelper", message);
    }
}
