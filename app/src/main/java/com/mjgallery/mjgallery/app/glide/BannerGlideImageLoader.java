package com.mjgallery.mjgallery.app.glide;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.mvp.model.bean.HomeBanner;
import com.youth.banner.loader.ImageLoader;

public class BannerGlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(@NonNull Context context, Object path, @NonNull ImageView imageView) {
        HomeBanner bean = (HomeBanner) path;
        RequestOptions options = new RequestOptions().error(R.drawable.icon_loading).placeholder(R.drawable.icon_loading);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(bean.getImgPath()).apply(options).into(imageView);
    }
}
