package com.android.library.util;

import android.content.Context;
import android.widget.ImageView;

import com.android.library.base.GlideApp;
import com.android.library.widget.RoundAngleImageView;
import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

public class BannerImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        GlideApp
                .with(context)
                .load(path)
//          .apply(bitmapTransform(new RoundedCornersTransformation(12, 0, RoundedCornersTransformation.CornerType.ALL)))
                .into(imageView);
    }

    @Override
    public ImageView createImageView(Context context) {
        return new RoundAngleImageView(context);
    }
}