package com.android.library.util;

import android.content.ContentProviderOperation;
import android.content.Context;

import com.google.gson.JsonElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Author:  jact
 * Date:    2016/3/24
 * Description:
 */
public abstract class JSONHandlerUtils {
    protected static Context mContext;

    public JSONHandlerUtils(Context context) {
        mContext = context;
    }

    public abstract void makeContentProviderOperations(ArrayList<ContentProviderOperation> list);

    public abstract void process(JsonElement element);

    public static String parseResource(Context context, int resource) throws IOException {
        InputStream is = context.getResources().openRawResource(resource);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            //Reader reader = new BufferedReader(new InputStreamReader(is, Charsets.UTF_8));
            Reader reader = new BufferedReader(new InputStreamReader(is));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }

        return writer.toString();
    }
}
