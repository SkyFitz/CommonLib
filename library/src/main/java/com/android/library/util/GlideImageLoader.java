package com.android.library.util;

import android.content.Context;
import android.widget.ImageView;

import com.android.library.base.GlideApp;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * @auth fitz
 * @date 2018/10/27 22:37
 * <p>
 * Copyright 2018 杭州字节律动信息科技有限公司   版权所有
 * Copyright 2018 ZhiJieLvDong Group Holding Ltd. All Rights Reserved
 */
public class GlideImageLoader {

    private static GlideImageLoader singleton;

    private GlideImageLoader() {

    }

    public static GlideImageLoader getInstance() {
        if (singleton == null) {
            synchronized (GlideImageLoader.class) {
                if (singleton == null) {
                    singleton = new GlideImageLoader();
                }
            }
        }
        return singleton;
    }

    public void Load(Context context, ImageView imageView, Object url){
        GlideApp.with(context)
                .load(url)
                .thumbnail(0.1f)
//                .placeholder(R.drawable.ic_img_placeholder)
                .into(imageView);
    }

    public void Load(Context context, ImageView imageView, String url, int radius){
        GlideApp.with(context)
                .load(url)
                .thumbnail(0.1f)
                .apply(RequestOptions.bitmapTransform(new MultiTransformation(
                        new CenterCrop(),
                        new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.ALL)))
//                        .placeholder(R.drawable.ic_img_placeholder)
                )
                .into(imageView);
    }
}
