package com.android.library.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Author:  Jact
 * Date:    2016/2/13.
 * Description:
 */
public class ToastUtils {

    /**
     * Long Toast
     * @param context
     */
    public static void LongToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * Long Toast
     * @param context
     */
    public static void ShortToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}