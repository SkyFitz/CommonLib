package com.android.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @auth fitz
 * @date 2018/09/24 22:34
 * <p>
 * Copyright 2018 杭州字节律动信息科技有限公司   版权所有
 * Copyright 2018 ZhiJieLvDong Group Holding Ltd. All Rights Reserved
 */
public class TranslucentScrollView extends ScrollView {

    public TranslucentScrollView(Context context) {
        super(context);
    }

    public TranslucentScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //1.获取ScrollView的Y方向的滑动距离
        int scrollY = getScrollY();
        //2.获取屏幕高度
        int heightPixels = getContext().getResources().getDisplayMetrics().heightPixels;
        //3.设置滚动范围，当scrollY < 屏幕高度的1/3时，进行设置
        float v = heightPixels / 3f;
        //4.设置滚动的百分比
        float v1 = scrollY / v;//(0-1)
        //5.透明度应为1-0
        float alpha = 1 - v1;

        //7.在必要的时候调用接口中的方法
        if(listener!=null){
            if(scrollY <= v){
                listener.onTranlucent(alpha);
            }

        }
    }

    //6.监听回调
    private TranslucentListener listener;
    public void setListener(TranslucentListener listener) {
        this.listener = listener;
    }

    public interface TranslucentListener{
        /**
         * 透明度的回调监听
         * @param alpha 0~1 透明度
         */
        public void onTranlucent(float alpha);
    }
}

