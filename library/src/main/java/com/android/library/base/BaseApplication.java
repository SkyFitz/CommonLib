package com.android.library.base;

import android.app.Application;
import android.content.Context;

/**
 * Author:  ljo_h
 * Date:    2016/6/3
 * Description:
 */

public class BaseApplication extends Application {

    static BaseApplication mInstance;

    public static BaseApplication instance() {
        return mInstance;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        this.mInstance = this;
    }
}
