package com.mjgallery.mjgallery.app.glide;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

import java.io.File;

public class GlideUtil {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};


//    /**
//     * 加载圆形图片
//     *
//     * @param imageView 加载的imageView
//     * @param file      文件
//     */
//    public static void loadCircleImage(ImageView imageView, File file) {
//        if (file == null || imageView == null) {
//            return;
//        }
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
////                .placeholder(R.drawable.icon_photo)
////                .error(R.drawable.icon_photo)
////                .fallback(R.drawable.icon_photo)
//                .priority(Priority.HIGH)
//                .transform(new GlideCircleTransform());
//        Glide.with(imageView.getContext())
//                .load(file)
//                .apply(options)
//                .into(imageView);
//    }

    /**
     * 加载圆形图片
     *
     * @param imageView 加载的imageView
     * @param url       文件地址
     */
    public static void loadCircleImage(ImageView imageView, String url, int error) {
        if (TextUtils.isEmpty(url) || imageView == null) {
            imageView.setImageResource(error);
            return;

        }
        RequestOptions options = new RequestOptions().error(error).placeholder(error).circleCrop();
        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void loadRequestOptions(ImageView imageView, String url, RequestOptions options) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param imageView 加载的imageView
     * @param res       资源文件
     */
    public static void loadCircleImage(ImageView imageView, int res) {
        if (res == 0 || imageView == null) {
            return;
        }
        RequestOptions options = new RequestOptions().error(res).placeholder(res).circleCrop();
        Glide.with(imageView.getContext())
                .load(res)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载普通图片
     *
     * @param imageView 加载的imageView
     * @param file      文件
     */
    public static void loadCircleImage(ImageView imageView, File file) {
        if (file == null || imageView == null) {
            return;
        }
        /**
         * placeholder：占位即加载中的图片
         * error：错误图片
         * fallback：当url为null的时候，判断是否设置了fallback，是的话则显示fallback图片
         */
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .placeholder(R.drawable.icon_notpicture)
//                .error(R.drawable.icon_notpicture)
//                .fallback(R.drawable.icon_notpicture)
                .priority(Priority.HIGH)
                .transform(new GlideCircleTransform());
        ;
        Glide.with(imageView.getContext())
                .load(file)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param imageView 加载的imageView
     * @param res       资源文件
     */
    public static void loadNormalImageSV(ImageView imageView, int res) {
        if (res == 0 || imageView == null) {
            return;
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .placeholder(R.drawable.icon_photo)
//                .error(R.drawable.icon_photo)
//                .fallback(R.drawable.icon_photo)
                .priority(Priority.HIGH)
                .transform(new GlideCircleTransform());
        Glide.with(imageView.getContext())
                .load(res)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载普通图片（缓存）
     *
     * @param imageView 加载的imageView
     * @param url       文件地址
     */
    public static void loadNormalImage(ImageView imageView, String url, int error, int w, int h) {
        if (TextUtils.isEmpty(url) || imageView == null) {
            return;
        }
        /**
         * placeholder：占位即加载中的图片
         * error：错误图片
         * fallback：当url为null的时候，判断是否设置了fallback，是的话则显示fallback图片
         */
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(error)
                .error(error)
                .fallback(error)
                .override(w, h)
                .priority(Priority.HIGH);
        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载普通图片（缓存）
     *
     * @param imageView 加载的imageView
     * @param url       文件地址
     */
    public static void loadNormalImage(ImageView imageView, String url, int error) {
        if (TextUtils.isEmpty(url) || imageView == null) {
            return;
        }
        /**
         * placeholder：占位即加载中的图片
         * error：错误图片
         * fallback：当url为null的时候，判断是否设置了fallback，是的话则显示fallback图片
         */
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(error)
                .error(error)
                .fallback(error)
                .priority(Priority.HIGH);
        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载普通图片（缓存）
     *
     * @param imageView 加载的imageView
     * @param url       文件地址
     */
    public static void loadNormalImage(ImageView imageView, String url) {
        if (TextUtils.isEmpty(url) || imageView == null) {
            return;
        }
        /**
         * placeholder：占位即加载中的图片
         * error：错误图片
         * fallback：当url为null的时候，判断是否设置了fallback，是的话则显示fallback图片
         */
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .placeholder(error)
//                .error(error)
//                .fallback(error)
                .priority(Priority.HIGH);
        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载普通图片（跳过缓存）
     *
     * @param imageView 加载的imageView
     * @param url       文件地址
     */
    public static void loadNormalImageSkipCache(ImageView imageView, String url, int error) {
        if (TextUtils.isEmpty(url) || imageView == null) {
            return;
        }
        /**
         * placeholder：占位即加载中的图片
         * error：错误图片
         * fallback：当url为null的时候，判断是否设置了fallback，是的话则显示fallback图片
         */
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(error)
                .error(error)
                .fallback(error)
                .skipMemoryCache(true)
                .placeholder(imageView.getDrawable())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.HIGH);
        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载普通图片
     *
     * @param imageView 加载的imageView
     * @param file      文件
     */
    public static void loadNormalImage(ImageView imageView, File file) {
        if (file == null || imageView == null) {
            return;
        }
        /**
         * placeholder：占位即加载中的图片
         * error：错误图片
         * fallback：当url为null的时候，判断是否设置了fallback，是的话则显示fallback图片
         */
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .placeholder(R.drawable.icon_notpicture)
//                .error(R.drawable.icon_notpicture)
//                .fallback(R.drawable.icon_notpicture)
                .priority(Priority.HIGH);
        Glide.with(imageView.getContext())
                .load(file)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载普通图片
     *
     * @param imageView 加载的imageView
     * @param res       资源文件
     */
    public static void loadNormalImage(ImageView imageView, int res) {
        if (res == 0 || imageView == null) {
            return;
        }
        /**
         * placeholder：占位即加载中的图片
         * error：错误图片
         * fallback：当url为null的时候，判断是否设置了fallback，是的话则显示fallback图片
         */
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(imageView.getDrawable())
                .error(res)
                .fallback(res)
                .priority(Priority.HIGH);
        Glide.with(imageView.getContext())
                .load(res)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载普通图片
     *
     * @param imageView 加载的imageView
     * @param res       资源文件
     */
    public static void loadBannberImage(ImageView imageView, int res) {
        if (res == 0 || imageView == null) {
            return;
        }
        /**
         * placeholder：占位即加载中的图片
         * error：错误图片
         * fallback：当url为null的时候，判断是否设置了fallback，是的话则显示fallback图片
         */
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .placeholder(R.drawable.icon_notpicture)
//                .error(R.drawable.icon_notpicture)
//                .fallback(R.drawable.icon_notpicture)
                .priority(Priority.HIGH);
        Glide.with(imageView.getContext())
                .load(res)
                .apply(options)
                .into(imageView);
    }

    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 使用ArmsUtils进行大数据
     */
    public static void imageNormalLoader(ImageView imageView, String imgUrl, int error) {
        ArmsUtils.obtainAppComponentFromContext(imageView.getContext())
                .imageLoader()
                .loadImage(imageView.getContext(), ImageConfigImpl
                        .builder()
                        .url(imgUrl)
                        .imageView(imageView)
                        .errorPic(error)
                        .placeholder(error)
                        .build());
    }



    /**
     * 使用ArmsUtils进行大数据
     */
    public static void imageNormalCircleLoader(ImageView imageView, String imgUrl, int error) {
        ArmsUtils.obtainAppComponentFromContext(imageView.getContext())
                .imageLoader()
                .loadImage(imageView.getContext(), ImageConfigImpl
                        .builder()
                        .url(imgUrl)
                        .imageView(imageView)
                        .isCircle(true)
                        .errorPic(error)
                        .placeholder(error)
                        .build());
    }
}
