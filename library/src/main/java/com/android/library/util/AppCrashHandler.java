package com.android.library.util;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

import java.lang.Thread.UncaughtExceptionHandler;


public class AppCrashHandler implements UncaughtExceptionHandler {
    /**
     * Debug Log Tag
     */
    public static final String TAG = AppCrashHandler.class.getSimpleName();
    /**
     * 是否开启日志输出, 在Debug状态下开启, 在Release状态下关闭以提升程序性能
     */
    public static final boolean DEBUG = true;
    /**
     * CrashHandler实例
     */
    private static AppCrashHandler INSTANCE;

    /**
     * 保证只有一个CrashHandler实例
     */
    private AppCrashHandler() {

    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static AppCrashHandler getInstance() {
        if (INSTANCE == null)
            INSTANCE = new AppCrashHandler();
        return INSTANCE;
    }

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     *
     */
    public static void init(Context context) {
        init(context, null);
    }

    public static void init(Context context, String toastMsg) {
    	AppCrashHandler crashHandler = getInstance();

        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
    }

    /** 
     * @brief 当UncaughtException发生时会转入该函数来处理 
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        String msg = ex.getMessage();
        LogUtils.e(msg);
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(final Throwable ex) {
    	
        return true;
    }

}