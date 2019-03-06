package com.android.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.android.library.R;
import com.bumptech.glide.Glide;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

/**
 * Author:  ljo_h
 * Date:    2016/9/28
 * Description:
 */
public class XRecyclerView extends RecyclerView{
    private Context context;

    public XRecyclerView(Context context) {
        this(context, null);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;

        init(attrs);
    }

    private void init(AttributeSet attrs){
        addOnScrollListener(new ImageAutoLoadScrollListener());

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.XRecyclerView);
        boolean isdriver = array.getBoolean(R.styleable.XRecyclerView_isdivider, false);
        int height = array.getDimensionPixelOffset(R.styleable.XRecyclerView_divider_height, 1);
        int background = array.getColor(R.styleable.XRecyclerView_divider_background, 1);
        int marginleft = array.getDimensionPixelOffset(R.styleable.XRecyclerView_divider_mrginleft, 0);
        int marginright = array.getDimensionPixelOffset(R.styleable.XRecyclerView_divider_mrginright, 0);
        boolean vertical = array.getBoolean(R.styleable.XRecyclerView_vertical, false);

        if(isdriver){
            if(vertical){
                this.addItemDecoration(new VerticalDividerItemDecoration.Builder(context)
                        .color(background)
                        .size(height)
                        .build());
            }else {
                this.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context)
                        .color(background)
                        .size(height)
                        .build());
            }
        }
    }

    //监听滚动来对图片加载进行判断处理
    public class ImageAutoLoadScrollListener extends OnScrollListener{

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState){
                case SCROLL_STATE_IDLE: // The RecyclerView is not currently scrolling.
                    //当屏幕停止滚动，加载图片
                    try {
                        if(getContext() != null) Glide.with(getContext()).resumeRequests();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case SCROLL_STATE_DRAGGING: // The RecyclerView is currently being dragged by outside input such as user touch input.
                    //当屏幕滚动且用户使用的触碰或手指还在屏幕上，停止加载图片
                    try {
                        if(getContext() != null) Glide.with(getContext()).pauseRequests();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case SCROLL_STATE_SETTLING: // The RecyclerView is currently animating to a final position while not under outside control.
                    //由于用户的操作，屏幕产生惯性滑动，停止加载图片
                    try {
                        if(getContext() != null) Glide.with(getContext()).pauseRequests();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
