package com.android.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by fitz on 2017/4/7.
 */

public class XScrollView extends ScrollView {

    private onScrollChangeListen listen;

    public XScrollView(Context context) {
        super(context);
    }

    public XScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(listen != null) {
            listen.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public void setOnScrollChangeListen(onScrollChangeListen listen){
        this.listen = listen;
    }

    public interface onScrollChangeListen{
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }
}
