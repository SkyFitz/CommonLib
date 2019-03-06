package com.android.library.util;

import android.content.Context;

import java.io.InputStream;
import java.util.Properties;

/**
 * 获取配置文件
 */
public class ProperTiesUtils {
    public static Properties getProperties(Context c){
        Properties properties;
        Properties props = new Properties();
        try {
            InputStream in = c.getAssets().open("config.properties");
            props.load(in);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        properties = props;
        return properties;
    }
}
