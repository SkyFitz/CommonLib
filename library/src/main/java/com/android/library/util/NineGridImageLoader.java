package com.android.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.library.base.GlideApp;
import com.lzy.ninegrid.NineGridView;

public class NineGridImageLoader implements NineGridView.ImageLoader {

    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
        GlideApp.with(context)
                .load(url)
//                .placeholder(R.drawable.ic_default_image)//
//                .error(R.drawable.ic_default_image)//
                .into(imageView);
    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }
}
